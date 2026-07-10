package com.mkalymlam.repository;

import com.mkalymlam.entity.StatutCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatutCommandeRepository extends JpaRepository<StatutCommande, Long> {
    
    Optional<StatutCommande> findByLibelle(String libelle);
}