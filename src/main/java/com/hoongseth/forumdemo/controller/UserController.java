package com.hoongseth.forumdemo.controller;

import com.hoongseth.forumdemo.model.ForumUser;
import com.hoongseth.forumdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{username}")
    public ForumUser getUser(@PathVariable String username) {
        return userService.findByUsername(username).withoutPassword();
    }

    @GetMapping("/users/email/{email}")
    public ForumUser getUserByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }

    @PostMapping("/users/signup")
    public ResponseEntity<ForumUser> signUp(@RequestBody ForumUser forumUser) {
        Optional<ForumUser> newUser = userService.createNewUser(forumUser);

        return newUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT).build());
    }
}
