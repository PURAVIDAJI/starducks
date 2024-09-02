package org.kosta.starducks.configs;

import org.kosta.starducks.auth.handler.CustomAccessDeniedHandler;
import org.kosta.starducks.auth.handler.CustomFailHandler;
import org.kosta.starducks.auth.handler.CustomSuccessHandler;
import org.kosta.starducks.auth.service.AccountService;
import org.kosta.starducks.commons.filters.AutoLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomFailHandler customFailHandler;

    @Autowired
    private AccountService accountService;  // AccountService (UserDetailsService) 등록

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = authenticationManager(http.getSharedObject(AuthenticationConfiguration.class));

        // AutoLoginFilter 추가 - 1004 계정으로 자동 로그인
        AutoLoginFilter autoLoginFilter = new AutoLoginFilter(accountService, authenticationManager);

        http
                // .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/forgotPwd").permitAll() // 비밀번호 찾기 등은 로그인 없이 접근 가능
                        .requestMatchers("/fina/**").hasAnyAuthority("FINA", "ROLE_BOSS")
                        .requestMatchers("/hr/**").hasAnyAuthority("HR", "ROLE_BOSS")
                        .requestMatchers("/logistic/**").hasAnyAuthority("LOGISTIC", "ROLE_BOSS")
                        .requestMatchers("/general/**").hasAnyAuthority("GENERAL", "ROLE_BOSS")
                        .requestMatchers("/**").authenticated()
                        .anyRequest().authenticated())
                .exceptionHandling((exceptions) -> exceptions
                        .accessDeniedHandler(accessDeniedHandler()))
                // 로그인 관련 설정 삭제
                .addFilterBefore(autoLoginFilter, UsernamePasswordAuthenticationFilter.class);  // AutoLoginFilter 추가

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return w -> w.ignoring().requestMatchers(
                "/images/**", "/css/**", "/js/**"
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
