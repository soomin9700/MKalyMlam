package com.mkalymlam.repository;

// import jakarta.persistence.Query;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mkalymlam.entity.FichePaie;

public interface FichePaieRepository extends JpaRepository<FichePaie, Long> {

    java.util.List<FichePaie> findByUtilisateur_IdUtilisateur(Long idUtilisateur);

    java.util.List<FichePaie> findByMoisAnnee(String moisAnnee);

    // Optional<FichePaie> findByUtilisateurIdAndMoisAnnee(Long idUtilisateur, String moisAnnee);

    @Query("""
        SELECT f
        FROM FichePaie f
        WHERE f.utilisateur.idUtilisateur = :idUtilisateur
        AND f.moisAnnee = :moisAnnee
    """)
    Optional<FichePaie> findByUtilisateurIdAndMoisAnnee(
            @Param("idUtilisateur") Integer idUtilisateur,
            @Param("moisAnnee") String moisAnnee);
}