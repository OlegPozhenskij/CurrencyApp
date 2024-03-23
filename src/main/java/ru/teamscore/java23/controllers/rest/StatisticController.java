package ru.teamscore.java23.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.teamscore.java23.controllers.dto.StatisticDto;
import ru.teamscore.java23.controllers.dto.StatisticListDto;
import ru.teamscore.java23.controllers.dto.StatisticParametersDto;
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
    public StatisticListDto showStatisticsList(StatisticParametersDto spt) {
        var statistics = new StatisticListDto();
        if (spt.getNum() != null) {
            statistics.setStatsList(currencyPairManager.getCurrencyStatistics(spt.getCurrencyFirst(), spt.getCurrencyLast(), spt.getNum(), spt.getPeriod()).
                    stream()
                    .map(StatisticDto::new)
                    .collect(Collectors.toList())
            );
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime dateStart = LocalDateTime.parse(spt.getStart(), formatter);
            LocalDateTime dateEnd = LocalDateTime.parse(spt.getEnd(), formatter);
            statistics.setStatsList(currencyPairManager.getCurrencyStatistics(spt.getCurrencyFirst(), spt.getCurrencyLast(), dateStart, dateEnd, spt.getPeriod()).
                    stream()
                    .map(StatisticDto::new)
                    .collect(Collectors.toList()));
        }

        return statistics;
    }
}
