package com.mkalymlam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mkalymlam.dto.IngredientStockDTO;
import com.mkalymlam.entity.LotIngredient;

@Repository
public interface LotIngredientRepository extends JpaRepository<LotIngredient, Long> {

    List<LotIngredient> findByIngredient_IdIngredient(Long ingredientId);

    List<LotIngredient> findByIngredient_NomIngredientContainingIgnoreCase(String nomIngredient);

    List<LotIngredient> findAllByOrderByDatePeremptionAsc();

    List<LotIngredient> findAllByOrderByDateReceptionAsc();

    @Query("SELECT COALESCE(SUM(l.quantiteRestante), 0) FROM LotIngredient l WHERE l.ingredient.idIngredient = :idIngredient")
    Double sumQuantiteRestanteByIdIngredient(@Param("idIngredient") Long idIngredient);

    // ✅ NOUVEAU : Récupérer les quantités totales par ingrédient
    @Query("""
        SELECT NEW com.mkalymlam.dto.IngredientStockDTO(
            i.idIngredient,
            i.nomIngredient,
            i.uniteMesure,
            i.seuilAlerteQuantite,
            COALESCE(SUM(l.quantiteRestante), 0),
            COALESCE(SUM(l.quantiteRestante * l.prixAchatUnitaire), 0)
        )
        FROM Ingredient i
        LEFT JOIN LotIngredient l ON l.ingredient = i
        GROUP BY i.idIngredient, i.nomIngredient, i.uniteMesure, i.seuilAlerteQuantite
        """)
    List<IngredientStockDTO> findAllIngredientsWithStock();

    // ✅ NOUVEAU : Compter les ingrédients disponibles (quantité > 0)
    @Query("""
        SELECT COUNT(DISTINCT i.idIngredient)
        FROM Ingredient i
        WHERE EXISTS (
            SELECT l FROM LotIngredient l
            WHERE l.ingredient = i AND l.quantiteRestante > 0
        )
        """)
    Long countIngredientsDisponibles();

    // ✅ NOUVEAU : Compter les ingrédients en rupture (quantité = 0 ou null)
    @Query("""
        SELECT COUNT(DISTINCT i.idIngredient)
        FROM Ingredient i
        WHERE NOT EXISTS (
            SELECT l FROM LotIngredient l
            WHERE l.ingredient = i AND l.quantiteRestante > 0
        )
        """)
    Long countIngredientsEnRupture();

    // ✅ NOUVEAU : Compter les ingrédients en alerte (quantité > 0 ET quantité <= seuil)
    @Query("""
        SELECT COUNT(DISTINCT i.idIngredient)
        FROM Ingredient i
        WHERE i.seuilAlerteQuantite IS NOT NULL
        AND EXISTS (
            SELECT l FROM LotIngredient l
            WHERE l.ingredient = i
            AND l.quantiteRestante > 0
            AND l.quantiteRestante <= i.seuilAlerteQuantite
        )
        """)
    Long countIngredientsEnAlerte();

    // ✅ NOUVEAU : Calculer la valeur totale des stocks
    @Query("SELECT COALESCE(SUM(l.quantiteRestante * l.prixAchatUnitaire), 0) FROM LotIngredient l")
    Double calculerValeurTotaleStock();
}