package com.bank.authorization.service;

import com.bank.authorization.dto.AuthenticationRequestDto;
import com.bank.authorization.dto.AuthenticationResponseDto;
import com.bank.authorization.entity.User;
import com.bank.authorization.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService{
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> createAuthToken(@RequestBody AuthenticationRequestDto authenticationRequestDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequestDto.getUsername(),
                    authenticationRequestDto.getPassword()));
        } catch (BadCredentialsException e) {
            log.info("Получен неверный логин или пароль");
            return new ResponseEntity<>("Неверный логин или пароль", HttpStatus.UNAUTHORIZED);
        }
        User user = userService.findByProfileId(Long.valueOf(authenticationRequestDto.getUsername()));
        String token = jwtTokenUtil.generateToken(user);
        log.info("Пользователь {} успешно прошел аутентификацию", user.getProfile_id());
        return ResponseEntity.ok(new AuthenticationResponseDto(token));
    }
}
