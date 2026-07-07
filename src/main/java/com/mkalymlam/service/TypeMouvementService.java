package com.mkalymlam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mkalymlam.entity.TypeMouvement;
import com.mkalymlam.repository.TypeMouvementRepository;

@Service
public class TypeMouvementService {
    @Autowired
    private final TypeMouvementRepository repository;

    public TypeMouvementService(
            TypeMouvementRepository repository) {

        this.repository = repository;
    }

    public List<TypeMouvement> findAll() {
        return repository.findAll();
    }

    public TypeMouvement getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public TypeMouvement save(
            TypeMouvement typeMouvement) {

        return repository.save(typeMouvement);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public TypeMouvement getTypeMouvementByLibelle(String libelle) {
        TypeMouvement typeMouvement = repository.findByLibelle(libelle);
        if (typeMouvement == null) {
            throw new RuntimeException("Type de mouvement non trouvé : " + libelle);
        }
        return typeMouvement;
    }
}
