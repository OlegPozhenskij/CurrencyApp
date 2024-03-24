package ru.teamscore.java23.controllers.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StatisticParametersDto {
    private String currencyFirst;
    private String currencyLast;
    private String period;
    private String start;
    private String end;
    private Integer num;

    public StatisticParametersDto(String currencyFirst, String currencyLast, String period, String start, String end, Integer num) {
        this.currencyFirst = currencyFirst;
        this.currencyLast = currencyLast;
        this.period = period;
        this.start = start;
        this.end = end;
        this.num = num;
    }
}
