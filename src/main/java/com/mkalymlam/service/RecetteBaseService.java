package com.mkalymlam.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mkalymlam.entity.RecetteBase;
import com.mkalymlam.entity.RecetteBaseId;
import com.mkalymlam.repository.RecetteBaseRepository;

@Service
public class RecetteBaseService {

    private final RecetteBaseRepository repo;

    public RecetteBaseService(RecetteBaseRepository repo) {
        this.repo = repo;
    }

    public List<RecetteBase> findAll() {
        return repo.findAll();
    }

    public RecetteBase save(RecetteBase r) {
        return repo.save(r);
    }

    public RecetteBase findById(Long idProduit, Long idIngredient) {

        RecetteBaseId id = new RecetteBaseId(idProduit, idIngredient);

        return repo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Recette introuvable."));
    }

    public void delete(Long idProduit, Long idIngredient) {
        repo.deleteById(new RecetteBaseId(idProduit, idIngredient));
    }

    public RecetteBase update(Long idProduit,Long idIngredient,RecetteBase recette) {

        RecetteBase ancienne = findById(idProduit, idIngredient);

        ancienne.setQuantiteRecette(recette.getQuantiteRecette());

        return repo.save(ancienne);
    }

}