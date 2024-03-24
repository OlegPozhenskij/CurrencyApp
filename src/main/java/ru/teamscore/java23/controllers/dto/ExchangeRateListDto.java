package ru.teamscore.java23.controllers.dto;

import lombok.Data;

import java.util.List;

@Data
public class ExchangeRateListDto {
    private List<ExchangeRateDto> rateList;

    public ExchangeRateListDto(List<ExchangeRateDto> collect) {
        this.rateList = collect;
    }
}
