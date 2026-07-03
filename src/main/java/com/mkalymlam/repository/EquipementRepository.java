package com.mkalymlam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mkalymlam.entity.Equipement;

@Repository
public interface EquipementRepository extends JpaRepository<Equipement, Long> {

    Equipement findByNomEquipement(String nomEquipement);
}