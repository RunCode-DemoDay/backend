package com.RunCode.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CreateTokenResponse {
    private String accessToken;
    private String refreshToken;
}
