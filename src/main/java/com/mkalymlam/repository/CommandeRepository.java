package com.mkalymlam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mkalymlam.entity.Commande;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {
    // @Query("SELECT COALESCE(SUM(c.montantTotal), 0) FROM Commande c WHERE c.sessionTruck.idSession = :idSession")
    // Double sumMontantTotalByIdSession(@Param("idSession") Long idSession);
    @Query(value = "SELECT COALESCE(SUM(c.\"montantTotal\"), 0) FROM commande c WHERE c.\"idSession\" = :idSession", nativeQuery = true)
    Double sumMontantTotalByIdSession(@Param("idSession") Long idSession);

    List<Commande> findByStatutCommande_Libelle(String libelle);

    List<Commande> findByTypeCommande_Libelle(String libelle);

    List<Commande> findByStatutCommande_LibelleAndTypeCommande_Libelle(String statut, String type);
}
