package ru.teamscore.java23.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.teamscore.java23.services.CurrencyManager;
import ru.teamscore.java23.services.CurrencyPairManager;

import org.springframework.beans.factory.annotation.Value;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Value("${admin.view}")
    private String adminView;

    @Autowired
    private CurrencyManager currencyManager;

    @Autowired
    private CurrencyPairManager currencyPairManager;

    @GetMapping
    public String showAdminPanel() {
        return adminView;
    }
}

