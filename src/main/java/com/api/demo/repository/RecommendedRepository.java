package com.api.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.demo.model.Recommended;

public interface RecommendedRepository extends JpaRepository<Recommended, Integer> {

}
