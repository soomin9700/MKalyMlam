package com.mkalymlam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mkalymlam.entity.ActionCommande;

@Repository
public interface ActionCommandeRepository
        extends JpaRepository<ActionCommande, Long> {
}
