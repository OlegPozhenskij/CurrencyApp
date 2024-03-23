package ru.teamscore.java23.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.teamscore.java23.controllers.dto.StatisticDto;
import ru.teamscore.java23.controllers.dto.StatisticListDto;
import ru.teamscore.java23.models.Currency;
import ru.teamscore.java23.models.CurrencyPair;
import ru.teamscore.java23.models.ExchangeRate;
import ru.teamscore.java23.models.PriceStatistics;
import ru.teamscore.java23.models.services.CurrencyManager;
import ru.teamscore.java23.models.services.CurrencyPairManager;
import ru.teamscore.java23.models.services.ExchangeRateManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stats")
public class StatisticController {
    @Autowired
    private CurrencyPairManager currencyPairManager;

    @Autowired
    private ExchangeRateManager exchangeRateManager;

    @Autowired
    private CurrencyManager currencyManager;

    @GetMapping
    public StatisticListDto showStatisticsList(
            //TODO преобразовать в DTO
            @RequestParam String currencyFirst,
            @RequestParam String currencyLast,
            @RequestParam String period,
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String end,
            @RequestParam(required = false) Integer num) {

        var statistics = new StatisticListDto();
        if (num != null) {
            statistics.setStatsList(currencyPairManager.getCurrencyStatistics(currencyFirst, currencyLast, num, period).
                    stream()
                    .map(StatisticDto::new)
                    .collect(Collectors.toList())
            );
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime dateStart = LocalDateTime.parse(start, formatter);
            LocalDateTime dateEnd = LocalDateTime.parse(end, formatter);
            statistics.setStatsList(currencyPairManager.getCurrencyStatistics(currencyFirst, currencyLast, dateStart, dateEnd, period).
                    stream()
                    .map(StatisticDto::new)
                    .collect(Collectors.toList()));
        }

        return statistics;
    }
}
