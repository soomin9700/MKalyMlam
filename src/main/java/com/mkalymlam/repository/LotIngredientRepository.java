package com.mkalymlam.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mkalymlam.entity.LotIngredient;

@Repository
public interface LotIngredientRepository extends JpaRepository<LotIngredient, Long> {

    List<LotIngredient> findByIngredient_IdIngredient(Long ingredientId);

    List<LotIngredient> findByIngredient_NomIngredientContainingIgnoreCase(String nomIngredient);

    List<LotIngredient> findAllByOrderByDatePeremptionAsc();

    List<LotIngredient> findAllByOrderByDateReceptionAsc();

    List<LotIngredient> findByDatePeremptionBetween(LocalDate startDate, LocalDate endDate);

    List<LotIngredient> findByDatePeremptionBetweenAndIngredient_IdIngredient(LocalDate startDate, LocalDate endDate,
            Long ingredientId);

    List<LotIngredient> findByDatePeremptionBefore(LocalDate date);

    @Query("SELECT COALESCE(SUM(l.quantiteInitiale), 0) FROM LotIngredient l WHERE l.ingredient.idIngredient = :idIngredient")
    Double sumQuantiteInitialeByIngredient(@Param("idIngredient") Long idIngredient);

    @Query("SELECT COALESCE(SUM(l.quantiteInitiale), 0) FROM LotIngredient l WHERE l.ingredient.idIngredient = :idIngredient AND l.typeMouvement.idTypeMouvement = :typeMouvementId")
    Double sumQuantiteInitialeByIngredientAndTypeMouvement(@Param("idIngredient") Long idIngredient,
            @Param("typeMouvementId") Long typeMouvementId);

    @Query("SELECT COALESCE(SUM(l.quantiteInitiale), 0) FROM LotIngredient l WHERE l.ingredient.idIngredient = :idIngredient")
    Double sumQuantiteInitialeByIdIngredient(@Param("idIngredient") Long idIngredient);

    // calcule la quantiteRestante à partir des mouvements
    @Query(value = """
        SELECT 
            l."idLot",
            l."idIngredient",
            l."dateReception",
            l."datePeremption",
            l."quantiteInitiale",
            l."prixAchatUnitaire",
            COALESCE(l."quantiteInitiale" + SUM(
                CASE 
                    WHEN m."idTypeMouvement" = 1 THEN m."quantite"   -- ENTREE
                    WHEN m."idTypeMouvement" = 2 THEN -m."quantite"  -- SORTIE
                    ELSE 0
                END
            ), l."quantiteInitiale") as quantiteRestante
        FROM "lotIngredient" l
        LEFT JOIN "mouvementLotIngredient" m ON m."idLot" = l."idLot"
        WHERE l."idIngredient" = :idIngredient
        GROUP BY l."idLot", l."idIngredient", l."dateReception", l."datePeremption", 
                 l."quantiteInitiale", l."prixAchatUnitaire"
        """, nativeQuery = true)
    List<Object[]> findLotsWithRestanteByIngredient(@Param("idIngredient") Long idIngredient);

    // somme des quantitesRestantes calculees
    @Query(value = """
        SELECT COALESCE(SUM(
            l."quantiteInitiale" + COALESCE(
                (SELECT SUM(
                    CASE 
                        WHEN m."idTypeMouvement" = 1 THEN m."quantite"
                        WHEN m."idTypeMouvement" = 2 THEN -m."quantite"
                        ELSE 0
                    END
                ) FROM "mouvementLotIngredient" m WHERE m."idLot" = l."idLot"),
            0)
        ), 0)
        FROM "lotIngredient" l
        WHERE l."idIngredient" = :idIngredient
        """, nativeQuery = true)
    Double sumQuantiteRestanteByIdIngredient(@Param("idIngredient") Long idIngredient);
}
