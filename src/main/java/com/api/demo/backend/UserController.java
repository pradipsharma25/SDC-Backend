package com.api.demo.backend;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.api.demo.model.Test;
import com.api.demo.repository.TestRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TestRepository tRepo;

    // ✅ Register new user
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Test user) {
        if (tRepo.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        if (tRepo.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        tRepo.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    // ✅ Login with either username or email
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            String loginId = null;

            if (request.getUsername() != null && !request.getUsername().isEmpty()) {
                loginId = request.getUsername();
            } else if (request.getEmail() != null && !request.getEmail().isEmpty()) {
                loginId = request.getEmail();
            } else {
                return ResponseEntity.badRequest().body("Please provide username or email");
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginId,
                            request.getPassword()
                    )
            );

            return ResponseEntity.ok("Login successful!");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid username/email or password");
        }
    }
}
