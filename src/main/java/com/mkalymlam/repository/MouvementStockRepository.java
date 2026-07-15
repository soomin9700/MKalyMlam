package com.mkalymlam.repository;

import com.mkalymlam.dto.MouvementStockDTO;
import com.mkalymlam.entity.MouvementStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MouvementStockRepository extends JpaRepository<MouvementStock, Long> {

    @Query(value = """
        SELECT 
            m."idMouvement",
            i."nomIngredient",
            CAST(m."idLot" AS VARCHAR),
            m."typeMouvement",
            m."quantite",
            m."quantiteAvant",
            m."quantiteApres",
            m."dateMouvement",
            m."motif",
            i."uniteMesure"
        FROM "mouvementStock" m
        JOIN "ingredient" i ON m."idIngredient" = i."idIngredient"
        JOIN "lotIngredient" l ON m."idLot" = l."idLot"
        ORDER BY m."dateMouvement" DESC
        """, nativeQuery = true)
    List<MouvementStockDTO> findAllWithDetails();

    @Query(value = """
        SELECT 
            m."idMouvement",
            i."nomIngredient",
            CAST(m."idLot" AS VARCHAR),
            m."typeMouvement",
            m."quantite",
            m."quantiteAvant",
            m."quantiteApres",
            m."dateMouvement",
            m."motif",
            i."uniteMesure"
        FROM "mouvementStock" m
        JOIN "ingredient" i ON m."idIngredient" = i."idIngredient"
        JOIN "lotIngredient" l ON m."idLot" = l."idLot"
        WHERE i."idIngredient" = :idIngredient
        ORDER BY m."dateMouvement" DESC
        """, nativeQuery = true)
    List<MouvementStockDTO> findDetailsByIngredientId(@Param("idIngredient") Long idIngredient);

    @Query(value = """
        SELECT 
            m."idMouvement",
            i."nomIngredient",
            CAST(m."idLot" AS VARCHAR),
            m."typeMouvement",
            m."quantite",
            m."quantiteAvant",
            m."quantiteApres",
            m."dateMouvement",
            m."motif",
            i."uniteMesure"
        FROM "mouvementStock" m
        JOIN "ingredient" i ON m."idIngredient" = i."idIngredient"
        JOIN "lotIngredient" l ON m."idLot" = l."idLot"
        WHERE (:dateDebut IS NULL OR m."dateMouvement" >= CAST(:dateDebut AS TIMESTAMP))
        AND (:dateFin IS NULL OR m."dateMouvement" <= CAST(:dateFin AS TIMESTAMP))
        AND (:typeMouvement IS NULL OR m."typeMouvement" = :typeMouvement)
        AND (:idIngredient IS NULL OR i."idIngredient" = :idIngredient)
        ORDER BY m."dateMouvement" DESC
        """, nativeQuery = true)
    List<MouvementStockDTO> findMouvementsWithFilters(
        @Param("dateDebut") LocalDateTime dateDebut,
        @Param("dateFin") LocalDateTime dateFin,
        @Param("typeMouvement") String typeMouvement,
        @Param("idIngredient") Long idIngredient
    );

    @Query(value = """
        SELECT 
            m."idMouvement",
            i."nomIngredient",
            CAST(m."idLot" AS VARCHAR),
            m."typeMouvement",
            m."quantite",
            m."quantiteAvant",
            m."quantiteApres",
            m."dateMouvement",
            m."motif",
            i."uniteMesure"
        FROM "mouvementStock" m
        JOIN "ingredient" i ON m."idIngredient" = i."idIngredient"
        JOIN "lotIngredient" l ON m."idLot" = l."idLot"
        WHERE DATE(m."dateMouvement") = CURRENT_DATE
        ORDER BY m."dateMouvement" DESC
        """, nativeQuery = true)
    List<MouvementStockDTO> findMouvementsDuJour();

    List<MouvementStock> findByIngredient_IdIngredientOrderByDateMouvementDesc(Long ingredientId);
    List<MouvementStock> findByLot_IdLotOrderByDateMouvementDesc(Long lotId);
    List<MouvementStock> findByTypeMouvementOrderByDateMouvementDesc(String typeMouvement);
    List<MouvementStock> findByDateMouvementBetweenOrderByDateMouvementDesc(LocalDateTime debut, LocalDateTime fin);
    
    @Query("SELECT m FROM MouvementStock m ORDER BY m.dateMouvement DESC")
    List<MouvementStock> findTop10ByOrderByDateMouvementDesc();
    
    @Query("SELECT COUNT(m) FROM MouvementStock m WHERE m.typeMouvement = :typeMouvement")
    long countByTypeMouvement(@Param("typeMouvement") String typeMouvement);
}