package com.mkalymlam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mkalymlam.entity.MouvementEquipement;

@Repository
public interface MouvementEquipementRepository extends JpaRepository<MouvementEquipement, Long> {
    
    List<MouvementEquipement> findByIdEquipement(Long idEquipement);
    
    List<MouvementEquipement> findByIdEquipementOrderByDateMouvementDesc(Long idEquipement);
    
    @Query("SELECT SUM(m.quantite) FROM mouvementEquipement m WHERE m.idEquipement = :idEquipement AND m.idTypeMouvement.idTypeMouvement = 1")
    Double sumEntreeByEquipement(Long idEquipement);
    
    @Query("SELECT SUM(m.quantite) FROM mouvementEquipement m WHERE m.idEquipement = :idEquipement AND m.idTypeMouvement.idTypeMouvement = 2")
    Double sumSortieByEquipement(Long idEquipement);
}