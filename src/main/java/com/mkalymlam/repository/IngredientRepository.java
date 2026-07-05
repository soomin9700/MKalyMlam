package com.mkalymlam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mkalymlam.entity.Ingredient;

public interface IngredientRepository
        extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findByNomIngredientContainingIgnoreCase(String nomIngredient);
}
