package com.mkalymlam.repository;

import com.mkalymlam.entity.TypeCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeCommandeRepository extends JpaRepository<TypeCommande, Long> {
    
    Optional<TypeCommande> findByLibelle(String libelle);
}