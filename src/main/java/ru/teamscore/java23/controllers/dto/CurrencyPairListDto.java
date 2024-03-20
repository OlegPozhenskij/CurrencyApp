package ru.teamscore.java23.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CurrencyPairListDto {
    private List<CurrencyPairDto> pairList;
}
