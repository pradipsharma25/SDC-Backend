package com.api.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.demo.model.Music;

public interface MusicRepository extends JpaRepository<Music, Integer>{

}
