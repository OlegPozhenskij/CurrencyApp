package ru.teamscore.java23.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.teamscore.java23.models.Currency;
import ru.teamscore.java23.models.CurrencyPair;
import ru.teamscore.java23.services.CurrencyManager;
import ru.teamscore.java23.services.CurrencyPairManager;
import ru.teamscore.java23.services.ExchangeRateManager;

import java.util.List;

@Controller
public class CurrencyPairController {
    private static final String CURRENCY_PAIR_INDEX_VIEW = "admin/currency_pair/index";
    private static final String CURRENCY_PAIR_EDIT_VIEW = "admin/currency_pair/edit";

    @Autowired
    private CurrencyPairManager currencyPairManager;

    @Autowired
    private ExchangeRateManager exchangeRateManager;

    @Autowired
    private CurrencyManager currencyManager;

    @GetMapping("admin/currency_pair/index.html")
    public String showCurrencyPairIndexPage(Model model) {
        List<CurrencyPair> pairs = currencyPairManager.getAllCurrencyPairs();
        model.addAttribute("pairs", pairs);
        return CURRENCY_PAIR_INDEX_VIEW;
    }

    @GetMapping("admin/currency_pair/edit.html")
    public String showCurrencyPairEditPage(@RequestParam(value = "id", required = false) Long currencyPairId, Model model) {
        if (currencyPairId != null) {
            CurrencyPair pair = currencyPairManager.getCurrencyPairById(currencyPairId);
            if (pair != null) {
                model.addAttribute("pair", pair);
            }
        } else {
            model.addAttribute("pair", null);
        }
        return CURRENCY_PAIR_EDIT_VIEW;
    }

    @DeleteMapping("admin/currency_pair/delete")
    public String deleteCurrencyPairById(@RequestParam("id") Long currencyId) {
        currencyPairManager.deleteCurrencyPairById(currencyId);
        return CURRENCY_PAIR_INDEX_VIEW;
    }

    @PostMapping("/admin/currency_pair/save")
    public String saveOrUpdateCurrency(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam("baseShortTitle") String baseShortTitle,
            @RequestParam("quotedShortTitle") String quotedShortTitle) {

        var cp = new CurrencyPair(
                currencyManager.getCurrencyByShortTitle(baseShortTitle),
                currencyManager.getCurrencyByShortTitle(quotedShortTitle),
                3
        );

        if (id != null) {
            cp.setId(id);
            currencyPairManager.updateCurrencyPair(cp);
        } else {
            currencyPairManager.saveCurrencyPair(cp);
        }
        return CURRENCY_PAIR_INDEX_VIEW; // Перенаправляем на страницу списка валют
    }

}
