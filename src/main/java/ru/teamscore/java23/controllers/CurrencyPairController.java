package ru.teamscore.java23.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.teamscore.java23.models.CurrencyPair;
import ru.teamscore.java23.services.CurrencyManager;
import ru.teamscore.java23.services.CurrencyPairManager;
import ru.teamscore.java23.services.ExchangeRateManager;

import java.util.List;

@Controller
@RequestMapping("/admin/currency_pair")
public class CurrencyPairController {
    private static final String INDEX_VIEW = "admin/currency_pair/index";
    private static final String EDIT_VIEW = "admin/currency_pair/edit";

    @Autowired
    private CurrencyPairManager currencyPairManager;

    @Autowired
    private ExchangeRateManager exchangeRateManager;

    @Autowired
    private CurrencyManager currencyManager;

    @GetMapping("/index.html")
    public String showCurrencyPairIndexPage(Model model) {
        List<CurrencyPair> pairs = currencyPairManager.getAllCurrencyPairs();
        model.addAttribute("pairs", pairs);
        return INDEX_VIEW;
    }

    @GetMapping("/edit.html")
    public String showCurrencyPairEditPage(@RequestParam(value = "id", required = false) Long currencyPairId, Model model) {
        if (currencyPairId != null) {
            CurrencyPair pair = currencyPairManager.getCurrencyPairById(currencyPairId);
            model.addAttribute("pair", pair);
        } else {
            model.addAttribute("pair", null);
        }
        return EDIT_VIEW;
    }

    @DeleteMapping("/delete")
    public String deleteCurrencyPairById(@RequestParam("id") Long currencyId) {
        currencyPairManager.deleteCurrencyPairById(currencyId);
        return INDEX_VIEW;
    }

    @PostMapping("/save")
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
        return "redirect:/admin/currency_pair/index.html";
    }

}
