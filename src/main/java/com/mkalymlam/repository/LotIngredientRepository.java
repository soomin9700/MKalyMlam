package com.mkalymlam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mkalymlam.entity.LotIngredient;

@Repository
public interface LotIngredientRepository extends JpaRepository<LotIngredient, Long> {

    List<LotIngredient> findByIngredient_IdIngredient(Long ingredientId);

    List<LotIngredient> findByIngredient_NomIngredientContainingIgnoreCase(String nomIngredient);

    List<LotIngredient> findAllByOrderByDatePeremptionAsc();

    List<LotIngredient> findAllByOrderByDateReceptionAsc();

    double sumQuantiteRestanteByIdIngredient(Long idIngredient );
}
