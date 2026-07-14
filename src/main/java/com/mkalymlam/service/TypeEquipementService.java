package com.mkalymlam.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mkalymlam.entity.TypeEquipement;
import com.mkalymlam.repository.TypeEquipementRepository;

@Service
public class TypeEquipementService {

    private final TypeEquipementRepository repository;

    public TypeEquipementService(TypeEquipementRepository repository) {
        this.repository = repository;
    }

    public List<TypeEquipement> findAll() {
        return repository.findAll();
    }

    public TypeEquipement getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public TypeEquipement save(TypeEquipement entity) {
        return repository.save(entity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}