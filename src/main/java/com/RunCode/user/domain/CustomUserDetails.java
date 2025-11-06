package com.RunCode.user.domain;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {
    private final Long userId; // DB PK
    private final String kakaoId; // username으로 사용
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Long userId, String kakaoId, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.kakaoId = kakaoId;
        this.authorities = authorities;
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public String getUsername() {
        return kakaoId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    @Override public String getPassword() { return null; }

}