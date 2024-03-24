package ru.teamscore.java23;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import ru.teamscore.java23.models.enums.Period;
import ru.teamscore.java23.models.Currency;
import ru.teamscore.java23.models.CurrencyPair;
import ru.teamscore.java23.models.ExchangeRate;

import org.hibernate.cfg.Configuration;
import ru.teamscore.java23.models.services.CurrencyManager;
import ru.teamscore.java23.models.services.CurrencyPairManager;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyPairManagerTest {
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private CurrencyPairManager currencyPairManager;
    private CurrencyManager currencyManager;

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
        entityManager = entityManagerFactory.createEntityManager();
        currencyManager = new CurrencyManager(entityManager);
        currencyPairManager = new CurrencyPairManager(entityManager, currencyManager);
    }

    @AfterEach
    public void closeSession() throws IOException {
        if (entityManager != null) {
            entityManager.close();
        }
        SqlScripts.runFromFile(entityManagerFactory, "clearTestCurrencyPair.sql");
        SqlScripts.runFromFile(entityManagerFactory, "clearTestCurrencies.sql");
    }

    @AfterAll
    public static void tearDown() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }

    @Test
    void searchCurrencyPairsByCurrencyName() {
        var currencyPair = currencyPairManager.searchCurrencyPairsByCurrencyName("USD","EUR");
        assertNotNull(currencyPair);
        assertEquals("USD", currencyPair.getBaseCurrency().getShortTitle());
        assertEquals("EUR", currencyPair.getQuotedCurrency().getShortTitle());
        System.out.println(Period.valueOf("HOUR"));
    }

    @Test
    void saveCurrencyPair() {
        CurrencyPair currencyPair = new CurrencyPair(
                new Currency("HOS", "Japanese Yen"),
                new Currency("BIC", "British Pound Sterling"),
                2
        );

        currencyPairManager.saveOrUpdateCurrencyPair(currencyPair);

        assertSame(currencyPair.getBaseCurrency(), currencyPairManager.getCurrencyPairByNames("HOS", "BIC").get().getBaseCurrency());
        assertSame(currencyPair.getQuotedCurrency(), currencyPairManager.getCurrencyPairByNames("HOS", "BIC").get().getQuotedCurrency());
    }

    @Test
    void deleteCurrencyPairById() {
        currencyPairManager.deleteCurrencyPairById(2);

        assertEquals(1 ,currencyPairManager.getAllCurrencyPairs().size());
    }

    @Test
    void getCurrencyPairByNames() {
        Optional<CurrencyPair> result = currencyPairManager.getCurrencyPairByNames("USD", "EUR");

        assertNotNull(result);
        assertEquals("USD", result.get().getBaseCurrency().getShortTitle());
        assertEquals("EUR", result.get().getQuotedCurrency().getShortTitle());
    }

}