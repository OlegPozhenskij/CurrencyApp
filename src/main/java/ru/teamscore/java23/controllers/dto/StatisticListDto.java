package ru.teamscore.java23.controllers.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.teamscore.java23.models.PriceStatistics;

import java.util.List;

@Data
@NoArgsConstructor
public class StatisticListDto {
    private List<StatisticDto> statsList;
}
