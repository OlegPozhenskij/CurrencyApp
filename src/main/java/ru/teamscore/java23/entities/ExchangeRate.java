package ru.teamscore.java23.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "exchange_rate", schema = "rates")
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "local_date_time")
    private LocalDateTime localDateTime;

    @Column(name = "rate_val")
    private BigDecimal rateVal;

    @ManyToOne
    @JoinColumn(name = "currency_pair_id", referencedColumnName = "id")
    private CurrencyPair currencyPair;
}
