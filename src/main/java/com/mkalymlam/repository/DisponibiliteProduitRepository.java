package com.mkalymlam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mkalymlam.entity.DisponibiliteProduit;

public interface DisponibiliteProduitRepository
        extends JpaRepository<DisponibiliteProduit, Long> {
            
}