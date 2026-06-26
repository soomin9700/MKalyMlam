package com.mkalymlam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mkalymlam.entity.RecetteBase;
import com.mkalymlam.entity.RecetteBaseId;

@Repository
public interface RecetteBaseRepository extends JpaRepository<RecetteBase, RecetteBaseId> {
}