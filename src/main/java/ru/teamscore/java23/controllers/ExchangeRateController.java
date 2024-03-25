package ru.teamscore.java23.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.teamscore.java23.controllers.dto.CurrencyPairDto;
import ru.teamscore.java23.controllers.dto.CurrencyPairListDto;
import ru.teamscore.java23.controllers.dto.ExchangeRateDto;
import ru.teamscore.java23.models.CurrencyPair;
import ru.teamscore.java23.models.ExchangeRate;
import ru.teamscore.java23.services.CurrencyPairService;
import ru.teamscore.java23.services.ExchangeRateService;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/exchange_rate")
public class ExchangeRateController {
    private static final String INDEX_VIEW = "/admin/exchange_rate/index";
    private static final String EDIT_VIEW = "/admin/exchange_rate/edit";
    private static final String REDIRECT_INDEX = "redirect:/admin/exchange_rate/index";

    private final CurrencyPairService currencyPairService;
    private final ExchangeRateService exchangeRateService;

    @GetMapping("/index")
    public String showExchangeRateIndexPage(Model model) {
        var pairsDto = new CurrencyPairListDto(currencyPairService.getAllCurrencyPairs()
                .stream()
                .map(CurrencyPairDto::new)
                .collect(Collectors.toList()));
        model.addAttribute("currencyPairs", pairsDto);
        return INDEX_VIEW;
    }

    @GetMapping("/edit")
    public String showExchangeRateEditPage(@RequestParam(value = "id", required = false) Long exchangeRateId, Model model) {
        Optional<ExchangeRate> exchangeRate = exchangeRateService.getExchangeRateById(exchangeRateId);
        var rateDto = exchangeRate.map(ExchangeRateDto::new).orElseGet(ExchangeRateDto::new);

        model.addAttribute("rate", rateDto);
        model.addAttribute("pairs", currencyPairService.getAllCurrencyPairs());
        return EDIT_VIEW;
    }

    @PostMapping("/save")
    public String saveOrUpdateExchangeRate(@ModelAttribute("rate") ExchangeRateDto rateDto) {
        rateDto.setCurrencyPairService(currencyPairService);
        exchangeRateService.saveOrUpdateExchangeRate(rateDto.toExchangeRate());
        return REDIRECT_INDEX;
    }

    @GetMapping("/delete")
    public String deleteExchangeRateById(@RequestParam("id") Long exchangeRateId) {
        exchangeRateService.deleteExchangeRateById(exchangeRateId);
        return REDIRECT_INDEX;
    }
}
