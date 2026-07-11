package com.mkalymlam.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mkalymlam.entity.MethodeComptable;
import com.mkalymlam.entity.TypeEquipement;
import com.mkalymlam.repository.MethodeComptableRepository;

@Service
public class MethodeComptableService {

    private final MethodeComptableRepository repository;

    public MethodeComptableService(MethodeComptableRepository repository) {
        this.repository = repository;
    }

    public List<MethodeComptable> findAll() {
        return repository.findAll();
    }

    public MethodeComptable getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public MethodeComptable save(MethodeComptable entity) {
        return repository.save(entity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}