package com.api.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.demo.model.Jazz;

public interface JazzRepository extends JpaRepository<Jazz, Integer> {

}
