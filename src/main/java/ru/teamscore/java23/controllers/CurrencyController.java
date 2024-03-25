package ru.teamscore.java23.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.teamscore.java23.controllers.dto.CurrencyDto;
import ru.teamscore.java23.controllers.dto.CurrencyListDto;
import ru.teamscore.java23.models.Currency;
import ru.teamscore.java23.services.CurrencyService;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/currency")
public class CurrencyController {
    private static final String INDEX_VIEW = "admin/currency/index";
    private static final String EDIT_VIEW = "admin/currency/edit";
    private static final String REDIRECT_INDEX = "redirect:/admin/currency/index";

    private final CurrencyService currencyService;

    @GetMapping("/index")
    public String showCurrencyIndexPage(Model model) {
        var currencies = new CurrencyListDto(currencyService.getAllCurrencies().stream()
                .map(CurrencyDto::new)
                .collect(Collectors.toList()));
        model.addAttribute("currencyDto", currencies);
        return INDEX_VIEW;
    }

    @GetMapping("/edit")
    public String showCurrencyEditPage(@RequestParam(value = "id", required = false) Long currencyId, Model model) {
        Optional<Currency> optCurr = currencyService.getCurrencyById(currencyId);
        var currency = optCurr.map(CurrencyDto::new).orElseGet(CurrencyDto::new);

        model.addAttribute("curr", currency);
        return EDIT_VIEW;
    }

    @PostMapping("/save")
    public String saveOrUpdateCurrency(@ModelAttribute("curr") CurrencyDto currency) {
        currencyService.saveOrUpdateCurrency(currency.toCurrency());
        return REDIRECT_INDEX;
    }

    @GetMapping("/delete")
    public String deleteCurrencyById(@RequestParam("id") Long currencyId) {
        Optional<Currency> optCurr = currencyService.getCurrencyById(currencyId);
        optCurr.orElseThrow(() -> new NoSuchElementException(
                "No such element with id=" + currencyId
        ));

        currencyService.deleteCurrency(optCurr.get());
        return REDIRECT_INDEX;
    }
}
