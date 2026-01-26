package com.api.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.demo.model.Rock;

public interface RockRepository extends JpaRepository<Rock, Integer> {

}
