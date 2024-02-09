package ru.teamscore.java23.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyPairTest {

    private CurrencyPair currencyPair;

    @BeforeEach
    void setUp() {
        currencyPair = new CurrencyPair(
                1L,
                new ArrayList<>(),
                2,
                new Currency("USD", "US Dollar"),
                new Currency("EUR", "Euro")
        );
        // Добавим несколько ExchangeRate для тестов
        currencyPair.addExchangeRate(new ExchangeRate(1L, LocalDateTime.of(2024, 1, 1, 0, 0), new BigDecimal("1.1"), currencyPair));
        currencyPair.addExchangeRate(new ExchangeRate(2L, LocalDateTime.of(2024, 1, 2, 0, 0), new BigDecimal("1.2"), currencyPair));
        currencyPair.addExchangeRate(new ExchangeRate(3L, LocalDateTime.of(2024, 1, 3, 0, 0), new BigDecimal("1.3"), currencyPair));
        currencyPair.addExchangeRate(new ExchangeRate(4L, LocalDateTime.of(2024, 1, 4, 0, 0), new BigDecimal("1.4"), currencyPair));
        currencyPair.addExchangeRate(new ExchangeRate(5L, LocalDateTime.of(2024, 1, 5, 0, 0), new BigDecimal("1.5"), currencyPair));
    }

    @Test
    void addExchangeRate() {
        int initialSize = currencyPair.getExchangeRatesCount();
        currencyPair.addExchangeRate(new ExchangeRate(null, LocalDateTime.now(), new BigDecimal("1.6"), currencyPair));
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
        assertEquals(3, exchangeRates.size());
    }

    @Test
    void getRecentExchangeRates() {
        List<ExchangeRate> recentRates = currencyPair.getExchangeRates(5);
        assertEquals(5, recentRates.size());
    }

}