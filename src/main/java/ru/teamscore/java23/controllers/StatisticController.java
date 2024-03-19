package ru.teamscore.java23.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.teamscore.java23.models.PriceStatistics;
import ru.teamscore.java23.services.CurrencyManager;
import ru.teamscore.java23.services.CurrencyPairManager;
import ru.teamscore.java23.services.ExchangeRateManager;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class StatisticController {
    @Autowired
    private CurrencyPairManager currencyPairManager;

    @Autowired
    private ExchangeRateManager exchangeRateManager;

    @Autowired
    private CurrencyManager currencyManager;

    @GetMapping({"", "/"})
    public String showUserPage() {
        System.out.println("Вернули страницу user");
        return "user";
    }

    @GetMapping("/stats")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> showStatisticsList(
            @RequestParam String currencyFirst,
            @RequestParam String currencyLast,
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String end,
            @RequestParam String period,
            @RequestParam(required = false) Integer num) {


        List<Map<String, Object>> resultList = new ArrayList<>();
        List<PriceStatistics> statistics;

        if (num != null) {
            statistics = currencyPairManager.getCurrencyStatistics(currencyFirst, currencyLast, num, period);

        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime dateStart = LocalDateTime.parse(start, formatter);
            LocalDateTime dateEnd = LocalDateTime.parse(end, formatter);

            statistics = currencyPairManager.getCurrencyStatistics(currencyFirst, currencyLast, dateStart, dateEnd, period);
        }

        for (PriceStatistics stat : statistics) {
            Map<String, Object> item = new HashMap<>();
            item.put("timestamp", stat.getTimestamp().toInstant(ZoneOffset.UTC).toEpochMilli());
            item.put("open", stat.getOpen());
            item.put("close", stat.getClose());
            item.put("max", stat.getMax());
            item.put("min", stat.getMin());
            item.put("direction", stat.getDirection());
            resultList.add(item);
        }

        return ResponseEntity.ok(resultList);
    }


    private void setData() {
//        currencyManager.saveCurrency(new Currency("USD", "United States Dollar"));
//        currencyManager.saveCurrency(new Currency("RUB", "Russian Ruble"));
//        currencyManager.saveCurrency(new Currency("EUR", "Euro"));
//        currencyManager.saveCurrency(new Currency("GBP", "British Pound"));
//        currencyManager.saveCurrency(new Currency("JPY", "Japanese Yen"));
//        currencyManager.saveCurrency(new Currency("AUD", "Australian Dollar"));
//        currencyManager.saveCurrency(new Currency("CAD", "Canadian Dollar"));
//        currencyManager.saveCurrency(new Currency("CHF", "Swiss Franc"));
//        currencyManager.saveCurrency(new Currency("CNY", "Chinese Yuan"));
//        currencyManager.saveCurrency(new Currency("HKD", "Hong Kong Dollar"));
//        currencyManager.saveCurrency(new Currency("NZD", "New Zealand Dollar"));
//        currencyManager.saveCurrency(new Currency("SEK", "Swedish Krona"));
//        currencyManager.saveCurrency(new Currency("KRW", "South Korean Won"));
//        currencyManager.saveCurrency(new Currency("SGD", "Singapore Dollar"));
//        currencyManager.saveCurrency(new Currency("NOK", "Norwegian Krone"));
//        currencyManager.saveCurrency(new Currency("MXN", "Mexican Peso"));
//        currencyManager.saveCurrency(new Currency("INR", "Indian Rupee"));
//        currencyManager.saveCurrency(new Currency("BRL", "Brazilian Real"));
//        currencyManager.saveCurrency(new Currency("ZAR", "South African Rand"));
//        currencyManager.saveCurrency(new Currency("TRY", "Turkish Lira"));


//        currencyPairManager.saveCurrencyPair(new CurrencyPair(
//                currencyManager.getCurrencyByShortTitle("USD"),
//                currencyManager.getCurrencyByShortTitle("RUB"),
//                2));
//
//        exchangeRateManager.saveRate(new ExchangeRate(
//                LocalDateTime.parse("2019-05-16T23:42:55", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")),
//                new BigDecimal("13.45"),
//                currencyPairManager.searchCurrencyPairsByCurrencyName("USD", "RUB")));
//
//        exchangeRateManager.saveRate(new ExchangeRate(
//                LocalDateTime.parse("2020-02-16T23:42:55", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")),
//                new BigDecimal("25.82"),
//                currencyPairManager.searchCurrencyPairsByCurrencyName("USD", "RUB")));
//
//        exchangeRateManager.saveRate(new ExchangeRate(
//                LocalDateTime.parse("2021-02-16T23:42:55", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")),
//                new BigDecimal("47.23"),
//                currencyPairManager.searchCurrencyPairsByCurrencyName("USD", "RUB")));
//
//        exchangeRateManager.saveRate(new ExchangeRate(
//                LocalDateTime.parse("2022-02-16T23:42:55", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")),
//                new BigDecimal("85.95"),
//                currencyPairManager.searchCurrencyPairsByCurrencyName("USD", "RUB")));
//
//        exchangeRateManager.saveRate(new ExchangeRate(
//                LocalDateTime.parse("2023-02-16T23:42:55", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")),
//                new BigDecimal("120.11"),
//                currencyPairManager.searchCurrencyPairsByCurrencyName("USD", "RUB")));
//
//        exchangeRateManager.saveRate(new ExchangeRate(
//                LocalDateTime.parse("2024-02-16T23:42:55", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")),
//                new BigDecimal("159.85"),
//                currencyPairManager.searchCurrencyPairsByCurrencyName("USD", "RUB")));
//
//        exchangeRateManager.saveRate(new ExchangeRate(
//                LocalDateTime.parse("2024-07-16T23:42:55", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")),
//                new BigDecimal("200.02"),
//                currencyPairManager.searchCurrencyPairsByCurrencyName("USD", "RUB")));
    }
}
