package com.mkalymlam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mkalymlam.entity.MouvementLotIngredient;

@Repository
public interface MouvementLotIngredientRepository extends JpaRepository<MouvementLotIngredient, Long> {

    List<MouvementLotIngredient> findByLot_IdLot(Long idLot);
}
