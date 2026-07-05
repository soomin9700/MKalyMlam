package com.mkalymlam.service;

import com.mkalymlam.entity.ProduitAvecDisponibilite;
import com.mkalymlam.repository.ProduitAvecDisponibiliteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProduitAvecDisponibiliteService {

    private final ProduitAvecDisponibiliteRepository repository;

    public ProduitAvecDisponibiliteService(ProduitAvecDisponibiliteRepository repository) {
        this.repository = repository;
    }

    public List<ProduitAvecDisponibilite> findAll() {
        return repository.findAll();
    }

    public ProduitAvecDisponibilite findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<ProduitAvecDisponibilite> findByNom(String nom) {
        return repository.findByNomProduitContainingIgnoreCase(nom);
    }

    public List<ProduitAvecDisponibilite> findByDisponible(Boolean disponible) {
        return repository.findByEstDisponible(disponible);
    }

    public List<ProduitAvecDisponibilite> findByNouveau(Boolean nouveau) {
        return repository.findByEstNouveau(nouveau);
    }

    public List<ProduitAvecDisponibilite> findByCriteria(String nom, Boolean disponible, Boolean nouveau) {
        return repository.findByCriteria(nom, disponible, nouveau);
    }

    public long countDisponibles() {
        return repository.countByEstDisponible(true);
    }
}