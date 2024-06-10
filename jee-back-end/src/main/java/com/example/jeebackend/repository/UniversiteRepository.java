package com.example.jeebackend.repository;

import com.example.jeebackend.model.Universite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniversiteRepository extends JpaRepository<Universite, Long> {
}
