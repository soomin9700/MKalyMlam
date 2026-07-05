package com.mkalymlam.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mkalymlam.entity.LotIngredient;
import com.mkalymlam.entity.Produit;
import com.mkalymlam.repository.ProduitRepository;

@Service
public class ProduitService {

    private final ProduitRepository repository;

    public ProduitService(ProduitRepository repository) {
        this.repository = repository;
    }

    public List<Produit> findAll() {
        return repository.findAll();
    }

    public Produit getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Produit save(Produit produit) {
        return repository.save(produit);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public boolean verifierEstNouveau(Produit produit) {
        if (produit.getEstNouveau() == true) {
            return true;
        }
        return false;
    }

    public List<Produit> findByNomProduit(String nomProduit) {
        List<Produit> produits = repository.findByProduit_NomProduitContainingIgnoreCase(nomProduit);
        produits.forEach(produit -> {
            produit.setEstNouveau(verifierEstNouveau(produit));
        });
        return produits;
    }

     public List<Produit> getAllNouveauxProduits() {
        List<Produit> produits = repository.findAll();
        produits.forEach(produit -> {
            produit.setEstNouveau(verifierEstNouveau(produit));
        });
        return produits;
    }
}