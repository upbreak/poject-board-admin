package com.fastcampus.projectboardadmin.config;

import com.fastcampus.projectboardadmin.dto.security.BoardAdminPrincipal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.ofNullable(SecurityContextHolder.getContext())//SecurityContextHolder - 세큐리티의 모든 정보를 가지는 클래스
                .map(SecurityContext::getAuthentication)//인증정보 확인
                .filter(Authentication::isAuthenticated)//인증 되었는지 확인
                .map(Authentication::getPrincipal)//로그인 정보 가져오기
                .map(BoardAdminPrincipal.class::cast)//커스텀 principal로 캐스트
                .map(BoardAdminPrincipal::getUsername);
    }
}
