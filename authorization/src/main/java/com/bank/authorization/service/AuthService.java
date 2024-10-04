package com.bank.authorization.service;

import com.bank.authorization.dto.AuthenticationRequestDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> createAuthToken(AuthenticationRequestDto authenticationRequestDto);
}
