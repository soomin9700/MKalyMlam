package com.mkalymlam.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.mkalymlam.entity.PersonnalisationCommande;
import com.mkalymlam.repository.PersonnalisationCommandeRepository;

@Service
public class PersonnalisationCommandeService {

    private final PersonnalisationCommandeRepository repository;

    public PersonnalisationCommandeService(PersonnalisationCommandeRepository repository) {
        this.repository = repository;
    }

    public PersonnalisationCommande save(PersonnalisationCommande p) {
        return repository.save(p);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public PersonnalisationCommande findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<PersonnalisationCommande> findAllByLigne(Long idLigne) {
        return repository.findByIdLine(idLigne);
    }
}