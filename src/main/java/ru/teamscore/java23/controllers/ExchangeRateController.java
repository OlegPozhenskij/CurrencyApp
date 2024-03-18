package ru.teamscore.java23.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.teamscore.java23.models.Currency;
import ru.teamscore.java23.models.CurrencyPair;
import ru.teamscore.java23.models.ExchangeRate;
import ru.teamscore.java23.services.CurrencyManager;
import ru.teamscore.java23.services.CurrencyPairManager;
import ru.teamscore.java23.services.ExchangeRateManager;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ExchangeRateController {
    private static final String EXCHANGE_RATE_INDEX_VIEW = "/admin/exchange_rate/index";
    private static final String EXCHANGE_RATE_EDIT_VIEW = "/admin/exchange_rate/edit";

    @Autowired
    private CurrencyPairManager currencyPairManager;

    @Autowired
    private ExchangeRateManager exchangeRateManager;

    @Autowired
    private CurrencyManager currencyManager;

    @GetMapping("admin/exchange_rate/index.html")
    public String showExchangeRateIndexPage(Model model) {
        var s = currencyPairManager.getAllCurrencyPairs();

        model.addAttribute("currencyPairs", s);
        return EXCHANGE_RATE_INDEX_VIEW;
    }

    @GetMapping("admin/exchange_rate/edit.html")
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
        return EXCHANGE_RATE_EDIT_VIEW;
    }

    @DeleteMapping("admin/exchange_rate/delete")
    public String deleteExchangeRateById(@RequestParam("id") Long exchangeRateId) {
        exchangeRateManager.deleteExchangeRateById(exchangeRateId);
        return EXCHANGE_RATE_INDEX_VIEW;
    }

    @PostMapping("/admin/exchange_rate/save")
    public String saveOrUpdateExchangeRate(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam("dateTimeInput") LocalDateTime dateTime,
            @RequestParam("exchangeRateInput") BigDecimal exchangeRate,
            @RequestParam("currencyPairSelect") Long currencyPairCode) {

        System.out.println(id);
        System.out.println(dateTime);
        System.out.println(exchangeRate);
        System.out.println(currencyPairCode);

        // Создать объект ExchangeRate
        ExchangeRate exchangeRateObject = new ExchangeRate(
                dateTime,
                exchangeRate,
                currencyPairManager.getCurrencyPairById(currencyPairCode));

        // Установить ID, если он указан
        if (id != null) {
            exchangeRateObject.setId(id);
            exchangeRateManager.updateExchangeRate(exchangeRateObject);
        } else {
            exchangeRateManager.saveRate(exchangeRateObject);
        }

        // Вернуть представление для редактирования курса обмена
        return "redirect:/admin/exchange_rate/index.html";
    }

}
