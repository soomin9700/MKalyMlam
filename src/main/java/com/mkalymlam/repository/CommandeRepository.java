package com.mkalymlam.repository;

import com.mkalymlam.entity.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {

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