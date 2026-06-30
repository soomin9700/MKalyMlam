package com.mkalymlam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mkalymlam.entity.Utilisateur;

public interface UtilisateurRepository
        extends JpaRepository<Utilisateur, Long> {
            
    Optional<Utilisateur> findByEmail(String email);
}