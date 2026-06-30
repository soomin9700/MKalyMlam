package com.mkalymlam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mkalymlam.entity.AbsenceConge;

public interface AbsenceCongeRepository
        extends JpaRepository<AbsenceConge, Long> {

    List<AbsenceConge> findByUtilisateur_IdUtilisateur(Long idUtilisateur);
}