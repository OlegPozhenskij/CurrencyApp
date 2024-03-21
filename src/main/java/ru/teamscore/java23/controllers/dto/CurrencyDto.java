package ru.teamscore.java23.controllers.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import ru.teamscore.java23.models.Currency;

@Data
@NoArgsConstructor
public class CurrencyDto {
    private Long id = 0L;
    private String shortTitle;
    private String fullTitle;

    public CurrencyDto(Currency currency) {
        this.id = currency.getId();
        this.shortTitle = currency.getShortTitle();
        this.fullTitle = currency.getFullTitle();
    }
}
