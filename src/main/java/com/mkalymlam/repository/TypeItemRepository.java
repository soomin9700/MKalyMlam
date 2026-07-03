package com.mkalymlam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mkalymlam.entity.TypeItem;

@Repository
public interface TypeItemRepository extends JpaRepository<TypeItem, Long> {

    TypeItem findByLibelle(String libelle);
    
}