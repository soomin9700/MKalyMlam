package com.mkalymlam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mkalymlam.entity.Produit;

public interface ProduitRepository
        extends JpaRepository<Produit, Long> {

        @Query(value = "SELECT * FROM \"produit\" ORDER BY \"idProduit\" DESC LIMIT ?1", nativeQuery = true)
        List<Produit> findAllLimit(int limit);

        @Query("SELECT p FROM Produit p ORDER BY p.idProduit DESC")
        List<Produit> findPopularProducts();
        @Query("SELECT p FROM Produit p WHERE LOWER(p.nomProduit) LIKE LOWER(CONCAT('%', :nomProduit, '%'))")
        List<Produit> findByProduit_NomContainingIgnoreCase(@Param("nomProduit") String nomProduit);


        // List<Produit> findByIngredient_NomIngredientContainingIgnoreCase(String nomIngredient);

        // List<Produit> findAllByOrderByDatePeremptionAsc();

        // List<Produit> findAllByOrderByDateReceptionAsc();
}