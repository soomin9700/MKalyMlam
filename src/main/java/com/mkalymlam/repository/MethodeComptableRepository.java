package com.mkalymlam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mkalymlam.entity.MethodeComptable;

public interface MethodeComptableRepository
        extends JpaRepository<MethodeComptable, Long> {
}