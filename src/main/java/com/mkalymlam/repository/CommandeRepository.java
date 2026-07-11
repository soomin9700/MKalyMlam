package com.mkalymlam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mkalymlam.entity.Commande;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {

    @Query("SELECT COALESCE(SUM(c.montantTotal), 0) FROM Commande c WHERE c.idSession = :idSession")
    Double sumMontantTotalByIdSession(@Param("idSession") Long idSession);
}
