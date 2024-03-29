package ru.teamscore.java23.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.teamscore.java23.controllers.dto.CurrencyPairDto;
import ru.teamscore.java23.controllers.dto.CurrencyPairListDto;
import ru.teamscore.java23.models.Currency;
import ru.teamscore.java23.models.CurrencyPair;
import ru.teamscore.java23.services.CurrencyService;
import ru.teamscore.java23.services.CurrencyPairService;

import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/currency_pair")
public class CurrencyPairController {
    private static final String INDEX_VIEW = "admin/currency_pair/index";
    private static final String EDIT_VIEW = "admin/currency_pair/edit";
    private static final String REDIRECT_INDEX = "redirect:/admin/currency_pair/index";

    private static final int PRECISION = 3;
    private final CurrencyPairService currencyPairService;
    private final CurrencyService currencyService;

    @GetMapping("/index")
    public String showCurrencyPairIndexPage(Model model) {
        var pairs = new CurrencyPairListDto(currencyPairService.getAllCurrencyPairs()
                .stream()
                .map(CurrencyPairDto::new)
                .collect(Collectors.toList()));
        model.addAttribute("pairs", pairs);
        return INDEX_VIEW;
    }

    @GetMapping("/edit")
    public String showCurrencyPairEditPage(@RequestParam(value = "id", required = false) Long currencyPairId, Model model) {
        var pairDto = currencyPairId != 0
                ? new CurrencyPairDto(currencyPairService.getCurrencyPairById(currencyPairId).get())
                : new CurrencyPairDto(new CurrencyPair(new Currency(), new Currency(), PRECISION));

        model.addAttribute("pair", pairDto);
        return EDIT_VIEW;
    }

    @PostMapping("/save")
    public String saveOrUpdateCurrencyPair(@ModelAttribute("pair") CurrencyPairDto pairDto) {
        pairDto.setCurrencyService(currencyService);
        currencyPairService.saveOrUpdateCurrencyPair(pairDto.toCurrencyPair());
        return REDIRECT_INDEX;
    }

    @GetMapping("/delete")
    public String deleteCurrencyPairById(@RequestParam("id") Long currencyId) {
        currencyPairService.deleteCurrencyPairById(currencyId);
        return REDIRECT_INDEX;
    }

}
