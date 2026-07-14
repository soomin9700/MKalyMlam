package com.mkalymlam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mkalymlam.entity.HistoriqueStatutCommande;

@Repository
public interface HistoriqueStatutCommandeRepository extends JpaRepository<HistoriqueStatutCommande, Long> {

    List<HistoriqueStatutCommande> findByCommande_IdCommandeOrderByDateChangementDesc(Long idCommande);
}
