package com.bank.authorization.service;

import com.bank.authorization.entity.User;
import com.bank.authorization.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }

    }

    @Override
    public User getUser(Long id) {
        try {
            return userRepository.findById(id).orElseThrow();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public void saveUser(User user) {
        try {
            if (user.getPassword().isEmpty()) {
                user.setPassword(userRepository.findById(user.getId()).orElseThrow().getPassword());
            } else {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            if (user.getRole() == null) {
                user.setRole("ROLE_USER");
            }
            userRepository.save(user);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public void updateUser(User user) {
        try {
            userRepository.save(user);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteUser(User user) {
        try {
            userRepository.delete(user);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public User findByProfileId(Long id) {
        try {
            return userRepository.findByProfile_id(id);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }
}
