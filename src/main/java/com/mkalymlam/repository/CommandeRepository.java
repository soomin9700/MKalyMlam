package com.mkalymlam.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.mkalymlam.entity.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

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

    @Query("SELECT COUNT(c) FROM Commande c")
    long countCommandes();

    @Query("SELECT COUNT(c) FROM Commande c WHERE c.dateHeureCreation BETWEEN :debut AND :fin")
    long countCommandesBetween(@Param("debut") LocalDateTime debut, @Param("fin") LocalDateTime fin);

    @Query("SELECT COALESCE(SUM(c.montantTotal), 0) FROM Commande c")
    Double sumMontantTotal();

    @Query("SELECT COALESCE(SUM(c.montantTotal), 0) FROM Commande c WHERE c.dateHeureCreation BETWEEN :debut AND :fin")
    Double sumMontantTotalBetween(@Param("debut") LocalDateTime debut, @Param("fin") LocalDateTime fin);

    @Query("SELECT COALESCE(AVG(c.montantTotal), 0) FROM Commande c")
    Double averageMontantTotal();

    @Query("SELECT COALESCE(AVG(c.montantTotal), 0) FROM Commande c WHERE c.dateHeureCreation BETWEEN :debut AND :fin")
    Double averageMontantTotalBetween(@Param("debut") LocalDateTime debut, @Param("fin") LocalDateTime fin);

    @Query("""
        SELECT FUNCTION('DATE_FORMAT', c.dateHeureCreation, '%Y-%m') as mois, 
               COUNT(c) as total 
        FROM Commande c 
        GROUP BY FUNCTION('DATE_FORMAT', c.dateHeureCreation, '%Y-%m')
        ORDER BY mois DESC
        """)
    List<Object[]> countByMonth();

    @Query("""
        SELECT FUNCTION('DATE_FORMAT', c.dateHeureCreation, '%Y-%m') as mois, 
               COALESCE(SUM(c.montantTotal), 0) as total 
        FROM Commande c 
        GROUP BY FUNCTION('DATE_FORMAT', c.dateHeureCreation, '%Y-%m')
        ORDER BY mois DESC
        """)
    List<Object[]> sumByMonth();

    @Query("""
        SELECT DATE(c.dateHeureCreation) as jour, 
               COUNT(c) as totalCommandes,
               COALESCE(SUM(c.montantTotal), 0) as totalCA
        FROM Commande c 
        WHERE c.dateHeureCreation >= :dateDebut
        GROUP BY DATE(c.dateHeureCreation)
        ORDER BY jour ASC
        """)
    List<Object[]> findLast7DaysStats(@Param("dateDebut") LocalDateTime dateDebut);
}
