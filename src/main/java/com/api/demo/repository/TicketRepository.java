package com.api.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.demo.model.Ticketbooking;

public interface TicketRepository extends JpaRepository<Ticketbooking, Integer>{
	

}
