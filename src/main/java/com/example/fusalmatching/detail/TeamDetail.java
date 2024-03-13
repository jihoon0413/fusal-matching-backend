package com.example.fusalmatching.detail;

import com.example.fusalmatching.domain.Team;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

import static com.example.fusalmatching.dto.Role.USER;

public class TeamDetail implements UserDetails {

    private final Team team;

    public TeamDetail(Team team) {
        this.team = team;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        ArrayList<GrantedAuthority> auths = new ArrayList<>();
        auths.add(new SimpleGrantedAuthority("ROLE_" + team.getUserRole().getKey()));
        return auths;
    }

    @Override
    public String getPassword() {
        return team.getPassword();
    }

    @Override
    public String getUsername() {
        return team.getId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
