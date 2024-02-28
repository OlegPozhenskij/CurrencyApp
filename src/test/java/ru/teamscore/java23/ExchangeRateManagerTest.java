package ru.teamscore.java23;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.grammars.hql.HqlParser;
import org.junit.jupiter.api.*;
import ru.teamscore.java23.entities.Currency;
import ru.teamscore.java23.entities.CurrencyPair;
import ru.teamscore.java23.entities.ExchangeRate;
import ru.teamscore.java23.services.CurrencyConverter;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExchangeRateManagerTest {
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private CurrencyPairManager currencyPairManager;
    private CurrencyManager currencyManager;
    private ExchangeRateManager rateManager;

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
        currencyManager = new CurrencyManager(entityManager);
        currencyPairManager = new CurrencyPairManager(entityManager, currencyManager);
        rateManager = new ExchangeRateManager(entityManager);
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
        var exchangeRate = rateManager.getExchangeRateById(2);
        assertNotNull(exchangeRate);
        assertEquals(BigDecimal.valueOf(0.7523), exchangeRate.getRateVal());
    }

    @Test
    void saveExchangeRate() {
        var exchangeRate = new ExchangeRate(8L,
                LocalDateTime.now(),
                BigDecimal.valueOf(23.5),
                currencyPairManager.searchCurrencyPairsByCurrencyName("USD","EUR"));

        rateManager.saveCurrency(exchangeRate);

        assertEquals(5, rateManager.getAllExchangeRates().size());
    }

    @Test
    void deleteCurrencyPairById() {
        CurrencyPair currencyPair = new CurrencyPair();

        rateManager.deleteExchangeRateById(3);

        assertEquals(3, rateManager.getAllExchangeRates().size());
    }
}