package com.mkalymlam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mkalymlam.entity.SessionTruck;

@Repository
public interface SessionTruckRepository extends JpaRepository<SessionTruck, Long> {

    Optional<SessionTruck> findTopByOrderByIdSessionDesc();
}
