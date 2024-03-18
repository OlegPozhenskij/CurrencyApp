package ru.teamscore.java23.models;

import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyPairTest {

    private CurrencyPair currencyPair;

    @BeforeEach
    void setUp() {
//        Currency currency = new Currency("asd", "asdasdads");
//        currency.getShortTitle()

        currencyPair = new CurrencyPair(
                new Currency("USD", "US Dollar"),
                new Currency("USD", "US Dollar"),
                2
        );
        // Добавим несколько ExchangeRate для тестов
        currencyPair.addExchangeRate(new ExchangeRate(LocalDateTime.of(2024, 1, 1, 2, 12), new BigDecimal("1.1"), currencyPair));
        currencyPair.addExchangeRate(new ExchangeRate(LocalDateTime.of(2024, 1, 2, 23, 6), new BigDecimal("1.2"), currencyPair));
        currencyPair.addExchangeRate(new ExchangeRate(LocalDateTime.of(2024, 1, 3, 7, 38), new BigDecimal("1.3"), currencyPair));
        currencyPair.addExchangeRate(new ExchangeRate(LocalDateTime.of(2024, 1, 4, 11, 21), new BigDecimal("1.4"), currencyPair));
        currencyPair.addExchangeRate(new ExchangeRate(LocalDateTime.of(2024, 1, 5, 18, 1), new BigDecimal("1.5"), currencyPair));
    }

    @Test
    void addExchangeRate() {
        int initialSize = currencyPair.getExchangeRatesCount();
        currencyPair.addExchangeRate(new ExchangeRate(LocalDateTime.now(), new BigDecimal("1.6"), currencyPair));
        assertEquals(initialSize + 1, currencyPair.getExchangeRatesCount());
    }

    @Test
    void deleteExchangeRate() {
        int initialSize = currencyPair.getExchangeRatesCount();
        ExchangeRate rateToRemove = currencyPair.getExchangeRateHistory().get(0);
        assertTrue(currencyPair.deleteExchangeRate(rateToRemove));
        assertEquals(initialSize - 1, currencyPair.getExchangeRatesCount());
    }

    @Test
    void getExchangeRatesInRange() {
        LocalDateTime start = LocalDateTime.of(2024, 1, 2, 0, 0);
        LocalDateTime end = LocalDateTime.of(2024, 1, 4, 0, 0);
        List<ExchangeRate> exchangeRates = currencyPair.getExchangeRates(start, end);
        assertEquals(2, exchangeRates.size());
    }

    @Test
    void getLastExchangeRatesInRange() {
        List<ExchangeRate> recentRates = currencyPair.getLastExchangeRatesInRange(5);
        assertEquals(5, recentRates.size());
    }

}