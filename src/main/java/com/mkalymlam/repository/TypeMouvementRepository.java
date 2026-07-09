package com.mkalymlam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mkalymlam.entity.TypeMouvement;

@Repository
public interface TypeMouvementRepository extends JpaRepository<TypeMouvement, Long> {

    Optional<TypeMouvement> findByLibelle(String libelle);
}
