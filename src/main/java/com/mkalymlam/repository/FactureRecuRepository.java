package com.mkalymlam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mkalymlam.entity.FactureRecu;

@Repository
public interface FactureRecuRepository extends JpaRepository<FactureRecu, Long> {

    Optional<FactureRecu> findTopByOrderByIdFactureDesc();
}
