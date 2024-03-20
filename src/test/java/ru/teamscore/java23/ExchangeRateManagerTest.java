package ru.teamscore.java23;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import ru.teamscore.java23.models.Currency;
import ru.teamscore.java23.models.CurrencyPair;
import ru.teamscore.java23.models.ExchangeRate;
import ru.teamscore.java23.models.services.CurrencyManager;
import ru.teamscore.java23.models.services.CurrencyPairManager;
import ru.teamscore.java23.models.services.ExchangeRateManager;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
        var exchangeRate = rateManager.getExchangeRateById(1);
        assertNotNull(exchangeRate);
        assertEquals(BigDecimal.valueOf(0.8524), exchangeRate.getRateVal());
    }

    @Test
    void saveExchangeRate() {
        var exchangeRate = new ExchangeRate(
                LocalDateTime.now(),
                BigDecimal.valueOf(23.5),
                currencyPairManager.searchCurrencyPairsByCurrencyName("USD","EUR"));

        rateManager.saveRate(exchangeRate);

        assertEquals(3, rateManager.getAllExchangeRates().size());
    }

    @Test
    void deleteCurrencyPairById() {
        rateManager.deleteExchangeRateById(1);

        assertEquals(1, rateManager.getAllExchangeRates().size());
    }
}