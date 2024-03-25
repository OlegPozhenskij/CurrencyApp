package ru.teamscore.java23.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.teamscore.java23.models.Currency;
import ru.teamscore.java23.repositories.CurrencyRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final CurrencyRepository currencyRepository;

    public void saveOrUpdateCurrency(@NonNull Currency currency) {
        currencyRepository.save(currency);
    }

    public void deleteCurrency(@NonNull Currency currency) {
        currencyRepository.deleteById(currency.getId());
    }

    public List<Currency> getAllCurrencies() {
        return (List<Currency>) currencyRepository.findAll();
    }

    public Optional<Currency> getCurrencyById(long id) {
        return currencyRepository.findById(id);
    }

    public long countCurrencies() {
        return currencyRepository.count();
    }

    public Currency getCurrencyByShortTitle(String shortTitle) {
        return currencyRepository.findCurrencyByShortTitle(shortTitle);
    }

    public List<Currency> getCurrenciesByNameSubstring(String nameSubstring) {
        return currencyRepository.findCurrenciesByNameSubstring(nameSubstring);
    }
}
