package ru.teamscore.java23.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.teamscore.java23.models.Currency;

import java.util.List;

@Repository
public interface CurrencyRepository extends CrudRepository<Currency, Long> {

    @Query("from Currency c where c.shortTitle = :shortTitle")
    Currency findCurrencyByShortTitle(String shortTitle);

    @Query("SELECT c FROM Currency c WHERE c.fullTitle LIKE :nameSubstring")
    List<Currency> findCurrenciesByNameSubstring(String nameSubstring);
}
