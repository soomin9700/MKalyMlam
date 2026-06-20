package com.mkalymlam.recette.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mkalymlam.recette.model.RecetteBase;
import com.mkalymlam.recette.repository.RecetteBaseRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RecetteBaseService {

    private final RecetteBaseRepository recetteBaseRepository;

    public RecetteBaseService(RecetteBaseRepository recetteBaseRepository) {
        this.recetteBaseRepository = recetteBaseRepository;
    }

    @Transactional
    public RecetteBase save(RecetteBase recetteBase) {
        return recetteBaseRepository.save(recetteBase);
    }

    public List<RecetteBase> findAll() {
        return recetteBaseRepository.findAll();
    }

    public Optional<RecetteBase> find(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id null");
        }
        if (!recetteBaseRepository.existsById(id)) {
            throw new IllegalArgumentException("RecetteBase" + id + " does not exist");
        }
        return recetteBaseRepository.findById(id);
    }

    @Transactional
    public RecetteBase update(RecetteBase recetteBase) {
        if (recetteBase == null || recetteBase.getId() == null) {
            throw new IllegalArgumentException(
                "Pas de recetteBase");
        }
        if (!recetteBaseRepository.existsById(recetteBase.getId())) {
            throw new IllegalArgumentException(
                "pas de l'" + recetteBase.getId() + " dans la base ");
        }
        return recetteBaseRepository.save(recetteBase);
    }


    @Transactional
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id null");
        }
        if (!recetteBaseRepository.existsById(id)) {
            throw new IllegalArgumentException("RecetteBase" + id + " does not exist");
        }
        recetteBaseRepository.deleteById(id);
    }
}
