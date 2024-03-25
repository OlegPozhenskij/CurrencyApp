package ru.teamscore.java23;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import ru.teamscore.java23.models.enums.Period;
import ru.teamscore.java23.models.Currency;
import ru.teamscore.java23.models.CurrencyPair;
import ru.teamscore.java23.models.ExchangeRate;

import org.hibernate.cfg.Configuration;
import ru.teamscore.java23.services.CurrencyService;
import ru.teamscore.java23.services.CurrencyPairService;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyPairServiceTest {
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private CurrencyPairService currencyPairService;
    private CurrencyService currencyService;

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
        currencyService = new CurrencyService(entityManager);
        currencyPairService = new CurrencyPairService(entityManager, currencyService);
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
        var currencyPair = currencyPairService.searchCurrencyPairsByCurrencyName("USD","EUR");
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

        currencyPairService.saveOrUpdateCurrencyPair(currencyPair);

        assertSame(currencyPair.getBaseCurrency(), currencyPairService.getCurrencyPairByNames("HOS", "BIC").get().getBaseCurrency());
        assertSame(currencyPair.getQuotedCurrency(), currencyPairService.getCurrencyPairByNames("HOS", "BIC").get().getQuotedCurrency());
    }

    @Test
    void deleteCurrencyPairById() {
        currencyPairService.deleteCurrencyPairById(2);

        assertEquals(1 , currencyPairService.getAllCurrencyPairs().size());
    }

    @Test
    void getCurrencyPairByNames() {
        Optional<CurrencyPair> result = currencyPairService.getCurrencyPairByNames("USD", "EUR");

        assertNotNull(result);
        assertEquals("USD", result.get().getBaseCurrency().getShortTitle());
        assertEquals("EUR", result.get().getQuotedCurrency().getShortTitle());
    }

}