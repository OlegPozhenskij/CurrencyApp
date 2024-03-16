package ru.teamscore.java23.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.teamscore.java23.services.CurrencyPairManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class StatisticController {
    @Autowired
    private CurrencyPairManager currPair;

    @GetMapping({"", "/"})
    public String showUserPage() {
        System.out.println("Вернули пустую страницу");
        return "user";
    }

    @GetMapping("/stats")
    @ResponseBody
    public ResponseEntity<?> showStatisticsList(
            @RequestParam String currencyFirst,
            @RequestParam String currencyLast,
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam String period) {

        System.out.println("Пришёл запрос");
        System.out.println("Параметры:");
        System.out.println("currencyFirst: " + currencyFirst);
        System.out.println("currencyLast: " + currencyLast);
        System.out.println("start: " + start);
        System.out.println("end: " + end);
        System.out.println("period: " + period);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime dateStart = LocalDateTime.parse(start, formatter);
        LocalDateTime dateEnd = LocalDateTime.parse(end, formatter);

        System.out.println("start: " + dateStart);
        System.out.println("end: " + dateStart);

        Object statisticsData = currPair.getCurrencyStatistics(currencyFirst, currencyLast, dateStart, dateEnd, period);

        // Возвращаем имя представления для отображения данных
        return new ResponseEntity<>(statisticsData, HttpStatus.OK);
    }
}
