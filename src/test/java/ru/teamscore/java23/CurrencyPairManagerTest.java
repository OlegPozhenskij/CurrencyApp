package ru.teamscore.java23;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import ru.teamscore.java23.entities.Currency;
import ru.teamscore.java23.entities.CurrencyPair;
import ru.teamscore.java23.entities.ExchangeRate;

import org.hibernate.cfg.Configuration;
import ru.teamscore.java23.services.CurrencyConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyPairManagerTest {
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private CurrencyPairManager currencyPairManager;
    private CurrencyManager currencyManager;
    private static CurrencyConverter converter;

    @BeforeAll
    public static void setup() throws IOException {
        entityManagerFactory = new Configuration()
                .configure("hibernate-postgres.cfg.xml")
                .addAnnotatedClass(CurrencyPair.class)
                .addAnnotatedClass(Currency.class)
                .addAnnotatedClass(ExchangeRate.class)
                .addAnnotatedClass(CurrencyConverter.class)
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
    }

    @Test
    void saveCurrencyPair() {
        CurrencyPair currencyPair = new CurrencyPair(14,
                Currency.load(45L,"JPY", "US Dollar"),
                Currency.load(9L,"USD", "Gagnitafon Coin"),
                2
        );

        currencyPairManager.saveCurrencyPair(currencyPair);

        assertSame(currencyPair.getBaseCurrency(), currencyPairManager.getCurrencyPairByNames("JPY", "USD").get().getBaseCurrency());
        assertSame(currencyPair.getQuotedCurrency(), currencyPairManager.getCurrencyPairByNames("JPY", "USD").get().getQuotedCurrency());
    }

    @Test
    void deleteCurrencyPairById() {
        CurrencyPair currencyPair = new CurrencyPair(3,
                Currency.load(6L,"USD", "US Dollar"),
                Currency.load(7L,"JPY", "Japan"),
                2
        );

        currencyPairManager.deleteCurrencyPairById(3);

        assertEquals(3 ,currencyPairManager.getAllCurrencyPairs().size());
    }

    @Test
    void getCurrencyPairByNames() {
        Optional<CurrencyPair> result = currencyPairManager.getCurrencyPairByNames("USD", "GBP");

        assertNotNull(result);
        assertEquals("USD", result.get().getBaseCurrency().getShortTitle());
        assertEquals("GBP", result.get().getQuotedCurrency().getShortTitle());
    }

}