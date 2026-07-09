package com.mkalymlam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mkalymlam.entity.Depense;

@Repository
public interface DepenseRepository extends JpaRepository<Depense, Long> {

    List<Depense> findBySession_IdOrderByIdDesc(Long idSession);

    List<Depense> findAllByOrderByDateDepenseDesc();

    List<Depense> findByStatutValidationAdmin_LibelleOrderByIdDesc(String libelle);
}
