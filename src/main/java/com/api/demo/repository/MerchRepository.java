package com.api.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.demo.model.Merch;

public interface MerchRepository extends JpaRepository<Merch, Integer>{

}
