package org.kosta.starducks.commons.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.kosta.starducks.auth.service.AccountService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AutoLoginFilter extends OncePerRequestFilter {

    private final AccountService accountService;
    private final AuthenticationManager authenticationManager;

    public AutoLoginFilter(AccountService accountService, AuthenticationManager authenticationManager) {
        this.accountService = accountService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 이미 인증된 사용자는 필터를 통과시킨다
        Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();

        if (currentAuthentication == null || !currentAuthentication.isAuthenticated()) {
            try {
                // "1004" 사용자 로드
                UserDetails userDetails = accountService.loadUserByUsername("1004");

                // 인증 토큰 생성
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, userDetails.getPassword(), userDetails.getAuthorities());

                // 인증 처리
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                // 오류 발생 시 로그 출력 및 로그인 실패 처리
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Auto login failed");
                return;
            }
        }

        // 다음 필터로 요청을 전달
        filterChain.doFilter(request, response);
    }
}
