package ru.teamscore.java23.controllers;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.teamscore.java23.models.Currency;
import ru.teamscore.java23.services.CurrencyManager;
import ru.teamscore.java23.services.CurrencyPairManager;
import ru.teamscore.java23.services.ExchangeRateManager;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CurrencyController {

    private static final String CURRENCY_INDEX_VIEW = "admin/currency/index";
    private static final String CURRENCY_EDIT_VIEW = "admin/currency/edit";

    @Autowired
    private CurrencyPairManager currencyPairManager;

    @Autowired
    private ExchangeRateManager exchangeRateManager;

    @Autowired
    private CurrencyManager currencyManager;

    @GetMapping("admin/currency/index.html")
    public String showCurrencyIndex(Model model) {
        List<Currency> currencies = currencyManager.getAllCurrencies();
        model.addAttribute("currencies", currencies);
        return CURRENCY_INDEX_VIEW;
    }

//    TODO при невозможности удалить Currency из-за связи с CurrencyPair, сообщать на фрот
//    TODO сделать отдельный метод
    @DeleteMapping("admin/currency/delete")
    public String deleteCurrencyById(@RequestParam("id") Long currencyId, Model model) {
        currencyManager.deleteCurrency(currencyManager.getCurrencyById(currencyId));
        return CURRENCY_INDEX_VIEW;
    }

    @GetMapping("admin/currency/edit.html")
    public String showCurrencyEdit(@RequestParam("id") Long currencyId, Model model) {
        Currency currency = currencyManager.getCurrencyById(currencyId);
        model.addAttribute("currency", currency);
        return CURRENCY_EDIT_VIEW;
    }



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
