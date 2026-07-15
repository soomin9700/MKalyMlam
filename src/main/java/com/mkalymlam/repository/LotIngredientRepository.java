package com.mkalymlam.repository;

import com.mkalymlam.entity.LotIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LotIngredientRepository extends JpaRepository<LotIngredient, Long> {

    // ============ REQUÊTES DE BASE ============
    
    List<LotIngredient> findByIngredient_NomIngredientContainingIgnoreCase(String nomIngredient);

    @Query("SELECT l FROM LotIngredient l WHERE l.ingredient.idIngredient = :idIngredient")
    List<LotIngredient> findByIdIngredient(@Param("idIngredient") Long idIngredient);

    // Méthode findByIngredient_IdIngredient (utilisée par ConsommationService et LotIngredientService)
    List<LotIngredient> findByIngredient_IdIngredient(Long idIngredient);

    // ============ METHODES DE TRI ============
    
    List<LotIngredient> findAllByOrderByDateReceptionAsc();

    // ============ REQUÊTES PAR DATE ============
    
    List<LotIngredient> findByDatePeremptionBetween(LocalDate dateDebut, LocalDate dateFin);
    
    List<LotIngredient> findByDatePeremptionBetweenAndIngredient_IdIngredient(LocalDate dateDebut, LocalDate dateFin, Long idIngredient);
    
    List<LotIngredient> findByDatePeremptionBefore(LocalDate date);
    
    List<LotIngredient> findByDatePeremptionBeforeAndIngredient_IdIngredient(LocalDate date, Long idIngredient);

    // ============ REQUÊTES AVEC FILTRES ============

    @Query("SELECT l FROM LotIngredient l WHERE " +
           "(:idIngredient IS NULL OR l.ingredient.idIngredient = :idIngredient) AND " +
           "(:dateDebut IS NULL OR l.datePeremption >= :dateDebut) AND " +
           "(:dateFin IS NULL OR l.datePeremption <= :dateFin)")
    List<LotIngredient> filterIngredients(
            @Param("dateDebut") LocalDate dateDebut,
            @Param("dateFin") LocalDate dateFin,
            @Param("idIngredient") Long idIngredient);

    // ============ REQUÊTES POUR STOCK ============

    @Query("SELECT l FROM LotIngredient l WHERE l.ingredient.idIngredient = :idIngredient ORDER BY l.datePeremption ASC")
    List<LotIngredient> findLotsDisponiblesByIngredientId(@Param("idIngredient") Long idIngredient);

    @Query("SELECT l FROM LotIngredient l WHERE l.ingredient.idIngredient = :idIngredient AND l.datePeremption >= :date ORDER BY l.datePeremption ASC")
    List<LotIngredient> findLotsValidesByIngredientIdAndDate(@Param("idIngredient") Long idIngredient, @Param("date") LocalDate date);
}