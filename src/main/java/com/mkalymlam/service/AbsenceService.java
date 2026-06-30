package com.mkalymlam.service;

import com.mkalymlam.entity.Absence;
import com.mkalymlam.repository.AbsenceRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AbsenceService {

    private final AbsenceRepository repository;

    public AbsenceService(AbsenceRepository repository) {
        this.repository = repository;
    }

    public Absence save(Absence absence) {
        return repository.save(absence);
    }

    public List<Absence> findAll() {
        return repository.findAll();
    }

    public List<Absence> findByEmploye(Long employeId) {
        return repository.findByEmployeId(employeId);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}