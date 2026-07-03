package com.mkalymlam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mkalymlam.entity.MouvementLotIngredient;

@Repository
public interface MouvementLotIngredientRepository extends JpaRepository<MouvementLotIngredient, Integer> {
    
    List<MouvementLotIngredient> findByLotIngredientIdLot(Long idLot);
    
    List<MouvementLotIngredient> findByLotIngredientIdLotOrderByDateMouvementDesc(Long idLot);
    
    @Query("SELECT SUM(m.quantite) FROM MouvementLotIngredient m WHERE m.lotIngredient.idLot = :idLot AND m.typeMouvement.idTypeMouvement = 1")
    Double sumEntreeByLot(@Param("idLot") Long idLot);
    
    @Query("SELECT SUM(m.quantite) FROM MouvementLotIngredient m WHERE m.lotIngredient.idLot = :idLot AND m.typeMouvement.idTypeMouvement = 2")
    Double sumSortieByLot(@Param("idLot") Long idLot);
}
