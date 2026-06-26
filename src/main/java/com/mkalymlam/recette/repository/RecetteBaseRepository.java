package com.mkalymlam.recette.repository;

import com.mkalymlam.recette.model.RecetteBase;
import com.mkalymlam.recette.model.RecetteBaseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecetteBaseRepository extends JpaRepository<RecetteBase, RecetteBaseId> {
}