//package ru.teamscore.java23;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import ru.teamscore.java23.entities.Currency;
//import ru.teamscore.java23.entities.CurrencyPair;
//import ru.teamscore.java23.entities.ExchangeRate;
//import ru.teamscore.java23.entities.PriceStatistics;
//
//import org.hibernate.cfg.Configuration;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class CurrencyPairManagerTest {
//    private static EntityManagerFactory entityManagerFactory;
//    private EntityManager entityManager;
//
//    @BeforeAll
//    public static void setup() throws IOException {
//        entityManagerFactory = new Configuration()
//                .configure("hibernate-postgres.cfg.xml")
//                .addAnnotatedClass(ExchangeRate.class)
//                .buildSessionFactory();
//    }
//
//    @Test
//    void searchCurrencyPairsByCurrencyName() {
//        CurrencyPair currencyPair = new CurrencyPair(1,
//                Currency.load(1L, "USD", "US Dollar"),
//                Currency.load(2L, "EUR", "Euro"),
//                2
//        );
//        currencyPairRepository.add(currencyPair);
//
//        List<CurrencyPair> result = currencyPairManager.searchCurrencyPairsByCurrencyName("USD");
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals("USD", result.get(0).getBaseCurrency().getShortTitle());
//    }
//
//    @Test
//    void saveCurrencyPair() {
//        List<CurrencyPair> currencyPairRepository = new ArrayList<>();
//        CurrencyPairManager currencyPairManager = new CurrencyPairManager(currencyPairRepository);
//
//        CurrencyPair currencyPair = new CurrencyPair(2,
//                Currency.load(1L,"USD", "US Dollar"),
//                Currency.load(1L,"EUR", "Euro"),
//                2
//        );
//
//        currencyPairManager.saveCurrencyPair(currencyPair);
//
//        assertTrue(currencyPairRepository.contains(currencyPair));
//    }
//
//    @Test
//    void deleteCurrencyPairById() {
//        List<CurrencyPair> currencyPairRepository = new ArrayList<>();
//        CurrencyPair currencyPair = new CurrencyPair(3,
//                Currency.load(1L,"USD", "US Dollar"),
//                Currency.load(1L,"EUR", "Euro"),
//                2
//        );
//        currencyPairRepository.add(currencyPair);
//        CurrencyPairManager currencyPairManager = new CurrencyPairManager(currencyPairRepository);
//
//        currencyPairManager.deleteCurrencyPairById(currencyPair);
//
//        assertFalse(currencyPairRepository.contains(currencyPair));
//    }
//
//    @Test
//    void getCurrencyPairByNames() {
//        List<CurrencyPair> currencyPairRepository = new ArrayList<>();
//        CurrencyPair currencyPair1 = new CurrencyPair(4,
//                Currency.load(1L,"USD", "US Dollar"),
//                Currency.load(1L,"EUR", "Euro"),
//                2
//        );
//        CurrencyPair currencyPair2 = new CurrencyPair(5,
//                Currency.load(1L,"USD", "US Dollar"),
//                Currency.load(1L,"GBP", "British Pound"),
//                2
//        );
//        currencyPairRepository.add(currencyPair1);
//        currencyPairRepository.add(currencyPair2);
//
//        CurrencyPairManager currencyPairManager = new CurrencyPairManager(currencyPairRepository);
//
//        CurrencyPair result = currencyPairManager.getCurrencyPairByNames("USD", "GBP");
//
//        assertNotNull(result);
//        assertEquals("USD", result.getBaseCurrency().getShortTitle());
//        assertEquals("GBP", result.getQuotedCurrency().getShortTitle());
//    }
//
//}