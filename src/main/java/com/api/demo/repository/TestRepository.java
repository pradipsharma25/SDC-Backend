package com.api.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.demo.model.Test;

public interface TestRepository extends JpaRepository<Test, Integer>{

}
