package com.bank.authorization.controller;

import com.bank.authorization.dto.AuthenticationRequestDto;
import com.bank.authorization.entity.User;
import com.bank.authorization.service.AuthService;
import com.bank.authorization.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
    @PostMapping("/login")
    public ResponseEntity<?> createAuthToken(@RequestBody AuthenticationRequestDto authenticationRequestDto) {
        return authService.createAuthToken(authenticationRequestDto);
    }
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/user")
    public ResponseEntity<User> getCurrentUser(Principal principal) {
        return new ResponseEntity<>(userService.findByProfileId(Long.valueOf(principal.getName())), HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/users")
    public ResponseEntity addUser(@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Secured("ROLE_ADMIN")
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }
    @Secured("ROLE_ADMIN")
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }
    @Secured("ROLE_ADMIN")
    @PutMapping("/users")
    public ResponseEntity editUser(@RequestBody User user) {
        userService.updateUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        userService.deleteUser(userService.getUser(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
