package ru.teamscore.java23.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import ru.teamscore.java23.SqlScripts;
import ru.teamscore.java23.models.Currency;
import ru.teamscore.java23.models.CurrencyPair;
import ru.teamscore.java23.models.ExchangeRate;
import ru.teamscore.java23.models.PriceStatistics;
import ru.teamscore.java23.models.enums.Direction;
import ru.teamscore.java23.models.enums.Period;
import ru.teamscore.java23.models.statistics.StatisticsService;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StatisticsServiceTest {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private CurrencyPairService currencyPairService;
    private CurrencyService currencyService;
    private ExchangeRateService rateManager;

    @BeforeAll
    public static void setup() throws IOException {
        entityManagerFactory = new Configuration()
                .configure("hibernate-postgres.cfg.xml")
                .addAnnotatedClass(CurrencyPair.class)
                .addAnnotatedClass(Currency.class)
                .addAnnotatedClass(ExchangeRate.class)
                .buildSessionFactory();

        SqlScripts.runFromFile(entityManagerFactory, "createSchema.sql");
    }

    @BeforeEach
    public void openSession() throws IOException {
        SqlScripts.runFromFile(entityManagerFactory, "insertTestCurrencies.sql");
        SqlScripts.runFromFile(entityManagerFactory, "insertTestCurrencyPair.sql");
        SqlScripts.runFromFile(entityManagerFactory, "insertExchangeRate.sql");
        entityManager = entityManagerFactory.createEntityManager();
        currencyService = new CurrencyService(entityManager);
        currencyPairService = new CurrencyPairService(entityManager, currencyService);
        rateManager = new ExchangeRateService(entityManager);
    }

    @AfterEach
    public void closeSession() throws IOException {
        if (entityManager != null) {
            entityManager.close();
        }
        SqlScripts.runFromFile(entityManagerFactory, "clearTestCurrencyPair.sql");
        SqlScripts.runFromFile(entityManagerFactory, "clearTestCurrencies.sql");
        SqlScripts.runFromFile(entityManagerFactory, "clearTestExchangeRate.sql");
    }

    @AfterAll
    public static void tearDown() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }

    @Test
    void testGetStatsWithDateRange() {
        var currencyPair =  currencyPairService.getAllCurrencyPairs().get(1);

        ExchangeRate rate1 = new ExchangeRate(
                LocalDateTime.parse("2024-04-01T06:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")),
                new BigDecimal("87.2345"),
                currencyPair
        );

        ExchangeRate rate2 = new ExchangeRate(
                LocalDateTime.parse("2024-04-01T12:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")),
                new BigDecimal("124.2375"),
                currencyPair
        );

        rateManager.saveOrUpdateExchangeRate(rate1);
        rateManager.saveOrUpdateExchangeRate(rate2);

        var startDate = LocalDateTime.parse("2024-04-01T02:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        var endDate = LocalDateTime.parse("2024-04-01T21:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        Period period = Period.HOUR;

        List<PriceStatistics> stats = StatisticsService.getStats(entityManager, currencyPair, startDate, endDate, period);

        System.out.println(stats);
        assertFalse(stats.isEmpty());
        assertEquals(2, stats.size());
        assertEquals(Direction.NONE, stats.get(1).getDirection());
        assertEquals(rate2.getRateVal(), stats.get(1).getMax());

    }


    @Test
    void testGetStatsWithNum() {

        CurrencyPair currencyPair = currencyPairService.getAllCurrencyPairs().get(1);
        
        ExchangeRate rate1 = new ExchangeRate(
                LocalDateTime.parse("2024-01-01T04:04:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")),
                new BigDecimal("1020.2345"),
                currencyPair
        );

        ExchangeRate rate2 = new ExchangeRate(
                LocalDateTime.parse("2024-01-01T05:34:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")),
                new BigDecimal("253.2145"),
                currencyPair
        );

        ExchangeRate rate3 = new ExchangeRate(
                LocalDateTime.parse("2024-01-01T06:18:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")),
                new BigDecimal("327.2345"),
                currencyPair
        );

        ExchangeRate rate4 = new ExchangeRate(
                LocalDateTime.parse("2024-01-01T09:34:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")),
                new BigDecimal("853.2345"),
                currencyPair
        );

        ExchangeRate rate5 = new ExchangeRate(
                LocalDateTime.parse("2024-01-01T09:21:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")),
                new BigDecimal("500.2345"),
                currencyPair
        );

        rateManager.saveOrUpdateExchangeRate(rate1);
        rateManager.saveOrUpdateExchangeRate(rate2);
        rateManager.saveOrUpdateExchangeRate(rate3);
        rateManager.saveOrUpdateExchangeRate(rate4);
        rateManager.saveOrUpdateExchangeRate(rate5);

        Period period = Period.HOUR;

        List<PriceStatistics> stats = StatisticsService.getStats(entityManager, currencyPair,3, period);

        System.out.println(stats);
        assertFalse(stats.isEmpty());
        assertEquals(2, stats.size());
        assertEquals(new BigDecimal("853.2345"), stats.get(0).getMax());

    }

}