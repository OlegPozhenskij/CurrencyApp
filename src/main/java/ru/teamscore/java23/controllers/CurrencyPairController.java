package ru.teamscore.java23.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.teamscore.java23.controllers.dto.CurrencyPairDto;
import ru.teamscore.java23.controllers.dto.CurrencyPairListDto;
import ru.teamscore.java23.models.CurrencyPair;
import ru.teamscore.java23.models.services.CurrencyManager;
import ru.teamscore.java23.models.services.CurrencyPairManager;
import ru.teamscore.java23.models.services.ExchangeRateManager;

import java.util.List;
import java.util.stream.Collectors;

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
        List<CurrencyPairDto> pairs = currencyPairManager.getAllCurrencyPairs()
                .stream()
                .map(CurrencyPairDto::new)
                .collect(Collectors.toList());
        model.addAttribute("pairs", pairs);
        return INDEX_VIEW;
    }

    @GetMapping("/edit.html")
    public String showCurrencyPairEditPage(@RequestParam(value = "id", required = false) Long currencyPairId, Model model) {
        if (currencyPairId != null) {
            CurrencyPairDto pair = new CurrencyPairDto(currencyPairManager.getCurrencyPairById(currencyPairId));
            model.addAttribute("pair", pair);
        } else {
            model.addAttribute("pair", null);
        }
        return EDIT_VIEW;
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

    @DeleteMapping("/delete")
    @ResponseBody
    public ResponseEntity<Object> deleteCurrencyPairById(@RequestParam("id") Long currencyId) {
        currencyPairManager.deleteCurrencyPairById(currencyId);
        return ResponseEntity.ok().build();
    }

}
