package com.example.fusalmatching.domain;

import com.example.fusalmatching.dto.Role;
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

    @Setter private String tel;

    @Setter private String email;

    @OneToMany(mappedBy = "manager")
    @ToString.Exclude
    private final Set<Stadium> stadiums = new LinkedHashSet<>();

    @Enumerated(EnumType.STRING)
    private Role userRole = Role.MANAGER;





    public Manager() {}

    protected Manager(String id, String password, String tel, String email) {
        this.id = id;
        this.password = password;
        this.tel = tel;
        this.email = email;

    }

    public static Manager of(String id, String password, String tel, String email) {

        return new Manager(id, password, tel, email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manager manager = (Manager) o;
        return Objects.equals(id, manager.id) && Objects.equals(password, manager.password) && Objects.equals(tel, manager.tel) && Objects.equals(email, manager.email) && Objects.equals(stadiums, manager.stadiums) && userRole == manager.userRole;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, password, tel, email, stadiums, userRole);
    }
}
