package com.example.fusalmatching.config;

import com.example.fusalmatching.config.jwt.JwtAuthenticationFilter;
import com.example.fusalmatching.config.jwt.JwtTokenProvider;
import com.example.fusalmatching.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(JwtTokenProvider jwtProvider) {
        this.jwtTokenProvider = jwtProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .httpBasic().disable()
                .cors(Customizer.withDefaults())
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .antMatchers("/teams").permitAll()
                .antMatchers("/stadiums/**").permitAll()
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("/teams/login").permitAll()
                .antMatchers("/teams/new").permitAll()
                .antMatchers("/teams/**").permitAll()
                .antMatchers("/review/**").permitAll()
                .antMatchers("/matching/**").permitAll() //나중에 없애야함 개발의 편의를 위해 설정
                .antMatchers("/teams/test").hasRole("[USER]")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();

//        return http
//                .cors(Customizer.withDefaults())
//                .csrf().disable()
//                .httpBasic().disable()
//                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
//                .formLogin().and()
//                .build();

    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    public void configure(AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder) throws Exception {
//        auth.inMemoryAuthentication()
//                .passwordEncoder(passwordEncoder)
//                .withUser("666")
//                .password("$2a$10$Fx0OPnvZv1wN2njYdcY0gOV6nUgqdTgfUVIBtm.ncbyOX4dWp8FN.")
//                .roles("USER");
        auth.userDetailsService(customUserDetailsService);

    }

}
