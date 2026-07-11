package com.mkalymlam.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mkalymlam.entity.Commande;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {

    List<Commande> findByStatutCommande_Libelle(String libelle);

    List<Commande> findByTypeCommande_Libelle(String libelle);

    List<Commande> findByStatutCommande_LibelleAndTypeCommande_Libelle(String statut, String type);

    Optional<Commande> findByIdCommande(Long id);

    List<Commande> findByDateHeureCreationBetween(LocalDateTime debut, LocalDateTime fin);
}
