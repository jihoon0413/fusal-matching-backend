package com.example.fusalmatching.config;


import com.example.fusalmatching.config.jwt.JwtAuthenticationFilter;
import com.example.fusalmatching.config.jwt.JwtTokenProvider;
import com.example.fusalmatching.detail.ManagerDetailsService;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@Order(1)
public class ManagerSecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain managerSecurityFilterChain(HttpSecurity http) throws Exception {

        http
                .httpBasic().disable()
                .cors(Customizer.withDefaults())
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .antMatchers("/managers/**").permitAll()
                .antMatchers("/managers/test").hasRole("[MANAGER]")
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }



    @Bean
    public static PasswordEncoder managerPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public ManagerDetailsService managerDetailsService;


    @Autowired
    public void configure(AuthenticationManagerBuilder auth, PasswordEncoder PasswordEncoder) throws Exception {
        auth.userDetailsService(managerDetailsService);

    }


}