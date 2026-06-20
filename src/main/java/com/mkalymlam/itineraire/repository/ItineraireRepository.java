package com.mkalymlam.itineraire.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mkalymlam.itineraire.module.Itineraire;

@Repository
public interface ItineraireRepository extends JpaRepository<Itineraire, Long> {

}