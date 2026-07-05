package com.mkalymlam.repository;

import java.util.List;
import java.util.Optional;

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

    Optional<LotIngredient> findFirstByIngredient_IdIngredientOrderByDateReceptionDescIdLotDesc(Long ingredientId);

    @Query("SELECT COALESCE(SUM(l.quantiteRestante), 0) FROM LotIngredient l WHERE l.ingredient.idIngredient = :idIngredient")
    Double sumQuantiteRestanteByIdIngredient(@Param("idIngredient") Long idIngredient);
}
