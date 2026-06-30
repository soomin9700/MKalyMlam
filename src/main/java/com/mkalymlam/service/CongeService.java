package com.mkalymlam.service;

import com.mkalymlam.entity.Conge;
import com.mkalymlam.entity.StatutValidation;
import com.mkalymlam.repository.CongeRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CongeService {

    private final CongeRepository repository;

    public CongeService(CongeRepository repository) {
        this.repository = repository;
    }

    public Conge save(Conge conge) {
        conge.setStatut(StatutValidation.EN_ATTENTE);
        return repository.save(conge);
    }

    public List<Conge> findAll() {
        return repository.findAll();
    }

    public List<Conge> findByEmploye(Long employeId) {
        return repository.findByEmployeId(employeId);
    }

    public Conge valider(Long id) {
        Conge conge = repository.findById(id).orElseThrow();
        conge.setStatut(StatutValidation.VALIDE);
        return repository.save(conge);
    }

    public Conge refuser(Long id) {
        Conge conge = repository.findById(id).orElseThrow();
        conge.setStatut(StatutValidation.REFUSE);
        return repository.save(conge);
    }
}