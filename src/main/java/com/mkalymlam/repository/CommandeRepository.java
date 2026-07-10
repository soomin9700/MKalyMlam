package com.mkalymlam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mkalymlam.entity.Commande;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {

    List<Commande> findByStatutCommande_Libelle(String libelle);

    List<Commande> findByTypeCommande_Libelle(String libelle);

    List<Commande> findByStatutCommande_LibelleAndTypeCommande_Libelle(String statut, String type);
}
