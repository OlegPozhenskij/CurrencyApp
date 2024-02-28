package ru.teamscore.java23.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;



@Getter
@ToString
@AllArgsConstructor(staticName = "load")
@NoArgsConstructor
@Entity
@Table(name = "currency", schema = "currencies")
@NamedQuery(name = "currenciesCount", query = "select count(*) from Currency")
@NamedQuery(name = "currencyByShortTitle", query = "from Currency c where c.shortTitle = :shortTitle")
public class Currency {

    @Id
    @SequenceGenerator(name = "seq", sequenceName = "currency_id_seq", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
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
