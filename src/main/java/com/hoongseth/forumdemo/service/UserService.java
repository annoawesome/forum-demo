package com.hoongseth.forumdemo.service;

import com.hoongseth.forumdemo.model.ForumUser;
import com.hoongseth.forumdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ForumUser findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public ForumUser findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<ForumUser> createNewUser(ForumUser forumUser) {
        ForumUser existingUser = userRepository.findByUsername(forumUser.getUsername());

        if (existingUser != null) {
            return Optional.empty();
        }

        if (forumUser.getPassword() == null || !validatePassword(forumUser.getPassword())) {
            return Optional.empty();
        }

        forumUser.addRole("USER");
        // Securely hash password
        forumUser.setPassword(
                new BCryptPasswordEncoder(16).encode(forumUser.getPassword())
        );

        forumUser = userRepository.save(forumUser);
        return Optional.of(forumUser.withoutPassword());
    }

    private boolean validatePassword(String password) {
        // courtesy of ChatGPT
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,20}$");
    }
}
