package ru.teamscore.java23.entities;

import jakarta.persistence.*;
import lombok.*;
import ru.teamscore.java23.services.CurrencyConverter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "currency_pair", schema = "currencies")
public class CurrencyPair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @OneToMany(mappedBy = "currencyPair")
    private List<ExchangeRate> exchangeRateHistory;

    @Setter
    @Column(name = "precision")
    private int precision;

    @Setter
    @Column(name = "base_currency_short_title")
    @Convert(converter = CurrencyConverter.class)
    private Currency baseCurrency;

    @Setter
    @Column(name = "quoted_currency_short_title")
    @Convert(converter = CurrencyConverter.class)
    private Currency quotedCurrency;

    public CurrencyPair(long id, Currency baseCurrency, Currency currency, int precision) {
        this.id = id;
        this.baseCurrency = baseCurrency;
        this.quotedCurrency = currency;
        this.precision = precision;
    }

    public void addExchangeRate(@NonNull ExchangeRate rate) {
        this.exchangeRateHistory.add(rate);
    }

    public boolean deleteExchangeRate(@NonNull ExchangeRate rate) {
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
