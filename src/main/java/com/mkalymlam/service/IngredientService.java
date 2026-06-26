package com.mkalymlam.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mkalymlam.entity.Ingredient;
import com.mkalymlam.repository.IngredientRepository;

@Service
public class IngredientService {

    private final IngredientRepository repository;

    public IngredientService(IngredientRepository repository) {
        this.repository = repository;
    }

    // Liste de tous les ingrédients
    public List<Ingredient> getAll() {
        return repository.findAll();
    }

    // Recherche par id
    public Ingredient getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    // Sauvegarde (INSERT ou UPDATE)
    public Ingredient save(Ingredient ingredient) {
        return repository.save(ingredient);
    }

    // Suppression
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}