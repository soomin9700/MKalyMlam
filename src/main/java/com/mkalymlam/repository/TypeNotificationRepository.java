package com.mkalymlam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mkalymlam.entity.TypeNotification;

@Repository
public interface TypeNotificationRepository extends JpaRepository<TypeNotification, Long> {

    Optional<TypeNotification> findByLibelle(String libelle);
}
