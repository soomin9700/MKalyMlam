package com.mkalymlam.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mkalymlam.entity.NotificationPlateforme;
import com.mkalymlam.entity.SessionTruck;
import com.mkalymlam.repository.NotificationPlateformeRepository;

@Service
public class NotificationPlateformeService {

    private final NotificationPlateformeRepository repository;

    public NotificationPlateformeService(NotificationPlateformeRepository repository) {
        this.repository = repository;
    }

    public NotificationPlateforme save(NotificationPlateforme notification) {
        if (notification.getDateHeureEnvoi() == null) {
            notification.setDateHeureEnvoi(LocalDateTime.now());
        }
        return repository.save(notification);
    }

    public List<NotificationPlateforme> findAll() {
        return repository.findAllByOrderByDateHeureEnvoiDesc();
    }

    public List<NotificationPlateforme> findBySession(SessionTruck sessionTruck) {
        if (sessionTruck == null || sessionTruck.getId() == null) {
            return List.of();
        }
        return repository.findBySessionLiee_IdOrderByDateHeureEnvoiDesc(sessionTruck.getId());
    }
}
