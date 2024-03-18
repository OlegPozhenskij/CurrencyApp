package ru.teamscore.java23.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.teamscore.java23.models.Currency;
import ru.teamscore.java23.services.CurrencyManager;
import ru.teamscore.java23.services.CurrencyPairManager;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final String ADMIN_PREFIX = "admin/";
    private static final String CURRENCY_INDEX_VIEW = ADMIN_PREFIX + "currency/index";
    private static final String CURRENCY_EDIT_VIEW = ADMIN_PREFIX + "currency/edit";
    private static final String ADMIN_VIEW = "admin";

    @Autowired
    private CurrencyManager currencyManager;

    @Autowired
    private CurrencyPairManager currencyPairManager;

    @GetMapping
    public String showAdminPanel() {
        System.out.println("Вернули страницу admin");
        return ADMIN_VIEW;
    }
}
