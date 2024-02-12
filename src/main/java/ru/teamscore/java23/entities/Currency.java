package ru.teamscore.java23.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;


@Getter
@ToString
@AllArgsConstructor(staticName = "load")
@NoArgsConstructor
@Entity
@Table(name = "сurrency", schema = "сurrencies")
@NamedQuery(name = "currenciesCount", query = "select count(*) from Currency")
@NamedQuery(name = "currencyByShortTitle", query = "from Currency c where c.shortTitle = :shortTitle")
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    @Column(name = "short_title", nullable = false, unique = true)
    private String shortTitle;

    @Setter
    @Column(name = "full_title", nullable = false)
    private String fullTitle;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Currency currency)) return false;
        return Objects.equals(id, currency.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
