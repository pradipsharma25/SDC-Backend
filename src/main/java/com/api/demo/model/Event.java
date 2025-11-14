package com.api.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Event {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String pops;
	private String rock;
	private String hiphop;
	private String jazz;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPops() {
		return pops;
	}
	public void setPops(String pops) {
		this.pops = pops;
	}
	public String getRock() {
		return rock;
	}
	public void setRock(String rock) {
		this.rock = rock;
	}
	public String getHiphop() {
		return hiphop;
	}
	public void setHiphop(String hiphop) {
		this.hiphop = hiphop;
	}
	public String getJazz() {
		return jazz;
	}
	public void setJazz(String jazz) {
		this.jazz = jazz;
	}
	
	
	
}
