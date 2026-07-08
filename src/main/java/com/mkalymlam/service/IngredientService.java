package com.mkalymlam.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mkalymlam.entity.Ingredient;
import com.mkalymlam.entity.IngredientStatut;
import com.mkalymlam.repository.IngredientRepository;
import com.mkalymlam.repository.IngredientStatutRepository;

@Service
public class IngredientService {

    private final IngredientRepository repository;
    private final IngredientStatutRepository statutRepository;

    public IngredientService(IngredientRepository repository, IngredientStatutRepository statutRepository) {
        this.repository = repository;
        this.statutRepository = statutRepository;
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
        Ingredient saved = repository.save(ingredient);
        if (!statutRepository.findByIdIngredient(saved.getIdIngredient()).isPresent()) {
            IngredientStatut statut = new IngredientStatut(saved, true);
            statutRepository.save(statut);
        }
        return saved;
    }

    public void deleteById(Long id) {
        statutRepository.findByIdIngredient(id).ifPresent(statutRepository::delete);
        repository.deleteById(id);
    }

    public boolean isActif(Long idIngredient) {
        return statutRepository.findByIdIngredient(idIngredient)
                .map(IngredientStatut::getStatutActif)
                .orElse(true);
    }

    @Transactional
    public boolean toggleStatut(Long idIngredient) {
        IngredientStatut statut = statutRepository.findByIdIngredient(idIngredient)
                .orElseGet(() -> {
                    Ingredient ingredient = repository.findById(idIngredient).orElse(null);
                    if (ingredient == null) return null;
                    return new IngredientStatut(ingredient, true);
                });
        if (statut == null) return false;
        statut.setStatutActif(!Boolean.TRUE.equals(statut.getStatutActif()));
        statutRepository.save(statut);
        return statut.getStatutActif();
    }

    public List<Ingredient> findAllWithStatut(String filtreStatut) {
        List<Ingredient> all = repository.findAll();
        if (filtreStatut == null || filtreStatut.isBlank()) return all;
        boolean filterActif = "actif".equalsIgnoreCase(filtreStatut);
        return all.stream()
                .filter(i -> isActif(i.getIdIngredient()) == filterActif)
                .toList();
    }
}
