package com.RunCode.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRegisterResponse {

    private Long id;
    private String kakaoId;
    private String name;
    private String nickname;
    private String profileImage;
    private String type; // nullable

    @Data
    @AllArgsConstructor
    public static class ErrorRes {
        private String error;
    }
}

