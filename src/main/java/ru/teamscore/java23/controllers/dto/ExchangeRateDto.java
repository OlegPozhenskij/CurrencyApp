package ru.teamscore.java23.controllers.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.teamscore.java23.models.CurrencyPair;
import ru.teamscore.java23.models.ExchangeRate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ExchangeRateDto {
    private Long id;
    private LocalDateTime localDateTime;
    private BigDecimal rateVal;

    public ExchangeRateDto(ExchangeRate rate) {
        this.id = rate.getId();
        this.localDateTime = rate.getLocalDateTime();
        this.rateVal = rate.getRateVal();
    }
}
