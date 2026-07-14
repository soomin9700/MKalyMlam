package com.mkalymlam.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mkalymlam.entity.RecetteBase;
import com.mkalymlam.entity.RecetteBaseId;
import com.mkalymlam.repository.RecetteBaseRepository;

@Service
public class RecetteBaseService {

    private final RecetteBaseRepository repo;

    public RecetteBaseService(RecetteBaseRepository repo) {
        this.repo = repo;
    }

    public List<RecetteBase> findAll() {
        return repo.findAll();
    }

    public RecetteBase save(RecetteBase r) {
        return repo.save(r);
    }

    public RecetteBase findById(Long idProduit, Long idIngredient) {
        RecetteBaseId id = new RecetteBaseId(idProduit, idIngredient);
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Recette introuvable."));
    }

    public void delete(Long idProduit, Long idIngredient) {
        repo.deleteById(new RecetteBaseId(idProduit, idIngredient));
    }

    public RecetteBase update(Long idProduit, Long idIngredient, RecetteBase recette) {
        RecetteBase ancienne = findById(idProduit, idIngredient);
        ancienne.setQuantiteRecette(recette.getQuantiteRecette());
        return repo.save(ancienne);
    }

    // filtres
    public List<RecetteBase> search(String nomProduit, String nomIngredient) {
        // Si les deux filtres sont vides, retourner tout
        if ((nomProduit == null || nomProduit.isEmpty()) && 
            (nomIngredient == null || nomIngredient.isEmpty())) {
            return repo.findAll();
        }
        
        // Si seulement le nom du produit est fourni
        if (nomProduit != null && !nomProduit.isEmpty() && 
            (nomIngredient == null || nomIngredient.isEmpty())) {
            return repo.findByProduitNomContainingIgnoreCase(nomProduit);
        }
        
        // Si seulement le nom de l'ingrédient est fourni
        if (nomIngredient != null && !nomIngredient.isEmpty() && 
            (nomProduit == null || nomProduit.isEmpty())) {
            return repo.findByIngredientNomContainingIgnoreCase(nomIngredient);
        }
        
        // Si les deux sont fournis
        return repo.findByProduitAndIngredientNom(nomProduit, nomIngredient);
    }

    public List<RecetteBase> findByProduitId(Long idProduit) {
        return repo.findByIdProduit(idProduit);
    }

    public List<RecetteBase> findByIngredientId(Long idIngredient) {
        return repo.findByIdIngredient(idIngredient);
    }

    public long countByProduitId(Long idProduit) {
        return repo.countByIdProduit(idProduit);
    }

    public long countByIngredientId(Long idIngredient) {
        return repo.countByIdIngredient(idIngredient);
    }
}