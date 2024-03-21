package ru.teamscore.java23.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.teamscore.java23.controllers.dto.CurrencyDto;
import ru.teamscore.java23.controllers.dto.CurrencyPairDto;
import ru.teamscore.java23.controllers.dto.CurrencyPairListDto;
import ru.teamscore.java23.models.Currency;
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

    private static final int PRECISION = 3;

    @Autowired
    private CurrencyPairManager currencyPairManager;

    @Autowired
    private ExchangeRateManager exchangeRateManager;

    @Autowired
    private CurrencyManager currencyManager;

    @GetMapping("/index")
    public String showCurrencyPairIndexPage(Model model) {
        List<CurrencyPairDto> pairs = currencyPairManager.getAllCurrencyPairs()
                .stream()
                .map(CurrencyPairDto::new)
                .collect(Collectors.toList());
        model.addAttribute("pairs", pairs);
        return INDEX_VIEW;
    }

    @GetMapping("/edit")
    public String showCurrencyPairEditPage(@RequestParam(value = "id", required = false) Long currencyPairId, Model model) {
        CurrencyPairDto pairDto = currencyPairId != null
                ? new CurrencyPairDto(currencyPairManager.getCurrencyPairById(currencyPairId))
                : new CurrencyPairDto(new CurrencyPair(new Currency(), new Currency(), PRECISION));

        model.addAttribute("pair", pairDto);
        return EDIT_VIEW;
    }

    @PostMapping("/save")
    public String saveOrUpdateCurrencyPair(@ModelAttribute("pair") CurrencyPairDto pairDto) {
        var cp = new CurrencyPair(
                pairDto.getId(),
                currencyManager.getCurrencyByShortTitle(pairDto.getBaseCurrency().getShortTitle()),
                currencyManager.getCurrencyByShortTitle(pairDto.getQuotedCurrency().getShortTitle()),
                PRECISION
        );
        currencyPairManager.saveOrUpdateCurrencyPair(cp);

        return "redirect:/admin/currency_pair/index";
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public ResponseEntity<Object> deleteCurrencyPairById(@RequestParam("id") Long currencyId) {
        currencyPairManager.deleteCurrencyPairById(currencyId);
        return ResponseEntity.ok().build();
    }

}
