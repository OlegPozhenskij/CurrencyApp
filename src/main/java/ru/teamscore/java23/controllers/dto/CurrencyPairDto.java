package ru.teamscore.java23.controllers.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.teamscore.java23.models.Currency;
import ru.teamscore.java23.models.CurrencyPair;
import ru.teamscore.java23.models.ExchangeRate;
import ru.teamscore.java23.services.CurrencyService;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class CurrencyPairDto {
    private Long id = 0L;
    private ExchangeRateListDto exchangeRateList;
    private int precision;
    private Currency baseCurrency;
    private Currency quotedCurrency;
    @Setter
    private CurrencyService currencyService;

    public CurrencyPairDto(CurrencyPair pair) {
        this.id = pair.getId();
        this.exchangeRateList = new ExchangeRateListDto(pair.getExchangeRateHistory()
                .stream()
                .map(ExchangeRateDto::new)
                .collect(Collectors.toList()));
        this.precision = pair.getPrecision();
        this.baseCurrency = pair.getBaseCurrency();
        this.quotedCurrency = pair.getQuotedCurrency();
    }

    public CurrencyPair toCurrencyPair() {
        return new CurrencyPair(
                id,
                currencyService.getCurrencyByShortTitle(baseCurrency.getShortTitle()),
                currencyService.getCurrencyByShortTitle(quotedCurrency.getShortTitle()),
                precision
        );
    }
}
