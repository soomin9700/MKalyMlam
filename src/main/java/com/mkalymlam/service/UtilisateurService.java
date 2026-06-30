package com.mkalymlam.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mkalymlam.entity.Utilisateur;
import com.mkalymlam.repository.UtilisateurRepository;

@Service
public class UtilisateurService {

    private final UtilisateurRepository repository;

    public UtilisateurService(UtilisateurRepository repository) {
        this.repository = repository;
    }

    public List<Utilisateur> findAll() {
        return repository.findAll();
    }

    public Utilisateur getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Utilisateur save(Utilisateur utilisateur) {
        return repository.save(utilisateur);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    /**
     * Désactiver un employé sans le supprimer.
     */
    public void desactiverUtilisateur(Long id) {

        Utilisateur utilisateur = getById(id);

        if (utilisateur != null) {
            utilisateur.setStatutActif(false);
            repository.save(utilisateur);
        }
    }
}