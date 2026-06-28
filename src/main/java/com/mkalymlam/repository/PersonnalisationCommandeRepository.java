package com.mkalymlam.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mkalymlam.entity.PersonnalisationCommande;

@Repository
public interface PersonnalisationCommandeRepository extends JpaRepository<PersonnalisationCommande, Long> {
    List<PersonnalisationCommande> findByIdLine(Long idLine);
}