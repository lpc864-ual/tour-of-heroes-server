package com.example.demo.repository;

import com.example.demo.entity.Hero;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;;

@RepositoryRestResource 
public interface HeroRepository extends JpaRepository<Hero, Long> { 
    List<Hero> findByNameContainingIgnoreCase(String name);
}