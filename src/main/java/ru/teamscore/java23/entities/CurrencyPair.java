package ru.teamscore.java23.entities;

import lombok.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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

    //проблема в том что при каждом поиске периода например в месяц, будут находиться 1 повторяющийся
    //ExchangeRate, поэтому нужно будет потом проверять на анличие одинаковых по времени ExchangeRate
    //и удолять их
    public List<ExchangeRate> getExchangeRates(LocalDateTime start, LocalDateTime end) {
        return this.exchangeRateHistory.stream()
                .filter(date -> date.getLocalDateTime().isAfter(start) ||   //Можн ещё над этим подумать
                        date.getLocalDateTime().isEqual(start))
                .filter(date -> date.getLocalDateTime().isBefore(end) ||
                        date.getLocalDateTime().isEqual(end))
                .collect(Collectors.toList());
    }

    public List<ExchangeRate> getExchangeRates(int range) {
        return this.exchangeRateHistory.stream()
                .skip(Math.max(0, exchangeRateHistory.size() - range))
                .collect(Collectors.toList());
    }

}
