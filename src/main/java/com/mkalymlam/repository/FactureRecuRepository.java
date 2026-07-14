package com.mkalymlam.repository;

import com.mkalymlam.entity.FactureRecu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FactureRecuRepository extends JpaRepository<FactureRecu, Long> {

    List<FactureRecu> findAllByOrderByDateFacturationDesc();
}
