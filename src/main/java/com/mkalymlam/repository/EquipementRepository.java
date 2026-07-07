package com.mkalymlam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.mkalymlam.entity.Equipement;
import com.mkalymlam.entity.MouvementEquipement;

@Repository
public interface EquipementRepository extends JpaRepository<Equipement, Long> {

    @Query("SELECT m FROM MouvementEquipement m WHERE m.equipement.idEquipement = :idEquipement ORDER BY m.dateMouvement ASC")
    List<MouvementEquipement> findMouvementsByEquipementIdOrderByDateMouvementAsc(
            @Param("idEquipement") Long idEquipement);


    Equipement findByNomEquipement(String nomEquipement);
}