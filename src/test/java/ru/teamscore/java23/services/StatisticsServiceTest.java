package ru.teamscore.java23.services;

import org.junit.jupiter.api.Test;
import ru.teamscore.java23.entities.Currency;
import ru.teamscore.java23.entities.CurrencyPair;
import ru.teamscore.java23.entities.ExchangeRate;
import ru.teamscore.java23.entities.PriceStatistics;
import ru.teamscore.java23.enums.Period;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StatisticsServiceTest {


    @Test
    void testGetStatsWithDateRange() {

        ExchangeRate rate1 = new ExchangeRate();
        rate1.setRateVal(new BigDecimal("87.2345"));
        rate1.setLocalDateTime(LocalDateTime.parse("2024-01-01T06:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));


        ExchangeRate rate2 = new ExchangeRate();
        rate2.setRateVal(new BigDecimal("53.3456"));
        rate2.setLocalDateTime(LocalDateTime.parse("2024-01-01T10:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));


        CurrencyPair currencyPair = new CurrencyPair(1L,
                List.of(rate1, rate2),
                1,
                new Currency("USD", "States"),
                new Currency("RUB", "Russia"));

        String startDate = "2024-01-01T03:00:00";
        String endDate = "2024-01-01T10:00:00";
        Period period = Period.HOUR;

        List<PriceStatistics> stats = StatisticsService.getStats(currencyPair, startDate, endDate, period);

        System.out.println(stats);
        assertFalse(stats.isEmpty());

    }


    @Test
    void testGetStatsWithNum() {

        ExchangeRate rate1 = new ExchangeRate();
        rate1.setRateVal(new BigDecimal("1000.2345"));
        rate1.setLocalDateTime(LocalDateTime.parse("2024-01-01T04:04:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));

        ExchangeRate rate2 = new ExchangeRate();
        rate2.setRateVal(new BigDecimal("1.3456"));
        rate2.setLocalDateTime(LocalDateTime.parse("2024-01-01T02:02:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));

        ExchangeRate rate3 = new ExchangeRate();
        rate3.setRateVal(new BigDecimal("0.2345"));
        rate3.setLocalDateTime(LocalDateTime.parse("2024-01-01T01:07:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));

        ExchangeRate rate4 = new ExchangeRate();
        rate4.setRateVal(new BigDecimal("3.3456"));
        rate4.setLocalDateTime(LocalDateTime.parse("2024-01-01T03:05:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));

        ExchangeRate rate5 = new ExchangeRate();
        rate5.setRateVal(new BigDecimal("22.2345"));
        rate5.setLocalDateTime(LocalDateTime.parse("2024-01-01T09:03:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));

        ExchangeRate rate6 = new ExchangeRate();
        rate6.setRateVal(new BigDecimal("51.3456"));
        rate6.setLocalDateTime(LocalDateTime.parse("2024-01-01T09:05:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));

        ExchangeRate rate7 = new ExchangeRate();
        rate7.setRateVal(new BigDecimal("73.2345"));
        rate7.setLocalDateTime(LocalDateTime.parse("2024-01-01T04:02:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));

        ExchangeRate rate8 = new ExchangeRate();
        rate8.setRateVal(new BigDecimal("53.3456"));
        rate8.setLocalDateTime(LocalDateTime.parse("2024-01-01T03:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));

        ExchangeRate rate9 = new ExchangeRate();
        rate9.setRateVal(new BigDecimal("80.2345"));
        rate9.setLocalDateTime(LocalDateTime.parse("2024-01-01T02:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));

        ExchangeRate rate10 = new ExchangeRate();
        rate10.setRateVal(new BigDecimal("10.3456"));
        rate10.setLocalDateTime(LocalDateTime.parse("2024-01-01T01:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));


        CurrencyPair currencyPair = new CurrencyPair(1L,
                List.of(rate1, rate2, rate3, rate4, rate5, rate6, rate7, rate8, rate9, rate10),
                1,
                new Currency("USD", "States"),
                new Currency("RUB", "Russia"));


        Period period = Period.HOUR;

        List<PriceStatistics> stats = StatisticsService.getStats(currencyPair,8, period);

        System.out.println(stats);
        assertFalse(stats.isEmpty());

    }

}