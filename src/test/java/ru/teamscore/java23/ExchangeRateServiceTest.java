package ru.teamscore.java23;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import ru.teamscore.java23.models.Currency;
import ru.teamscore.java23.models.CurrencyPair;
import ru.teamscore.java23.models.ExchangeRate;
import ru.teamscore.java23.services.CurrencyService;
import ru.teamscore.java23.services.CurrencyPairService;
import ru.teamscore.java23.services.ExchangeRateService;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ExchangeRateServiceTest {
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
    void searchExchangeRateById() {
        var exchangeRate = rateManager.getExchangeRateById(1);
        assertNotNull(exchangeRate);
        assertEquals(BigDecimal.valueOf(0.8524), exchangeRate.getRateVal());
    }

    @Test
    void saveExchangeRate() {
        var exchangeRate = new ExchangeRate(
                LocalDateTime.now(),
                BigDecimal.valueOf(23.5),
                currencyPairService.searchCurrencyPairsByCurrencyName("USD","EUR"));

        rateManager.saveOrUpdateExchangeRate(exchangeRate);

        assertEquals(3, rateManager.getAllExchangeRates().size());
    }

    @Test
    void deleteCurrencyPairById() {
        rateManager.deleteExchangeRateById(1);

        assertEquals(1, rateManager.getAllExchangeRates().size());
    }
}