package com.mkalymlam.service;

import com.mkalymlam.entity.Employe;
import com.mkalymlam.repository.EmployeRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeService {

    private final EmployeRepository repository;

    public EmployeService(EmployeRepository repository) {
        this.repository = repository;
    }

    public List<Employe> findAll() {
        return repository.findAll();
    }

    public Employe findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Employe save(Employe employe) {
        return repository.save(employe);
    }

    public Employe update(Long id, Employe updated) {
        return repository.findById(id).map(employe -> {
            employe.setMatricule(updated.getMatricule());
            employe.setPoste(updated.getPoste());
            employe.setDepartement(updated.getDepartement());
            employe.setDateEmbauche(updated.getDateEmbauche());
            employe.setSalaireBase(updated.getSalaireBase());
            // utilisateur non modifiable ici
            return repository.save(employe);
        }).orElse(null);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}