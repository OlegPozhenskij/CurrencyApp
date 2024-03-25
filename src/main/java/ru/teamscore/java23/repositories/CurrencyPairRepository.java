package ru.teamscore.java23.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.teamscore.java23.models.Currency;
import ru.teamscore.java23.models.CurrencyPair;

@Repository
public interface CurrencyPairRepository extends CrudRepository<CurrencyPair, Long> {

    @Query("from CurrencyPair c where c.baseCurrency = :baseCurrency and c.quotedCurrency = :quotedCurrency")
    CurrencyPair findCurrencyPairsByCurrencyName(Currency baseCurrency, Currency quotedCurrency);

}
