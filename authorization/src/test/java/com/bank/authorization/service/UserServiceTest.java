package com.bank.authorization.service;

import com.bank.authorization.entity.User;
import com.bank.authorization.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

public class UserServiceTest {
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    private User user;

    @BeforeEach
    public void setUpUser() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setProfileId(1L);
        user.setPassword("$2a$12$jE3L5cVqiz.iajldNZbHauoteJS9hI4nXW08PK2ld6y4rP2TbY7iC");
    }

    @Test
    void testSaveUser() {
        Mockito.when(userRepository.save(user)).thenReturn(user);
        User result = userService.saveUser(user);
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
    }

    @Test
    void testGetAllUsers() {
        Mockito.when(userRepository.findAll()).thenReturn(List.of(user));
        List<User> result = userService.getAllUsers();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testFindByProfileId() {
        Mockito.when(userRepository.findByProfileId(user.getProfileId())).thenReturn(user);
        User result = userService.findByProfileId(user.getProfileId());
        assertNotNull(result);
        assertEquals(user.getProfileId(), result.getProfileId());
    }

    @Test
    void testGetUser() {
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));
        User result = userService.getUser(user.getId());
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
    }

    @Test
    void testUpdateUser() {
        Mockito.when(userRepository.save(user)).thenReturn(user);
        User result = userService.updateUser(user);
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
    }

    @Test
    void testDeleteUser() {
        Mockito.when(userRepository.existsById(user.getId())).thenReturn(true);
        userService.deleteUser(user);
        Mockito.verify(userRepository).delete(user);
    }

    @Test
    void testSaveUserWithExistingId() {
        User newUser = new User();
        newUser.setId(1L);

        Mockito.when(userRepository.save(any(User.class)))
                .thenThrow(new IllegalArgumentException());

        assertThrows(IllegalArgumentException.class, () -> {
            userService.saveUser(newUser);
        });
    }

    @Test
    void testGetUserNotFound() {
        Long nonexistentId = 999L;
        Mockito.when(userRepository.findById(nonexistentId)).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () -> {
            userService.getUser(nonexistentId);
        });
    }
}
