package ru.teamscore.java23.controllers.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.teamscore.java23.models.Currency;
import ru.teamscore.java23.models.CurrencyPair;
import ru.teamscore.java23.models.ExchangeRate;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class CurrencyPairDto {
    private Long id = 0L;
    private ExchangeRateListDto exchangeRateList;
    private int precision;
    private CurrencyDto baseCurrency;
    private CurrencyDto quotedCurrency;

    public CurrencyPairDto(CurrencyPair pair) {
        this.id = pair.getId();
        this.exchangeRateList = new ExchangeRateListDto(pair.getExchangeRateHistory()
                .stream()
                .map(ExchangeRateDto::new)
                .collect(Collectors.toList()));
        this.precision = pair.getPrecision();
        this.baseCurrency = new CurrencyDto(pair.getBaseCurrency());
        this.quotedCurrency = new CurrencyDto(pair.getQuotedCurrency());
    }

}
