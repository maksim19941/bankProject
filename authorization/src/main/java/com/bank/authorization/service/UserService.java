package com.bank.authorization.service;

import com.bank.authorization.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUser(Long id);
    User saveUser(User user);
    User updateUser(User user);
    void deleteUser(User user);
    User findByProfileId(Long id);
}
