package ru.teamscore.java23.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.teamscore.java23.controllers.dto.CurrencyDto;
import ru.teamscore.java23.controllers.dto.CurrencyListDto;
import ru.teamscore.java23.controllers.dto.CurrencyPairListDto;
import ru.teamscore.java23.models.Currency;
import ru.teamscore.java23.models.services.CurrencyManager;
import ru.teamscore.java23.models.services.CurrencyPairManager;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/currency")
public class CurrencyController {

    private static final String INDEX_VIEW = "admin/currency/index";
    private static final String EDIT_VIEW = "admin/currency/edit";
    private static final String REDIRECT_INDEX = "redirect:/admin/currency/index";

    @Autowired
    private CurrencyPairManager currencyPairManager;

    @Autowired
    private CurrencyManager currencyManager;

    @GetMapping("/index")
    public String showCurrencyIndexPage(Model model) {
        var currencies = new CurrencyListDto(currencyManager.getAllCurrencies().stream()
                .map(CurrencyDto::new)
                .collect(Collectors.toList()));
        model.addAttribute("currencyDto", currencies);
        return INDEX_VIEW;
    }

    @GetMapping("/edit")
    public String showCurrencyEditPage(@RequestParam(value = "id", required = false) Long currencyId, Model model) {
        var currency = currencyId != null
                ? new CurrencyDto(currencyManager.getCurrencyById(currencyId))
                : new CurrencyDto();
        model.addAttribute("curr", currency);
        return EDIT_VIEW;
    }

    @PostMapping("/save")
    public String saveOrUpdateCurrency(@ModelAttribute("curr") CurrencyDto currency) {
        currencyManager.saveOrUpdateCurrency(new Currency(
                currency.getId(),
                currency.getShortTitle(),
                currency.getFullTitle()));

        return REDIRECT_INDEX;
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public ResponseEntity<Object> deleteCurrencyById(@RequestParam("id") Long currencyId) {
        currencyManager.deleteCurrency(currencyManager.getCurrencyById(currencyId));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<List<String>> getCurrencyList() {
        return ResponseEntity.ok(currencyPairManager.getAllCurrencyPairs().stream()
                .map(pair -> pair.getBaseCurrency().getShortTitle() + "/" + pair.getQuotedCurrency().getShortTitle())
                .collect(Collectors.toList()));
    }
}
