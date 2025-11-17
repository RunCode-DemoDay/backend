package com.RunCode.login.config.jwt;

import com.RunCode.user.domain.CustomUserDetails;
import com.RunCode.user.domain.User;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TokenProvider {

    private final JwtProperties jwtProperties;

    private Key key() {
        return Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(User user, Duration expiredAt) {
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
    }

    private String makeToken(Date expiry, User user) {
        return Jwts.builder()
                .setSubject(user.getKakaoId())              // subject = kakaoId
                .claim("userId", user.getId())              // DB PK
                .claim("nickname", user.getNickname())
                .claim("kakaoId", user.getKakaoId())
                .setIssuedAt(new Date())
                .setExpiration(expiry)
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** ✅ CustomUserDetails 로 Principal 구성 */
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);

        Long userId = claims.get("userId", Long.class);
        String kakaoId = claims.getSubject();

        Set<SimpleGrantedAuthority> auths =
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        CustomUserDetails principal = new CustomUserDetails(userId, kakaoId, auths);

        return new UsernamePasswordAuthenticationToken(principal, token, auths);
    }

    public Long getUserId(String token) {
        Claims claims = getClaims(token);
        return claims.get("userId", Long.class);
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token)
                .getBody();
    }
}