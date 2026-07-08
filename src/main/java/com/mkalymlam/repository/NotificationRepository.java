package com.mkalymlam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mkalymlam.entity.Notification;
import com.mkalymlam.entity.TypeNotification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

   
    List<Notification> findByPublieTrueOrderByDateCreationDesc();

    
    List<Notification> findByProduit_IdProduitOrderByDateCreationDesc(Long idProduit);

   
    List<Notification> findBySession_IdOrderByDateCreationDesc(Long sessionId);

   
    List<Notification> findByTypeOrderByDateCreationDesc(TypeNotification type);
}