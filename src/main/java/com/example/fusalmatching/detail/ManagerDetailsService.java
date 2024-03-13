package com.example.fusalmatching.detail;

import com.example.fusalmatching.domain.Manager;
import com.example.fusalmatching.dto.QuickGuideUser;
import com.example.fusalmatching.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagerDetailsService implements UserDetailsService {

    private final ManagerRepository managerRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return managerRepository.findById(id)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
    }

    // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(Manager manager) {

        if (manager == null) {
            throw new UsernameNotFoundException("is not found.");
        }

        ManagerDetail managerDetail = new ManagerDetail(manager);

        QuickGuideUser quickGuideUser = new QuickGuideUser();
        quickGuideUser.setUsername(managerDetail.getUsername());
        quickGuideUser.setPassword(managerDetail.getPassword());
        quickGuideUser.setAuthorities(managerDetail.getAuthorities());
        quickGuideUser.setEnabled(managerDetail.isEnabled());
        quickGuideUser.setAccountNonExpired(managerDetail.isAccountNonExpired());
        quickGuideUser.setAccountNonLocked(managerDetail.isAccountNonLocked());
        quickGuideUser.setCredentialsNonExpired(managerDetail.isCredentialsNonExpired());

        return quickGuideUser;

    }
}
