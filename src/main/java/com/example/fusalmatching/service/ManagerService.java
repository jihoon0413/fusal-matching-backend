package com.example.fusalmatching.service;

import com.example.fusalmatching.config.jwt.JwtToken;
import com.example.fusalmatching.config.jwt.JwtTokenProvider;
import com.example.fusalmatching.domain.Manager;
import com.example.fusalmatching.dto.request.ManagerSignRequestDto;
import com.example.fusalmatching.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Transactional
    public void createManager(ManagerSignRequestDto managerSignDto) throws Exception {

        try {
            String hashedPassword = passwordEncoder.encode(managerSignDto.getPassword());

            Manager manager = Manager.of(managerSignDto.getId(), hashedPassword, managerSignDto.getTel(), managerSignDto.getEmail());

            managerRepository.save(manager);
        } catch (Exception e) {
            throw new Exception("에러 발생");
        }

    }


    @Transactional
    public JwtToken login(String id, String password) {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(id, password);

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 검증된 인증 정보로 JWT 토큰 생성

        return jwtTokenProvider.generateToken(authentication, id);

    }


}
