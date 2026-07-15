package com.mkalymlam.repository;

import com.mkalymlam.entity.LigneCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LigneCommandeRepository extends JpaRepository<LigneCommande, Long> {

    List<LigneCommande> findByIdCommande(Long idCommande);

    @Query("""
        SELECT l.idProduit, SUM(l.quantite) as totalQuantite
        FROM LigneCommande l
        GROUP BY l.idProduit
        ORDER BY totalQuantite DESC
        """)
    List<Object[]> sumQuantiteByProduit();

    @Query("""
        SELECT l.idProduit, SUM(l.quantite) as totalQuantite
        FROM LigneCommande l
        JOIN Commande c ON c.idCommande = l.idCommande
        WHERE c.dateHeureCreation BETWEEN :debut AND :fin
        GROUP BY l.idProduit
        ORDER BY totalQuantite DESC
        """)
    List<Object[]> sumQuantiteByProduitBetween(
        @Param("debut") LocalDateTime debut,
        @Param("fin") LocalDateTime fin
    );

    @Query("""
        SELECT l.idProduit, COALESCE(SUM(l.quantite * l.prixUnitaireFacture), 0) as totalCA
        FROM LigneCommande l
        GROUP BY l.idProduit
        ORDER BY totalCA DESC
        """)
    List<Object[]> sumCAByProduit();

    @Query("""
        SELECT l.idProduit, COALESCE(SUM(l.quantite * l.prixUnitaireFacture), 0) as totalCA
        FROM LigneCommande l
        JOIN Commande c ON c.idCommande = l.idCommande
        WHERE c.dateHeureCreation BETWEEN :debut AND :fin
        GROUP BY l.idProduit
        ORDER BY totalCA DESC
        """)
    List<Object[]> sumCAByProduitBetween(
        @Param("debut") LocalDateTime debut,
        @Param("fin") LocalDateTime fin
    );

    @Query(value = """
        SELECT 
            p."idProduit",
            p."nomProduit",
            COALESCE(SUM(l.quantite), 0) as quantiteTotale,
            COALESCE(SUM(l.quantite * l."prixUnitaireFacture"), 0) as caTotal,
            CASE 
                WHEN COALESCE(SUM(l.quantite), 0) > 0 
                THEN COALESCE(SUM(l.quantite * l."prixUnitaireFacture"), 0) / COALESCE(SUM(l.quantite), 1)
                ELSE 0 
            END as prixMoyen
        FROM "ligneCommande" l
        JOIN "produit" p ON p."idProduit" = l."idProduit"
        GROUP BY p."idProduit", p."nomProduit"
        ORDER BY quantiteTotale DESC
        LIMIT 5
        """, nativeQuery = true)
    List<Object[]> findTop5Produits();

    @Query(value = """
        SELECT 
            p."idProduit",
            p."nomProduit",
            COALESCE(SUM(l.quantite), 0) as quantiteTotale,
            COALESCE(SUM(l.quantite * l."prixUnitaireFacture"), 0) as caTotal,
            CASE 
                WHEN COALESCE(SUM(l.quantite), 0) > 0 
                THEN COALESCE(SUM(l.quantite * l."prixUnitaireFacture"), 0) / COALESCE(SUM(l.quantite), 1)
                ELSE 0 
            END as prixMoyen
        FROM "ligneCommande" l
        JOIN "produit" p ON p."idProduit" = l."idProduit"
        JOIN "commande" c ON c."idCommande" = l."idCommande"
        WHERE c."dateHeureCreation" BETWEEN :debut AND :fin
        GROUP BY p."idProduit", p."nomProduit"
        ORDER BY quantiteTotale DESC
        LIMIT 5
        """, nativeQuery = true)
    List<Object[]> findTop5ProduitsBetween(
        @Param("debut") LocalDateTime debut,
        @Param("fin") LocalDateTime fin
    );

    @Query("SELECT COALESCE(SUM(l.quantite), 0) FROM LigneCommande l")
    Long sumTotalQuantite();

    @Query("""
        SELECT COALESCE(SUM(l.quantite), 0) 
        FROM LigneCommande l
        JOIN Commande c ON c.idCommande = l.idCommande
        WHERE c.dateHeureCreation BETWEEN :debut AND :fin
        """)
    Long sumTotalQuantiteBetween(
        @Param("debut") LocalDateTime debut,
        @Param("fin") LocalDateTime fin
    );

    @Query("""
        SELECT COALESCE(SUM(l.quantite), 0) 
        FROM LigneCommande l
        WHERE l.idProduit = :idProduit
        """)
    Long getQuantiteVendueByProduit(@Param("idProduit") Long idProduit);

    @Query("""
        SELECT COALESCE(SUM(l.quantite), 0) 
        FROM LigneCommande l
        JOIN Commande c ON c.idCommande = l.idCommande
        WHERE l.idProduit = :idProduit
        AND c.dateHeureCreation BETWEEN :debut AND :fin
        """)
    Long getQuantiteVendueByProduitBetween(
        @Param("idProduit") Long idProduit,
        @Param("debut") LocalDateTime debut,
        @Param("fin") LocalDateTime fin
    );
}