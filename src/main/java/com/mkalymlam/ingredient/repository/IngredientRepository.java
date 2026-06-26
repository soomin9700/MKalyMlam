package com.mkalymlam.ingredient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mkalymlam.ingredient.model.Ingredient;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

}