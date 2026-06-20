package com.mkalymlam.itineraire.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mkalymlam.itineraire.module.Itineraire;
import com.mkalymlam.itineraire.repository.ItineraireRepository;
import java.util.List;
@Service
public class ItineraireService {
    
    private final ItineraireRepository itineraireRepository;

    public ItineraireService(ItineraireRepository itineraireRepository) {
        this.itineraireRepository = itineraireRepository;
    }

    @Transactional
    public Itineraire save(Itineraire itineraire) {
        return itineraireRepository.save(itineraire);
    }

    public List<Itineraire> findAll() {
        return itineraireRepository.findAll();
    }

    public Itineraire find(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id null");
        }
        if (!itineraireRepository.existsById(id)) {
            throw new IllegalArgumentException("Itineraire" + id + " does not exist");
        }
        return itineraireRepository.findById(id).orElse(null);
    }

    @Transactional
    public Itineraire update(Itineraire itineraire) {
        if (itineraire == null || itineraire.getId() == null) {
            throw new IllegalArgumentException(
                "Pas de itineraire");
        }
        if (!itineraireRepository.existsById(itineraire.getId())) {
            throw new IllegalArgumentException(
                "pas de l'" + itineraire.getId() + " dans la base ");
        }
        return itineraireRepository.save(itineraire);
    }

    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id null");
        }
        if (!itineraireRepository.existsById(id)) {
            throw new IllegalArgumentException("Itineraire" + id + " does not exist");
        }
        itineraireRepository.deleteById(id);
    }

    
}