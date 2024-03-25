package ru.teamscore.java23;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import ru.teamscore.java23.models.Currency;


import org.hibernate.cfg.Configuration;
import ru.teamscore.java23.models.CurrencyPair;
import ru.teamscore.java23.models.ExchangeRate;
import ru.teamscore.java23.services.CurrencyService;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyServiceTest {
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private CurrencyService currencyService;


    @BeforeAll
    public static void setup() throws IOException {
        entityManagerFactory = new Configuration()
                .configure("hibernate-postgres.cfg.xml")
                .addAnnotatedClass(Currency.class)
                .addAnnotatedClass(CurrencyPair.class)
                .addAnnotatedClass(ExchangeRate.class)
                .buildSessionFactory();

        SqlScripts.runFromFile(entityManagerFactory, "createSchema.sql");
    }

    @BeforeEach
    public void openSession() throws IOException {
        SqlScripts.runFromFile(entityManagerFactory, "insertTestCurrencies.sql");
        entityManager = entityManagerFactory.createEntityManager();
        currencyService = new CurrencyService(entityManager);
    }

    @AfterEach
    public void closeSession() throws IOException {
        if (entityManager != null) {
            entityManager.close();
        }
        SqlScripts.runFromFile(entityManagerFactory, "clearTestCurrencies.sql");
    }

    @AfterAll
    public static void tearDown() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }

    @Test
    public void testSaveCurrency() {
        Currency currency = new Currency();
        currency.setShortTitle("ASD");
        currency.setFullTitle("ASolo NA");

        currencyService.saveOrUpdateCurrency(currency);

        Currency savedCurrency = entityManager.find(Currency.class, currency.getId());
        assertNotNull(savedCurrency);
        assertEquals(currency, savedCurrency);
    }

    @Test
    public void testDeleteCurrency() {
        currencyService.deleteCurrency(entityManager.find(Currency.class, 1));

        assertNull(entityManager.find(Currency.class, 1));
        assertNotNull(entityManager.find(Currency.class, 2));
    }

    @Test
    void testGetCurrencyByShortTitle() {
        Currency currency = currencyService.getCurrencyByShortTitle("USD");
        assertNotNull(currency);
        assertEquals("USD", currency.getShortTitle());
    }

    @Test
    void testGetAllCurrencies() {
        List<Currency> currencies = currencyService.getAllCurrencies();
        assertNotNull(currencies);
        assertFalse(currencies.isEmpty());
    }

    @Test
    void testGetCurrencyById() {
        Currency currency = currencyService.getCurrencyById(2);
        assertNotNull(currency);
        assertEquals(2, currency.getId());
    }

    @Test
    void testCountCurrencies() {
        long count = currencyService.countCurrencies();
        assertEquals(4, count);
    }

    @Test
    void testGetCurrenciesByNameSubstring() {
        List<Currency> currencies = currencyService.getCurrenciesByNameSubstring("Dollar");
        assertFalse(currencies.isEmpty());
        assertEquals("USD", currencies.get(0).getShortTitle());
    }
}