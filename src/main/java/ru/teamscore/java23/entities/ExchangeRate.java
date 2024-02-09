package ru.teamscore.java23.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRate {

    private Long id;

    private LocalDateTime localDateTime;

    private BigDecimal rateVal;

    private CurrencyPair currencypair;
}
