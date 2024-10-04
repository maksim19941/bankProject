package com.bank.authorization.service;

import com.bank.authorization.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserDetails user = null;
        try {
            user = userRepository.findByProfileId(Long.parseLong(username));
        } catch (UsernameNotFoundException e) {
            log.error("Имя пользователя не найдено", e);
        }
        return user;
    }
}
