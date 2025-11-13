package com.api.demo.backend;

import java.util.List;

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
public class RestApi {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TestRepository tRepo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/api/test")
    public List<Test> getApi() {
        return tRepo.findAll();
    }

    
    @GetMapping("/hello")
    public String test() {
    	return "Hello world";
    }
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                    request.getUsername(), request.getPassword()
//                )
//            );
//            return ResponseEntity.ok("Login successful!");
//        } catch (AuthenticationException e) {
//            return ResponseEntity.status(401).body("Invalid username or password");
//        }
//    }
    
//    @PostMapping("/register")
//    public ResponseEntity<String> registerUser(@RequestBody Test user) {
//        // Encrypt the password before saving
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//
//        // Save user in DB
//        tRepo.save(user);
//
//        return ResponseEntity.ok("User registered successfully!");
//    }
    @GetMapping("/register")
    public String getRegister() {
    	return "register";
    }
}
