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
    public User saveUser(User user) {
        try {
            if (user.getRole() == null) {
                user.setRole("ROLE_USER");
            }
            userRepository.save(user);
            log.info("Saved user {}", user);
            return user;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public User updateUser(User user) {
        try {
            if (user.getPassword().isEmpty()) {
                user.setPassword(userRepository.findById(user.getId()).orElseThrow().getPassword());
            } else {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            userRepository.save(user);
            log.info("Updated user {}", user);
            return user;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteUser(User user) {
        try {
            userRepository.delete(user);
            log.info("Deleted user {}", user);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public User findByProfileId(Long id) {
        try {
            return userRepository.findByProfileId(id);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }
}
