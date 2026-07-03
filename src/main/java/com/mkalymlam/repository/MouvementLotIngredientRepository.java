package com.mkalymlam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mkalymlam.entity.MouvementLotIngredient;

@Repository
public interface MouvementLotIngredientRepository extends JpaRepository<MouvementLotIngredient, Integer> {
    
    List<MouvementLotIngredient> findByIdLot(Integer idLot);
    
    List<MouvementLotIngredient> findByIdLotOrderByDateMouvementDesc(Integer idLot);
    
    @Query("SELECT SUM(m.quantite) FROM MouvementLotIngredient m WHERE m.idLot = :idLot AND m.idTypeMouvement.idTypeMouvement = 1")
    Double sumEntreeByLot(Integer idLot);
    
    @Query("SELECT SUM(m.quantite) FROM MouvementLotIngredient m WHERE m.idLot = :idLot AND m.idTypeMouvement.idTypeMouvement = 2")
    Double sumSortieByLot(Integer idLot);
}
