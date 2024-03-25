package ru.teamscore.java23.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.teamscore.java23.services.CurrencyService;
import ru.teamscore.java23.services.CurrencyPairService;

import org.springframework.beans.factory.annotation.Value;

@Controller
public class HomePageController {

    @Value("${admin.view}")
    private String adminView;

    @Value("${user.view}")
    private String userView;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private CurrencyPairService currencyPairService;

    @GetMapping("/admin")
    public String showAdminPanel() {
        return adminView;
    }

    @GetMapping({"", "/"})
    public String showUserPage() {
        return userView;
    }
}

