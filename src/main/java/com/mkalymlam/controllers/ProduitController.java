package com.mkalymlam.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mkalymlam.models.Produit;
import com.mkalymlam.repository.ProduitRepository;

@RestController
@RequestMapping("/produit")
public class ProduitController {

    private final ProduitRepository produitRepository;

    public ProduitController(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    @GetMapping("/liste")
    public List<Produit> liste() {
        return produitRepository.findAll();
    }
}
