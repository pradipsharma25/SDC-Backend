package com.api.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.demo.model.Event;

public interface EventRepository extends JpaRepository<Event, Integer> {

}
