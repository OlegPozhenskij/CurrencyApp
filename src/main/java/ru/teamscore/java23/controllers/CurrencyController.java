package ru.teamscore.java23.controllers;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.teamscore.java23.services.CurrencyManager;
import ru.teamscore.java23.services.CurrencyPairManager;
import ru.teamscore.java23.services.ExchangeRateManager;

import java.util.stream.Collectors;

@Controller
public class CurrencyController {

    @Autowired
    private CurrencyPairManager currencyPairManager;

    @Autowired
    private ExchangeRateManager exchangeRateManager;

    @Autowired
    private CurrencyManager currencyManager;

    @GetMapping("/currency")
    @ResponseBody
    public ResponseEntity<Object> getCurrencyList() {
        var currPars = currencyPairManager.getAllCurrencyPairs();

        var currList = currPars
                .stream()
                .map(item -> item.getBaseCurrency().getShortTitle()
                         + "/"
                         + item.getQuotedCurrency().getShortTitle())
                .collect(Collectors.toList());

        return ResponseEntity.ok(currList);
    }
}
