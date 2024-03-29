package ru.teamscore.java23.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@NoArgsConstructor
@Entity
@EqualsAndHashCode
@Table(name = "currency_pair", schema = "currencies")
public class CurrencyPair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id = 0L;

    @Setter
    @OneToMany(mappedBy = "currencyPair")
    private List<ExchangeRate> exchangeRateHistory;

    @Setter
    @Column(name = "precision", nullable = false)
    private int precision;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "base_currency_id", nullable = false, referencedColumnName = "id")
    private Currency baseCurrency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quoted_currency_id", nullable = false, referencedColumnName = "id")
    private Currency quotedCurrency;

    public CurrencyPair(Currency baseCurrency, Currency currency, int precision) {
        this.exchangeRateHistory = new ArrayList<>();
        this.baseCurrency = baseCurrency;
        this.quotedCurrency = currency;
        this.precision = precision;
    }

    public CurrencyPair(Long id, Currency baseCurrency, Currency currency, int precision) {
        this(baseCurrency, currency, precision);
        this.id = id;
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

    public List<ExchangeRate> getExchangeRates(LocalDateTime start, LocalDateTime end) {
        return this.exchangeRateHistory.stream()
                .filter(date -> date.getLocalDateTime().isAfter(start) ||
                        date.getLocalDateTime().isEqual(start))
                .filter(date -> date.getLocalDateTime().isBefore(end))
                .collect(Collectors.toList());
    }

    public List<ExchangeRate> getLastExchangeRatesInRange(int range) {
        return this.exchangeRateHistory.stream()
                .skip(Math.max(0, exchangeRateHistory.size() - range))
                .collect(Collectors.toList());
    }


}
