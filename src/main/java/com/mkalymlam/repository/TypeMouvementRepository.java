package com.mkalymlam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.mkalymlam.entity.TypeMouvement;

@Repository
public interface TypeMouvementRepository
        extends JpaRepository<TypeMouvement, Long> {

    TypeMouvement findByLibelle(String libelle);
    
}