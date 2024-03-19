package ru.teamscore.java23.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.teamscore.java23.models.ExchangeRate;
import ru.teamscore.java23.services.CurrencyManager;
import ru.teamscore.java23.services.CurrencyPairManager;
import ru.teamscore.java23.services.ExchangeRateManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/admin/exchange_rate")
public class ExchangeRateController {
    private static final String INDEX_VIEW = "/admin/exchange_rate/index";
    private static final String EDIT_VIEW = "/admin/exchange_rate/edit";

    @Autowired
    private CurrencyPairManager currencyPairManager;

    @Autowired
    private ExchangeRateManager exchangeRateManager;

    @Autowired
    private CurrencyManager currencyManager;

    @GetMapping("/index.html")
    public String showExchangeRateIndexPage(Model model) {
        var s = currencyPairManager.getAllCurrencyPairs();
        model.addAttribute("currencyPairs", s);
        return INDEX_VIEW;
    }

    @GetMapping("/edit.html")
    public String showExchangeRateEditPage(@RequestParam(value = "id", required = false) Long exchangeRateId, Model model) {
        if (exchangeRateId != null) {
            var rate = exchangeRateManager.getExchangeRateById(exchangeRateId);
            if (rate != null) {
                model.addAttribute("rate", rate);
            }
        } else {
            model.addAttribute("rate", null);
        }

        model.addAttribute("pairs", currencyPairManager.getAllCurrencyPairs());
        return EDIT_VIEW;
    }

    @DeleteMapping("/delete")
    public String deleteExchangeRateById(@RequestParam("id") Long exchangeRateId) {
        exchangeRateManager.deleteExchangeRateById(exchangeRateId);
        return INDEX_VIEW;
    }

    @PostMapping("/save")
    public String saveOrUpdateExchangeRate(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam("dateTimeInput") LocalDateTime dateTime,
            @RequestParam("exchangeRateInput") BigDecimal exchangeRate,
            @RequestParam("currencyPairSelect") Long currencyPairCode) {

        System.out.println(id);
        System.out.println(dateTime);
        System.out.println(exchangeRate);
        System.out.println(currencyPairCode);

        ExchangeRate exchangeRateObject = new ExchangeRate(
                dateTime,
                exchangeRate,
                currencyPairManager.getCurrencyPairById(currencyPairCode));

        if (id != null) {
            exchangeRateObject.setId(id);
            exchangeRateManager.updateExchangeRate(exchangeRateObject);
        } else {
            exchangeRateManager.saveRate(exchangeRateObject);
        }

        return "redirect:/admin/exchange_rate/index.html";
    }

}
