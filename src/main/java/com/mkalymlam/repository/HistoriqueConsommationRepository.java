package com.mkalymlam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mkalymlam.entity.HistoriqueConsommation;

@Repository
public interface HistoriqueConsommationRepository extends JpaRepository<HistoriqueConsommation, Long> {

    List<HistoriqueConsommation> findByCommande_IdCommande(Long idCommande);

    List<HistoriqueConsommation> findBySession_IdSession(Long idSession);

    List<HistoriqueConsommation> findByIngredient_IdIngredient(Long idIngredient);

    List<HistoriqueConsommation> findBySession_IdSessionAndIngredient_IdIngredient(Long idSession, Long idIngredient);
}
