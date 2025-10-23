package com.api.demo.backend;

import java.util.List;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.demo.model.Test;
import com.api.demo.repository.TestRepository;

@RestController
public class RestApi {
	
	@Autowired
	private org.springframework.security.authentication.AuthenticationManager authenticationManager;

	@Autowired
	private TestRepository tRepo;
	
	@GetMapping("/api/test")
	public List<Test> getApi() {
		return tRepo.findAll();
	}
	
	@GetMapping("/api/tests")
    public String testApi() {
        return "API is working!";
    }
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) {
		
	    try {
	        Authentication authentication = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
	        );

	        // Authentication successful
	        return ResponseEntity.ok("Login successful!");
	    } catch (AuthenticationException e) {
	        // Authentication failed
	        return ResponseEntity.status(401).body("Invalid username or password");
	    }
	}

}
