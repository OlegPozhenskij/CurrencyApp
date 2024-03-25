package ru.teamscore.java23.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.teamscore.java23.models.ExchangeRate;
import ru.teamscore.java23.repositories.ExchangeRateRepository;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ExchangeRateService {
    private final ExchangeRateRepository exchangeRateRepository;

    public void saveOrUpdateExchangeRate(@NonNull ExchangeRate exchangeRate) {
        exchangeRateRepository.save(exchangeRate);
    }

    public List<ExchangeRate> getAllExchangeRates() {
        return (List<ExchangeRate>) exchangeRateRepository.findAll();
    }

    public void deleteExchangeRateById(long id) {
        exchangeRateRepository.deleteById(id);
    }

    private void deleteExchangeRate(ExchangeRate exchangeRate) {
        exchangeRateRepository.delete(exchangeRate);
    }

    public Optional<ExchangeRate> getExchangeRateById(long id) {
        return exchangeRateRepository.findById(id);
    }

}
