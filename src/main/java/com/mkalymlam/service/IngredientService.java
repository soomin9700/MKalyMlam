package com.mkalymlam.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<Ingredient> searchByNom(String recherche) {
        if (recherche == null || recherche.isBlank()) {
            return findAll();
        }
        return repository.findByNomIngredientContainingIgnoreCase(recherche.trim());
    }

    public Ingredient getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public Ingredient save(Ingredient ingredient) {
        if (ingredient.getActif() == null) {
            ingredient.setActif(true);
        }
        return repository.save(ingredient);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public boolean toggleStatut(Long idIngredient) {
        Ingredient ingredient = repository.findById(idIngredient).orElse(null);
        if (ingredient == null) return false;
        ingredient.setActif(!Boolean.TRUE.equals(ingredient.getActif()));
        repository.save(ingredient);
        return ingredient.getActif();
    }

    public List<Ingredient> findAllWithStatut(String filtreStatut) {
        List<Ingredient> all = repository.findAll();
        if (filtreStatut == null || filtreStatut.isBlank()) return all;
        boolean filterActif = "actif".equalsIgnoreCase(filtreStatut);
        return all.stream()
                .filter(i -> Boolean.TRUE.equals(i.getActif()) == filterActif)
                .toList();
    }
}
