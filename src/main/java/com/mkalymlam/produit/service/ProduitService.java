package com.mkalymlam.produit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mkalymlam.produit.model.Produit;
import com.mkalymlam.produit.repository.ProduitRepository;

@Service
public class ProduitService {

    private final ProduitRepository produitRepository;

    public ProduitService(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    public List<Produit> findAll() {
        return produitRepository.findAll();
    }

    public Produit findById(Long id) {
        return produitRepository.findById(id).orElse(null);
    }

    public Produit save(Produit produit) {
        return produitRepository.save(produit);
    }

    public void delete(Long id) {
        produitRepository.deleteById(id);
    }
}