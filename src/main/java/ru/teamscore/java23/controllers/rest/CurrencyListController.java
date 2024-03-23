package ru.teamscore.java23.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.teamscore.java23.controllers.dto.StringListDto;
import ru.teamscore.java23.models.services.CurrencyManager;
import ru.teamscore.java23.models.services.CurrencyPairManager;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/currencyList")
public class CurrencyListController {

    @Autowired
    private CurrencyPairManager currencyPairManager;

    @Autowired
    private CurrencyManager currencyManager;

    @GetMapping
    public StringListDto getCurrencyList() {
        return new StringListDto(currencyPairManager.getAllCurrencyPairs().stream()
                .map(pair -> pair.getBaseCurrency().getShortTitle() + "/" + pair.getQuotedCurrency().getShortTitle())
                .collect(Collectors.toList()));
    }
}
