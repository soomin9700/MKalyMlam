package com.mkalymlam.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mkalymlam.entity.ActionCommande;
import com.mkalymlam.entity.Ingredient;
import com.mkalymlam.entity.PersonnalisationCommande;
import com.mkalymlam.entity.RecetteBase;
import com.mkalymlam.repository.ActionCommandeRepository;
import com.mkalymlam.repository.IngredientRepository;
import com.mkalymlam.repository.PersonnalisationCommandeRepository;
import com.mkalymlam.repository.RecetteBaseRepository;

@Service
public class PersonnalisationService {

    private final PersonnalisationCommandeRepository repository;
    private final RecetteBaseRepository recetteBaseRepository;
    private final IngredientRepository ingredientRepository;
    private final ActionCommandeRepository actionCommandeRepository;

    public PersonnalisationService(
            PersonnalisationCommandeRepository repository,
            RecetteBaseRepository recetteBaseRepository,
            IngredientRepository ingredientRepository,
            ActionCommandeRepository actionCommandeRepository) {
        this.repository = repository;
        this.recetteBaseRepository = recetteBaseRepository;
        this.ingredientRepository = ingredientRepository;
        this.actionCommandeRepository = actionCommandeRepository;
    }

    public PersonnalisationCommande ajouter(PersonnalisationCommande perso) {
        return repository.save(perso);
    }

    public void supprimer(Long id) {
        repository.deleteById(id);
    }

    public List<PersonnalisationCommande> findByLigne(Long idLigne) {
        return repository.findByIdLigne(idLigne);
    }

    public List<ActionCommande> findAllActions() {
        return actionCommandeRepository.findAll();
    }

    public List<Ingredient> findAllIngredients() {
        return ingredientRepository.findAll();
    }

    public List<RecetteIngredient> findIngredientsProduit(Long idProduit) {
        List<RecetteBase> recettes =
                recetteBaseRepository.findByIdProduit(idProduit);
        return recettes.stream().map(r -> {
            Ingredient ing = ingredientRepository.findById(r.getIdIngredient())
                    .orElse(null);
            if (ing == null) {
                return null;
            }
            return new RecetteIngredient(
                    ing.getIdIngredient(),
                    ing.getNomIngredient(),
                    ing.getUniteMesure(),
                    r.getQuantiteRecette());
        }).filter(java.util.Objects::nonNull).collect(Collectors.toList());
    }

    public static class RecetteIngredient {
        private Long idIngredient;
        private String nomIngredient;
        private String uniteMesure;
        private Double quantiteRecette;

        public RecetteIngredient(Long idIngredient, String nomIngredient,
                                  String uniteMesure, Double quantiteRecette) {
            this.idIngredient = idIngredient;
            this.nomIngredient = nomIngredient;
            this.uniteMesure = uniteMesure;
            this.quantiteRecette = quantiteRecette;
        }

        public Long getIdIngredient() {
            return idIngredient;
        }

        public String getNomIngredient() {
            return nomIngredient;
        }

        public String getUniteMesure() {
            return uniteMesure;
        }

        public Double getQuantiteRecette() {
            return quantiteRecette;
        }
    }
}
