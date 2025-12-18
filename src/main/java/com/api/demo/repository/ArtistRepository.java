package com.api.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.demo.model.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Integer>{

}
