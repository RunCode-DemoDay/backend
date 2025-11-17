package com.RunCode.login.config;

import com.RunCode.common.domain.ApiResponse;
import com.RunCode.login.config.jwt.TokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.Authentication;
import java.io.IOException;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final static String HEADER_AUTHORIZATION="Authorization";
    private final static String TOKEN_PREFIX="Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
        String token = (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX))
                ? authorizationHeader.substring(TOKEN_PREFIX.length())
                : null;

        if (token != null) {
            try {
                // 이 과정에서 ExpiredJwtException, JwtException 발생함
                tokenProvider.getClaims(token);

                // 토큰이 유효하면 Authentication 생성
                Authentication authentication = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (ExpiredJwtException ex) {
                // 토큰 만료 응답
                writeErrorResponse(response, 401, "엑세스 토큰이 만료되었습니다.");
                return;

            } catch (JwtException ex) {
                // JWT 자체가 변조, 파싱 오류 등
                writeErrorResponse(response, 401, "엑세스 토큰이 유효하지 않습니다.");
                return;

            } catch (Exception ex) {
                // 기타 예외
                writeErrorResponse(response, 401, "엑세스 토큰 처리 중 오류가 발생했습니다.");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private void writeErrorResponse(HttpServletResponse response, int code, String message) throws IOException {
        ApiResponse<Void> apiResponse = new ApiResponse<>(false, code, message, null);

        response.setStatus(code);
        response.setContentType("application/json;charset=UTF-8");

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(), apiResponse);
    }

    private String getAccessToken(String authorizationHeader){
        if(authorizationHeader!=null &&authorizationHeader.startsWith(TOKEN_PREFIX)){
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

}
