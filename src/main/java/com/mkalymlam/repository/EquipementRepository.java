package com.mkalymlam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mkalymlam.entity.Equipement;
import com.mkalymlam.entity.MouvementEquipement;

public interface EquipementRepository extends JpaRepository<Equipement, Long> {
    List<MouvementEquipement> findByEquipement_IdEquipementOrderByDateMouvementAsc( Long idEquipement);
    List<MouvementEquipement>findByEquipement_IdEquipementAndTypeMouvement_IdTypeMouvementOrderByDateMouvementDesc( Long idEquipement, Long idTypeMouvement);

}