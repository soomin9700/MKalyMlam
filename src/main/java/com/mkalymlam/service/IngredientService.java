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

    public List<Ingredient> findAll() {
        return repository.findAll();
    }

    public Ingredient getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public Ingredient save(Ingredient ingredient) {
        return repository.save(ingredient);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}