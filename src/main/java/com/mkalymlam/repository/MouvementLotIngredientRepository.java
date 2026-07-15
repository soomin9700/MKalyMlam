package com.mkalymlam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mkalymlam.entity.MouvementLotIngredient;

@Repository
public interface MouvementLotIngredientRepository extends JpaRepository<MouvementLotIngredient, Long> {

    @Query("SELECT COALESCE(SUM(m.quantite), 0) FROM MouvementLotIngredient m WHERE m.lot.idLot = :lotId AND m.typeMouvement.idTypeMouvement = :typeId")
    Double sumQuantiteByLotAndType(@Param("lotId") Long lotId, @Param("typeId") Long typeId);

    @Query("SELECT COALESCE(SUM(m.quantite), 0) FROM MouvementLotIngredient m WHERE m.lot.ingredient.idIngredient = :ingredientId AND m.typeMouvement.idTypeMouvement = :typeId")
    Double sumQuantiteByIngredientAndType(@Param("ingredientId") Long ingredientId, @Param("typeId") Long typeId);

    // AJOUT DES MÉTHODES MANQUANTES
    @Query("SELECT m FROM MouvementLotIngredient m WHERE m.lot.idLot = :lotId ORDER BY m.dateMouvement DESC")
    List<MouvementLotIngredient> findByLotIdOrderByDateMouvementDesc(@Param("lotId") Long lotId);

    @Query("SELECT COALESCE(SUM(m.quantite), 0) FROM MouvementLotIngredient m WHERE m.lot.idLot = :lotId AND m.typeMouvement.libelle = 'ENTREE'")
    Double sumEntreeByLot(@Param("lotId") Long lotId);

    @Query("SELECT COALESCE(SUM(m.quantite), 0) FROM MouvementLotIngredient m WHERE m.lot.idLot = :lotId AND m.typeMouvement.libelle = 'SORTIE'")
    Double sumSortieByLot(@Param("lotId") Long lotId);
}