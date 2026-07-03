package com.mkalymlam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mkalymlam.entity.Ingredient;

public interface IngredientRepository
        extends JpaRepository<Ingredient, Long> {
}