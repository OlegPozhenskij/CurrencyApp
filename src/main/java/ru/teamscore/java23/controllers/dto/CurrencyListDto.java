package ru.teamscore.java23.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.teamscore.java23.models.Currency;

import java.util.List;

@Data
@AllArgsConstructor
public class CurrencyListDto {
    private List<CurrencyDto> currencyList;
}
