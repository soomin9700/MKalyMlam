package com.mkalymlam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mkalymlam.entity.NotificationPlateforme;

@Repository
public interface NotificationPlateformeRepository extends JpaRepository<NotificationPlateforme, Long> {
    List<NotificationPlateforme> findBySessionLiee_IdOrderByDateHeureEnvoiDesc(Long idSession);

    List<NotificationPlateforme> findAllByOrderByDateHeureEnvoiDesc();
}
