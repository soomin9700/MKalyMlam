package com.mkalymlam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mkalymlam.entity.IngredientStatut;

public interface IngredientStatutRepository extends JpaRepository<IngredientStatut, Long> {

    Optional<IngredientStatut> findByIdIngredient(Long idIngredient);
}
