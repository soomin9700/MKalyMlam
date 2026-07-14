package com.mkalymlam.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mkalymlam.entity.LotIngredient;

@Repository
public interface LotIngredientRepository extends JpaRepository<LotIngredient, Long> {

        List<LotIngredient> findAllByOrderByDatePeremptionAsc();

        @EntityGraph(attributePaths = "ingredient")
        List<LotIngredient> findAllByOrderByDateReceptionAsc();

        @EntityGraph(attributePaths = "ingredient")
        List<LotIngredient> findByIngredient_IdIngredient(Long ingredientId);

        @EntityGraph(attributePaths = "ingredient")
        List<LotIngredient> findByIngredient_NomIngredientContainingIgnoreCase(String nomIngredient);

        @EntityGraph(attributePaths = "ingredient")
        List<LotIngredient> findByDatePeremptionBetween(LocalDate startDate, LocalDate endDate);

        @EntityGraph(attributePaths = "ingredient")
        List<LotIngredient> findByDatePeremptionBetweenAndIngredient_IdIngredient(LocalDate startDate,
                        LocalDate endDate,
                        Long ingredientId);

        @EntityGraph(attributePaths = "ingredient")
        List<LotIngredient> findByDatePeremptionBefore(LocalDate date);

        @Query("SELECT COALESCE(SUM(l.quantiteInitiale), 0) FROM LotIngredient l WHERE l.ingredient.idIngredient = :idIngredient")
        Double sumQuantiteRestanteByIdIngredient(@Param("idIngredient") Long idIngredient);

        @Query("SELECT COALESCE(SUM(l.quantiteInitiale), 0) FROM LotIngredient l WHERE l.ingredient.idIngredient = :idIngredient")
        Double sumQuantiteInitialeByIngredient(@Param("idIngredient") Long idIngredient);

}
