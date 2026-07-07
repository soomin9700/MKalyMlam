package com.mkalymlam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mkalymlam.entity.TypeEquipement;

public interface TypeEquipementRepository
        extends JpaRepository<TypeEquipement, Long> {
}