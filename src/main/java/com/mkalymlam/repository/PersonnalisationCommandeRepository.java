package com.mkalymlam.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.mkalymlam.entity.PersonnalisationCommande;

@Repository
public interface PersonnalisationCommandeRepository extends JpaRepository<PersonnalisationCommande, Long> {
    List<PersonnalisationCommande> findByIdLine(Long idLine);

    @Query(value = "SELECT * FROM \"personnalisationCommande\" WHERE \"idLigne\" IN (SELECT \"idLigne\" FROM \"ligneCommande\" WHERE \"idCommande\" = :idCommande)", nativeQuery = true)
    List<PersonnalisationCommande> findAllByCommande(@Param("idCommande") Long idCommande);
}
