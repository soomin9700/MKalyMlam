package com.mkalymlam.produit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mkalymlam.produit.model.Produit;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {

}