package com.mkalymlam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mkalymlam.entity.MouvementEquipement;

public interface MouvementEquipementRepository extends JpaRepository<MouvementEquipement, Long> {

    List<MouvementEquipement> findByEquipement_IdEquipementOrderByDateMouvementAsc(Long idEquipement);

}