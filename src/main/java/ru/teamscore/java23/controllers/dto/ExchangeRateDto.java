package ru.teamscore.java23.controllers.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import ru.teamscore.java23.models.CurrencyPair;
import ru.teamscore.java23.models.ExchangeRate;
import ru.teamscore.java23.repositories.CurrencyPairRepository;
import ru.teamscore.java23.services.CurrencyPairService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ExchangeRateDto {
    private Long id = 0L;
    private LocalDateTime localDateTime;
    private BigDecimal rateVal;
    private Long currencyPairCode;
    @Setter
    private CurrencyPairService currencyPairService;

    public ExchangeRateDto(ExchangeRate rate) {
        this.id = rate.getId();
        this.localDateTime = rate.getLocalDateTime();
        this.rateVal = rate.getRateVal();
    }

    public ExchangeRate toExchangeRate() {
        return new ExchangeRate(
                id,
                localDateTime,
                rateVal,
                currencyPairService.getCurrencyPairById(currencyPairCode).get()
        );
    }
}
