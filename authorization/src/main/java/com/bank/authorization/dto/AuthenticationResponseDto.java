package com.bank.authorization.dto;

import lombok.Data;

@Data
public class AuthenticationResponseDto {
    private String token;
    public AuthenticationResponseDto(String token) {
        this.token = token;
    }
}
