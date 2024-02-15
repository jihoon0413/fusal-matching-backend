package com.example.fusalmatching.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@ToString
@Getter
@Entity
public class Manager extends AuditingFields{

    @Id
    @Setter
    private String id;

    @Setter private String password;

    @OneToMany(mappedBy = "manager")
    @ToString.Exclude
    private final Set<Stadium> stadiums = new LinkedHashSet<>();





    public Manager() {}

    protected Manager(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public static Manager of(String id, String password) {

        return new Manager(id, password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manager manager = (Manager) o;
        return Objects.equals(id, manager.id) && Objects.equals(password, manager.password) && Objects.equals(stadiums, manager.stadiums);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, password, stadiums);
    }
}
