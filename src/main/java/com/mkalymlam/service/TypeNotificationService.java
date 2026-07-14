package com.mkalymlam.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mkalymlam.entity.TypeNotification;
import com.mkalymlam.repository.TypeNotificationRepository;

@Service
public class TypeNotificationService {

    private final TypeNotificationRepository repository;

    public TypeNotificationService(TypeNotificationRepository repository) {
        this.repository = repository;
    }

    public List<TypeNotification> findAll() {
        return repository.findAll();
    }

    public TypeNotification save(TypeNotification typeNotification) {
        return repository.save(typeNotification);
    }

    public TypeNotification findById(Long id) {
        return repository.findById(id).orElse(null);
    }
}
