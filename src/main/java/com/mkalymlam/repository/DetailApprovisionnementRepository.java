package com.mkalymlam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mkalymlam.entity.DetailApprovisionnement;

public interface DetailApprovisionnementRepository extends JpaRepository<DetailApprovisionnement, Long> {

    List<DetailApprovisionnement> findByApprovisionnement_IdApprovisionnementOrderByIdDetailApprovisionnementAsc(
            Long idApprovisionnement);
}
