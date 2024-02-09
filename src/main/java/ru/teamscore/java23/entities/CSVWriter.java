package ru.teamscore.java23.entities;

import java.util.List;


public class CSVWriter {
    public static String generateCSV(List<PriceStatistics> priceStatisticsList) {
        StringBuilder csvData = new StringBuilder();

        // Запись заголовка
        csvData.append("Временная метка,Открытие,Закрытие,Максимум,Минимум,Направление\n");

        // Запись данных
        for (PriceStatistics stats : priceStatisticsList) {
            csvData.append(String.format("%s,%s,%s,%s,%s,%c\n",
                    stats.getTimestamp(),
                    stats.getOpen(),
                    stats.getClose(),
                    stats.getMax(),
                    stats.getMin(),
                    stats.getDirection()));
        }

        return csvData.toString();
    }
}
