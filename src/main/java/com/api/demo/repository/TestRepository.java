package com.api.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.api.demo.model.Test;
import java.util.Optional;

public interface TestRepository extends JpaRepository<Test, Integer> {
    Optional<Test> findByUsername(String username);
    Optional<Test> findByEmail (String email);
}
