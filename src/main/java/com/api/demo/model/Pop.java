package com.api.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Pop {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

}
