package com.mkalymlam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mkalymlam.entity.Produit;

public interface ProduitRepository
        extends JpaRepository<Produit, Long> {

}