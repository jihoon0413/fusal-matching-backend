package com.example.fusalmatching.service;

import com.example.fusalmatching.domain.Team;
import com.example.fusalmatching.dto.QuickGuideUser;
import com.example.fusalmatching.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final TeamRepository teamRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return teamRepository.findById(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
    }

    // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(Team team) {

//        return Team.builder()
//                .id(team.getId())
//                .password(String.valueOf(passwordEncoder.matches(team.getPassword(),team1.get().getPassword())))
////                        .encode(team.getPassword()))
//                .roles(team.getRoles())
//                .build();


        if (team == null) {


            throw new UsernameNotFoundException("is not found.");
        }

        QuickGuideUser quickGuideUser = new QuickGuideUser();
        quickGuideUser.setUsername(team.getId());
        quickGuideUser.setPassword(team.getPassword());
        quickGuideUser.setAuthorities(team.getAuthorities());
        quickGuideUser.setEnabled(true);
        quickGuideUser.setAccountNonExpired(true);
        quickGuideUser.setAccountNonLocked(true);
        quickGuideUser.setCredentialsNonExpired(true);

        return quickGuideUser;

    }
}