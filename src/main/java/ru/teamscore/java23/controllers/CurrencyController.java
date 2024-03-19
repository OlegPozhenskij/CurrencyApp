package ru.teamscore.java23.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.teamscore.java23.models.Currency;
import ru.teamscore.java23.services.CurrencyManager;
import ru.teamscore.java23.services.CurrencyPairManager;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/currency")
public class CurrencyController {

    private static final String INDEX_VIEW = "admin/currency/index";
    private static final String EDIT_VIEW = "admin/currency/edit";
    private static final String REDIRECT_INDEX = "redirect:/admin/currency/index.html";

    @Autowired
    private CurrencyPairManager currencyPairManager;

    @Autowired
    private CurrencyManager currencyManager;

    @GetMapping("/index.html")
    public String showCurrencyIndexPage(Model model) {
        List<Currency> currencies = currencyManager.getAllCurrencies();
        model.addAttribute("currencies", currencies);
        return INDEX_VIEW;
    }

    @GetMapping("/edit.html")
    public String showCurrencyEditPage(@RequestParam(value = "id", required = false) Long currencyId, Model model) {
        Currency currency = currencyId != null ? currencyManager.getCurrencyById(currencyId) : new Currency();
        model.addAttribute("curr", currency);
        return EDIT_VIEW;
    }

    @DeleteMapping("/delete")
    public String deleteCurrencyById(@RequestParam("id") Long currencyId) {
        currencyManager.deleteCurrency(currencyManager.getCurrencyById(currencyId));
        return INDEX_VIEW;
    }

    @PostMapping("/save")
    public String saveOrUpdateCurrency(@ModelAttribute("curr") Currency currency) {
        if (currency.getId() != null) {
            currencyManager.updateCurrency(currency);
        } else {
            currencyManager.saveCurrency(currency);
        }
        return REDIRECT_INDEX;
    }

    @GetMapping("/list")
    @ResponseBody
    public List<String> getCurrencyList() {
        return currencyPairManager.getAllCurrencyPairs().stream()
                .map(pair -> pair.getBaseCurrency().getShortTitle() + "/" + pair.getQuotedCurrency().getShortTitle())
                .collect(Collectors.toList());
    }
}
