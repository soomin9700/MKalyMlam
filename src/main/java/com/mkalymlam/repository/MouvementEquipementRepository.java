package com.mkalymlam.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mkalymlam.entity.MouvementEquipement;

@Repository
public interface MouvementEquipementRepository extends JpaRepository<MouvementEquipement, Long> {
    
    List<MouvementEquipement> findByEquipementIdEquipement(Long idEquipement);
    
    List<MouvementEquipement> findByEquipementIdEquipementOrderByDateMouvementDesc(Long idEquipement);
    
    @Query("SELECT SUM(m.quantite) FROM MouvementEquipement m WHERE m.equipement.idEquipement = :idEquipement AND m.typeMouvement.idTypeMouvement = 1")
    Double sumEntreeByEquipement(@Param("idEquipement") Long idEquipement);
    
    @Query("SELECT SUM(m.quantite) FROM MouvementEquipement m WHERE m.equipement.idEquipement = :idEquipement AND m.typeMouvement.idTypeMouvement = 2")
    Double sumSortieByEquipement(@Param("idEquipement") Long idEquipement);
    
    @Query("SELECT SUM(m.quantite) FROM MouvementEquipement m WHERE m.equipement.id = :idEquipement AND m.dateMouvement <= :date AND m.typeMouvement.idTypeMouvement = 1")
    Double sumEntreeByEquipementBeforeDate(@Param("idEquipement") Long idEquipement, @Param("date") LocalDate date);
}