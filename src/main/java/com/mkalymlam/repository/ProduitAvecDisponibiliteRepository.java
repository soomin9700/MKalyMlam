package com.mkalymlam.repository;

import com.mkalymlam.entity.ProduitAvecDisponibilite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProduitAvecDisponibiliteRepository extends JpaRepository<ProduitAvecDisponibilite, Long> {

    // Filtrer par nom
    List<ProduitAvecDisponibilite> findByNomProduitContainingIgnoreCase(String nomProduit);

    // Filtrer par disponibilité
    List<ProduitAvecDisponibilite> findByEstDisponible(Boolean estDisponible);

    // Filtrer par nouveauté
    List<ProduitAvecDisponibilite> findByEstNouveau(Boolean estNouveau);

    // Combinaison de filtres
    List<ProduitAvecDisponibilite> findByEstDisponibleAndEstNouveau(Boolean estDisponible, Boolean estNouveau);

    // Recherche avec plusieurs critères (JPQL)
    @Query(value = """
        SELECT * FROM produit_avec_disponibilite p 
        WHERE (:nomProduit IS NULL OR p."nomProduit" ILIKE CONCAT('%', :nomProduit, '%'))
        AND (:estDisponible IS NULL OR p."estDisponible" = :estDisponible)
        AND (:estNouveau IS NULL OR p."estNouveau" = :estNouveau)
        """, nativeQuery = true)
    List<ProduitAvecDisponibilite> findByCriteria(
        @Param("nomProduit") String nomProduit,
        @Param("estDisponible") Boolean estDisponible,
        @Param("estNouveau") Boolean estNouveau
    );
    // Compter les produits disponibles
    long countByEstDisponible(Boolean estDisponible);

    // Récupérer les produits les plus récents
    List<ProduitAvecDisponibilite> findTop10ByOrderByDateCreationDesc();
}