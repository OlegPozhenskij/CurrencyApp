package ru.teamscore.java23.controllers;

import jakarta.servlet.http.HttpServletRequest;
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
    private CurrencyManager currencyManager;

    //+
    @GetMapping("admin/currency/index.html")
    public String showCurrencyIndexPage(Model model) {
        List<Currency> currencies = currencyManager.getAllCurrencies();
        model.addAttribute("currencies", currencies);
        return CURRENCY_INDEX_VIEW;
    }

    //+
    @GetMapping("admin/currency/edit.html")
    public String showCurrencyEditPage(@RequestParam(value = "id", required = false) Long currencyId, Model model) {
        if (currencyId != null) {
            Currency currency = currencyManager.getCurrencyById(currencyId);
            if (currency != null) {
                model.addAttribute("curr", currency);
            }
        } else {
            model.addAttribute("curr", null);
        }
        return CURRENCY_EDIT_VIEW;
    }

    //+
    @DeleteMapping("admin/currency/delete")
    public String deleteCurrencyById(@RequestParam("id") Long currencyId) {
        currencyManager.deleteCurrency(currencyManager.getCurrencyById(currencyId));
        return CURRENCY_INDEX_VIEW;
    }

    @PostMapping("/admin/currency/save")
    public String saveOrUpdateCurrency(@ModelAttribute("curr") Currency currency) {
        if (currency.getId() != null) {
            currencyManager.updateCurrency(currency);
        } else {
            currencyManager.saveCurrency(currency);
        }
        return "redirect:/admin/currency/index.html";
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
