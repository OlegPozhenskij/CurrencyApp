package ru.teamscore.java23.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "exchange_rate", schema = "rates")
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "local_date_time")
    private LocalDateTime localDateTime;

    @Column(name = "rate_val", nullable = false)
    private BigDecimal rateVal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_pair_id", referencedColumnName = "id")
    private CurrencyPair currencyPair;

    public ExchangeRate(LocalDateTime localDateTime, BigDecimal rateVal, CurrencyPair currencyPair) {
        this.localDateTime = localDateTime;
        this.rateVal = rateVal;
        this.currencyPair = currencyPair;
    }
}
