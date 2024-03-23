package ru.teamscore.java23.controllers.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class StringListDto {
    private List<String> strList;

    public StringListDto(List<String> strList) {
        this.strList = strList;
    }

}
