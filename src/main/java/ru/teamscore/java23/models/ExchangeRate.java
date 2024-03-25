package ru.teamscore.java23.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@EqualsAndHashCode
@Table(name = "exchange_rate", schema = "rates")
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id = 0L;

    @Column(name = "local_date_time", nullable = false)
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

    public ExchangeRate(Long id, LocalDateTime localDateTime, BigDecimal rateVal, CurrencyPair currencyPair) {
        this(localDateTime, rateVal, currencyPair);
        this.id = id;
    }
}
