package com.mkalymlam.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    Optional<Commande> findByIdCommande(Long id);

    List<Commande> findByDateHeureCreationBetween(LocalDateTime debut, LocalDateTime fin);

    @Query("""
        SELECT c FROM Commande c
        JOIN c.sessionTruck st
        JOIN st.itineraire i
        WHERE c.statutCommande.libelle = 'LIVREE'
        AND (:dateDebut IS NULL OR c.dateHeureCreation >= :dateDebut)
        AND (:dateFin IS NULL OR c.dateHeureCreation <= :dateFin)
        AND (:idSession IS NULL OR st.id = :idSession)
        AND (:zone IS NULL OR i.nomZone = :zone)
        ORDER BY c.dateHeureCreation DESC
    """)
    List<Commande> findVentesFiltrees(
        @Param("dateDebut") LocalDateTime dateDebut,
        @Param("dateFin") LocalDateTime dateFin,
        @Param("idSession") Long idSession,
        @Param("zone") String zone
    );
}
