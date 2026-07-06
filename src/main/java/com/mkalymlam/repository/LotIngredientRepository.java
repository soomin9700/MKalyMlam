package com.mkalymlam.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mkalymlam.entity.LotIngredient;

@Repository
public interface LotIngredientRepository extends JpaRepository<LotIngredient, Long> {

    List<LotIngredient> findByIngredient_IdIngredient(Long ingredientId);

    List<LotIngredient> findByIngredient_NomIngredientContainingIgnoreCase(String nomIngredient);

    List<LotIngredient> findAllByOrderByDatePeremptionAsc();

    List<LotIngredient> findAllByOrderByDateReceptionAsc();

    List<LotIngredient> findByDatePeremptionBetween(LocalDate startDate, LocalDate endDate);

    List<LotIngredient> findByDatePeremptionBetweenAndIngredient_IdIngredient(LocalDate startDate, LocalDate endDate,
            Long ingredientId);

    List<LotIngredient> findByDatePeremptionBefore(LocalDate date);

    @Query("SELECT COALESCE(SUM(l.quantiteInitiale), 0) FROM LotIngredient l WHERE l.ingredient.idIngredient = :idIngredient")
    Double sumQuantiteInitialeByIngredient(@Param("idIngredient") Long idIngredient);

    @Query("SELECT COALESCE(SUM(l.quantiteInitiale), 0) FROM LotIngredient l WHERE l.ingredient.idIngredient = :idIngredient AND l.typeMouvement.idTypeMouvement = :typeMouvementId")
    Double sumQuantiteInitialeByIngredientAndTypeMouvement(@Param("idIngredient") Long idIngredient,
            @Param("typeMouvementId") Long typeMouvementId);

}
