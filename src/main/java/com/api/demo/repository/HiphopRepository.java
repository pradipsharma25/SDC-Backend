package com.api.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.demo.model.Hiphop;

public interface HiphopRepository extends JpaRepository<Hiphop, Integer> {

}
