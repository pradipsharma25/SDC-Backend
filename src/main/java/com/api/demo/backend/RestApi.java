package com.api.demo.backend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.demo.model.Test;
import com.api.demo.repository.TestRepository;

@RestController
public class RestApi {
	
	
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
	
}
