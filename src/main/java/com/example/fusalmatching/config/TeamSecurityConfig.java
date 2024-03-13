package com.example.fusalmatching.config;

import com.example.fusalmatching.config.jwt.JwtAuthenticationFilter;
import com.example.fusalmatching.config.jwt.JwtTokenProvider;
import com.example.fusalmatching.detail.TeamDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@Order(2)
public class TeamSecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .httpBasic().disable()
                .cors(Customizer.withDefaults())
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .antMatchers("/teams/**").permitAll()
                .antMatchers("/stadiums/**").permitAll()
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("/review/**").permitAll()
                .antMatchers("/matching/**").permitAll() //TODO:나중에 없애야함 개발의 편의를 위해 설정
                .antMatchers("/teams/test").hasRole("[USER]")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }



    @Autowired
    private TeamDetailsService teamDetailsService;


    @Autowired
    public void configure(AuthenticationManagerBuilder auth, PasswordEncoder PasswordEncoder) throws Exception {
        auth.userDetailsService(teamDetailsService);
    }

}
