package ru.teamscore.java23.entities;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@NoArgsConstructor
public class CurrencyPair {

    @Getter
    private Long id;

    @Getter
    @Setter
    private List<ExchangeRate> exchangeRateHistory;

    @Getter
    @Setter
    private int precision;

    @Getter
    @Setter
    private Currency baseCurrency;

    @Getter
    @Setter
    private Currency quotedCurrency;

    public CurrencyPair(Currency baseCurrency, Currency currency, int precision) {
        this.baseCurrency = baseCurrency;
        this.quotedCurrency = currency;
        this.precision = precision;
    }

    public void addExchangeRate(ExchangeRate rate) {
        this.exchangeRateHistory.add(rate);
    }

    public boolean deleteExchangeRate(ExchangeRate rate) {
        return this.exchangeRateHistory.remove(rate);
    }

    public int getExchangeRatesCount() {
        return this.exchangeRateHistory.size();
    }

    public List<ExchangeRate> getExchangeRates(LocalDateTime start, LocalDateTime end) {
        return this.exchangeRateHistory.stream()
                .filter(date -> date.getLocalDateTime().isAfter(start))
                .filter(date -> date.getLocalDateTime().isBefore(end))
                .collect(Collectors.toList());
    }

    public List<ExchangeRate> getExchangeRates(int range) {
        return this.exchangeRateHistory.stream()
                .skip(Math.max(0, exchangeRateHistory.size() - 5))
                .collect(Collectors.toList());
    }

}
