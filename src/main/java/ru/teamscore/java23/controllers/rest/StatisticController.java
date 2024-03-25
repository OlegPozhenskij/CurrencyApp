package ru.teamscore.java23.controllers.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.teamscore.java23.controllers.dto.StatisticDto;
import ru.teamscore.java23.controllers.dto.StatisticListDto;
import ru.teamscore.java23.controllers.dto.StatisticParametersDto;
import ru.teamscore.java23.services.CurrencyPairService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stats")
public class StatisticController {
    private final CurrencyPairService currencyPairService;

    @GetMapping
    public StatisticListDto showStatisticsList(StatisticParametersDto spt) {
        var statistics = new StatisticListDto();
        if (spt.getNum() != null) {
            statistics.setStatsList(currencyPairService.getCurrencyStatistics(spt.getCurrencyFirst(), spt.getCurrencyLast(), spt.getNum(), spt.getPeriod()).
                    stream()
                    .map(StatisticDto::new)
                    .collect(Collectors.toList())
            );
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime dateStart = LocalDateTime.parse(spt.getStart(), formatter);
            LocalDateTime dateEnd = LocalDateTime.parse(spt.getEnd(), formatter);
            statistics.setStatsList(currencyPairService.getCurrencyStatistics(spt.getCurrencyFirst(), spt.getCurrencyLast(), dateStart, dateEnd, spt.getPeriod()).
                    stream()
                    .map(StatisticDto::new)
                    .collect(Collectors.toList()));
        }

        return statistics;
    }
}
