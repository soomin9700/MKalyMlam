package com.mkalymlam.repository;

import com.mkalymlam.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    // Retourne un seul résultat même si plusieurs correspondent (prend le premier)
    Optional<RoleEntity> findFirstByLibelle(String libelle);

    // On garde l'ancienne méthode pour d'éventuels autres usages sécurisés
    Optional<RoleEntity> findByLibelle(String libelle);
}