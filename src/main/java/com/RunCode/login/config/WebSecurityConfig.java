package com.RunCode.login.config;

import com.RunCode.login.config.jwt.TokenProvider;
import com.RunCode.login.config.oauth.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.RunCode.login.config.oauth.OAuth2SuccessHandler;
import com.RunCode.login.repository.RefreshTokenRepository;
import com.RunCode.login.service.OAuth2UserService;
import com.RunCode.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig {

    // Required Beans or Dependencies
    private final OAuth2UserService oAuth2UserCustomService;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // CSRF 보호 비활성화 (REST API에 필요 없을 경우)
                .csrf(AbstractHttpConfigurer::disable)

                // HTTP Basic 인증 비활성화
                .httpBasic(AbstractHttpConfigurer::disable)

                // 세션 비활성화 (JWT 및 OAuth2 사용 환경에서 STATELESS 추천)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 요청 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/oauth2/authorization/kakao","/login/oauth2/code/**",  "/auth/**").permitAll() // 인증 없이 접근 가능한 API
                        .anyRequest().permitAll() //authenticated()
                )

                // OAuth2 로그인을 위한 설정
                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(authEndpoint -> authEndpoint
                                .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository()) // 쿠키에 인증 요청 저장
                        )
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuth2UserCustomService) // 사용자 정보 서비스
                        )
                        .successHandler(oAuth2SuccessHandler()) // OAuth2 인증 성공 핸들러
                        .failureHandler((req, res, ex) -> {                  // ★ 추가: 실패 시 JSON
                            res.setStatus(401);
                            res.setContentType("application/json;charset=UTF-8");
                            res.getWriter().write("{\"error\":\"" + ex.getMessage() + "\"}");
                        })
                )

                // 커스텀 TokenAuthenticationFilter 추가 (JWT 인증 필터)
                .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)

                // SecurityFilterChain 빌드
                .build();
    }


    /**
     * SecurityFilterChain: Spring Security 설정
     */
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf(AbstractHttpConfigurer::disable) // CSRF 보호 비활성화
//                .httpBasic(AbstractHttpConfigurer::disable) // HTTP Basic 인증 비활성화
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 비활성화
//                .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class) // Custom Token Filter 추가
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/oauth2/authorization/kakao","/auth/**").permitAll() // 특정 API는 인증 없이 접근 허용
//                        .anyRequest().authenticated()  // 다른 요청들은 인증 필요
//                )
//                .oauth2Login(oauth2 -> oauth2
//                        .authorizationEndpoint(authEndpoint -> authEndpoint
//                                .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository()) // 쿠키에 인증 요청 저장
//                        )
//                        .userInfoEndpoint(userInfo -> userInfo
//                                .userService(oAuth2UserCustomService) // 사용자 정보 가져오기
//                        )
//                        .successHandler(oAuth2SuccessHandler()) // OAuth2 인증 성공 핸들러
//                )
//                .build(); // SecurityFilterChain 구성 완료
//    }

    /**
     * OAuth 성공 핸들러 (Bean 등록)
     */
    @Bean
    public OAuth2SuccessHandler oAuth2SuccessHandler() {
        return new OAuth2SuccessHandler(
                tokenProvider,
                refreshTokenRepository,
                oAuth2AuthorizationRequestBasedOnCookieRepository(),
                userService
        );
    }

    /**
     * OAuth2 인증 요청 쿠키 기반 Repository
     */
    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }

    /**
     * Token Authentication Filter 등록
     */
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }
}

