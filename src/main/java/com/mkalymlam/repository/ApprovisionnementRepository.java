package com.mkalymlam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mkalymlam.entity.Approvisionnement;

public interface ApprovisionnementRepository extends JpaRepository<Approvisionnement, Long> {

    List<Approvisionnement> findAllByOrderByDateApprovisionnementDescIdApprovisionnementDesc();
}
