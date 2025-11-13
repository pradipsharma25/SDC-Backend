package com.api.demo.backend;

import com.api.demo.config.JwtUtil;
import com.api.demo.model.Test;
import com.api.demo.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @Autowired
    private JwtUtil jwtUtil;

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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            String loginId = (request.getUsername() != null && !request.getUsername().isEmpty())
                    ? request.getUsername()
                    : request.getEmail();
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginId, request.getPassword())
            );
            
            
            // To fetch the role of the user
            Test user = request.getUsername() != null && !request.getUsername().isEmpty()
                    ? tRepo.findByUsername(request.getUsername()).orElse(null)
                    : tRepo.findByEmail(request.getEmail()).orElse(null);
            
            if (user == null) {
                return ResponseEntity.status(404).body("User not found");
            }

            // ✅ Generate JWT if login successful
            String token = jwtUtil.generateToken(loginId);

            return ResponseEntity.ok(Map.of(
                    "message", "Login successful",
                    "token", token, "role",user.getRole()!=null ? user.getRole() : "test"
            ));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid username/email or password");
        }
    }

    // Example of a protected API endpoint
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        if (jwtUtil.validateToken(token)) {
            String username = jwtUtil.extractUsername(token);
            return ResponseEntity.ok("Welcome, " + username + "!");
        }
        return ResponseEntity.status(401).body("Invalid or expired token");
    }
}
