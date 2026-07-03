package com.mkalymlam.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mkalymlam.entity.MouvementEquipement;
import com.mkalymlam.repository.MouvementEquipementRepository;

@Service
public class MouvementEquipementService {

    private final MouvementEquipementRepository repository;

    public MouvementEquipementService(  MouvementEquipementRepository repository) {
        this.repository = repository;
    }

    public List<MouvementEquipement> findAll() {
        return repository.findAll();
    }

    public MouvementEquipement getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public MouvementEquipement save( MouvementEquipement mouvementEquipement) {
        return repository.save(mouvementEquipement);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}