package com.mkalymlam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mkalymlam.entity.TypeMouvement;

public interface TypeMouvementRepository
        extends JpaRepository<TypeMouvement, Long> {

}