package ru.teamscore.java23.controllers.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.teamscore.java23.controllers.dto.StringListDto;
import ru.teamscore.java23.services.CurrencyPairService;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/currencyList")
public class CurrencyListController {
    private final CurrencyPairService currencyPairService;

    @GetMapping
    public StringListDto getCurrencyList() {
        return new StringListDto(currencyPairService.getAllCurrencyPairs().stream()
                .map(pair -> pair.getBaseCurrency().getShortTitle() + "/" + pair.getQuotedCurrency().getShortTitle())
                .collect(Collectors.toList()));
    }
}
