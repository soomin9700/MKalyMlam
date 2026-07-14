package com.mkalymlam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mkalymlam.entity.RecetteBase;
import com.mkalymlam.entity.RecetteBaseId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


@Repository
public interface RecetteBaseRepository extends JpaRepository<RecetteBase, RecetteBaseId> {

    @Query("""
        SELECT r FROM RecetteBase r 
        WHERE EXISTS (
            SELECT p FROM Produit p 
            WHERE p.idProduit = r.idProduit 
            AND LOWER(p.nomProduit) LIKE LOWER(CONCAT('%', :nomProduit, '%'))
        )
        """)
    List<RecetteBase> findByProduitNomContainingIgnoreCase(@Param("nomProduit") String nomProduit);

    @Query("""
        SELECT r FROM RecetteBase r 
        WHERE EXISTS (
            SELECT i FROM Ingredient i 
            WHERE i.idIngredient = r.idIngredient 
            AND LOWER(i.nomIngredient) LIKE LOWER(CONCAT('%', :nomIngredient, '%'))
        )
        """)
    List<RecetteBase> findByIngredientNomContainingIgnoreCase(@Param("nomIngredient") String nomIngredient);

    @Query("""
        SELECT r FROM RecetteBase r 
        WHERE EXISTS (
            SELECT p FROM Produit p 
            WHERE p.idProduit = r.idProduit 
            AND LOWER(p.nomProduit) LIKE LOWER(CONCAT('%', :nomProduit, '%'))
        )
        AND EXISTS (
            SELECT i FROM Ingredient i 
            WHERE i.idIngredient = r.idIngredient 
            AND LOWER(i.nomIngredient) LIKE LOWER(CONCAT('%', :nomIngredient, '%'))
        )
        """)
    List<RecetteBase> findByProduitAndIngredientNom(
        @Param("nomProduit") String nomProduit,
        @Param("nomIngredient") String nomIngredient
    );

    List<RecetteBase> findByIdProduit(Long idProduit);

    List<RecetteBase> findByIdIngredient(Long idIngredient);

    List<RecetteBase> findByIdProduitAndIdIngredient(Long idProduit, Long idIngredient);

    @Query("SELECT COUNT(r) FROM RecetteBase r WHERE r.idProduit = :idProduit")
    long countByIdProduit(@Param("idProduit") Long idProduit);

    @Query("SELECT COUNT(r) FROM RecetteBase r WHERE r.idIngredient = :idIngredient")
    long countByIdIngredient(@Param("idIngredient") Long idIngredient);
}