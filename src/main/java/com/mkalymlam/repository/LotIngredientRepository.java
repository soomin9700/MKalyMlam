package com.mkalymlam.repository;

import java.time.LocalDate;
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

    List<LotIngredient> findByDatePeremptionBetween(LocalDate startDate, LocalDate endDate);

    List<LotIngredient> findByDatePeremptionBetweenAndIngredient_IdIngredient(LocalDate startDate, LocalDate endDate,
            Long ingredientId);

    List<LotIngredient> findByDatePeremptionBefore(LocalDate date);

    @Query("SELECT COALESCE(SUM(l.quantiteInitiale), 0) FROM LotIngredient l WHERE l.ingredient.idIngredient = :idIngredient")
    Double sumQuantiteInitialeByIngredient(@Param("idIngredient") Long idIngredient);

    @Query("SELECT COALESCE(SUM(l.quantiteInitiale), 0) FROM LotIngredient l WHERE l.ingredient.idIngredient = :idIngredient AND l.typeMouvement.idTypeMouvement = :typeMouvementId")
    Double sumQuantiteInitialeByIngredientAndTypeMouvement(@Param("idIngredient") Long idIngredient,
            @Param("typeMouvementId") Long typeMouvementId);

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

    @Query("""
        SELECT COUNT(DISTINCT i.idIngredient)
        FROM Ingredient i
        WHERE EXISTS (
            SELECT l FROM LotIngredient l
            WHERE l.ingredient = i AND l.quantiteRestante > 0
        )
        """)
    Long countIngredientsDisponibles();

    @Query("""
        SELECT COUNT(DISTINCT i.idIngredient)
        FROM Ingredient i
        WHERE NOT EXISTS (
            SELECT l FROM LotIngredient l
            WHERE l.ingredient = i AND l.quantiteRestante > 0
        )
        """)
    Long countIngredientsEnRupture();

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

    @Query("SELECT COALESCE(SUM(l.quantiteRestante * l.prixAchatUnitaire), 0) FROM LotIngredient l")
    Double calculerValeurTotaleStock();
}
