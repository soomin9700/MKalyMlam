package com.mkalymlam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mkalymlam.entity.StatutValidationAdmin;

@Repository
public interface StatutValidationAdminRepository extends JpaRepository<StatutValidationAdmin, Long> {
    StatutValidationAdmin findByLibelle(String libelle);
}
