package com.mkalymlam.recette.service;

import com.mkalymlam.recette.model.RecetteBase;
import com.mkalymlam.recette.model.RecetteBaseId;
import com.mkalymlam.recette.repository.RecetteBaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void delete(Long idProduit, Long idIngredient) {
        repo.deleteById(new RecetteBaseId(idProduit, idIngredient));
    }
}