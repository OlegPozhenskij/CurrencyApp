package ru.teamscore.java23.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.teamscore.java23.controllers.dto.CurrencyPairDto;
import ru.teamscore.java23.controllers.dto.CurrencyPairListDto;
import ru.teamscore.java23.controllers.dto.ExchangeRateDto;
import ru.teamscore.java23.controllers.dto.ExchangeRateListDto;
import ru.teamscore.java23.models.ExchangeRate;
import ru.teamscore.java23.models.services.CurrencyManager;
import ru.teamscore.java23.models.services.CurrencyPairManager;
import ru.teamscore.java23.models.services.ExchangeRateManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

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

    @GetMapping("/index")
    public String showExchangeRateIndexPage(Model model) {
        var pairsDto = new CurrencyPairListDto(currencyPairManager.getAllCurrencyPairs()
                .stream()
                .map(CurrencyPairDto::new)
                .collect(Collectors.toList()));
        model.addAttribute("currencyPairs", pairsDto);
        return INDEX_VIEW;
    }

    @GetMapping("/edit")
    public String showExchangeRateEditPage(@RequestParam(value = "id", required = false) Long exchangeRateId, Model model) {
        var rateDto = exchangeRateId != null
                ? new ExchangeRateDto(exchangeRateManager.getExchangeRateById(exchangeRateId))
                : new ExchangeRateDto();
        model.addAttribute("rate", rateDto);
        model.addAttribute("pairs", currencyPairManager.getAllCurrencyPairs());
        return EDIT_VIEW;
    }

    @PostMapping("/save")
    public String saveOrUpdateExchangeRate(@ModelAttribute("rate") ExchangeRateDto rateDto) {
        var exchangeRateObject = new ExchangeRate(
                rateDto.getId(),
                rateDto.getLocalDateTime(),
                rateDto.getRateVal(),
                currencyPairManager.getCurrencyPairById(rateDto.getCurrencyPairCode())
        );
        exchangeRateManager.saveOrUpdateExchangeRate(exchangeRateObject);

        return "redirect:/admin/exchange_rate/index";
    }

    @GetMapping("/delete")
    public String deleteExchangeRateById(@RequestParam("id") Long exchangeRateId) {
        exchangeRateManager.deleteExchangeRateById(exchangeRateId);
        return "redirect:/admin/exchange_rate/index";
    }

}
