package com.example.jeebackend.repository;

import com.example.jeebackend.model.Offre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OffreRepository extends JpaRepository<Offre, Long> {
    @Query("SELECT o FROM Offre o WHERE o.verified = true")
    List<Offre> findVerifiedOffres();
}
