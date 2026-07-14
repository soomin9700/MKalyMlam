package com.mkalymlam.service;

import com.mkalymlam.entity.Itineraire;
import com.mkalymlam.entity.SessionTruck;
import com.mkalymlam.entity.SessionTruckPosition;
import com.mkalymlam.repository.SessionTruckPositionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class SessionTruckPositionService {

    private final SessionTruckPositionRepository positionRepository;
    private final SessionTruckService sessionTruckService;
    private final ItineraireService itineraireService;

    public SessionTruckPositionService(SessionTruckPositionRepository positionRepository,
                                       SessionTruckService sessionTruckService,
                                       ItineraireService itineraireService) {
        this.positionRepository = positionRepository;
        this.sessionTruckService = sessionTruckService;
        this.itineraireService = itineraireService;
    }

    @Transactional
    public SessionTruckPosition publierPosition(Long idSession, Long idItineraire, LocalTime heureArrivee) {
        SessionTruck sessionTruck = sessionTruckService.find(idSession);
        Itineraire itineraire = itineraireService.findById(idItineraire);

        SessionTruckPosition position = new SessionTruckPosition();
        position.setSessionTruck(sessionTruck);
        position.setItineraire(itineraire);
        position.setHeureArrivee(heureArrivee != null ? heureArrivee : LocalTime.now());
        position.setDatePublication(LocalDate.now());

        return positionRepository.save(position);
    }

    public List<SessionTruckPosition> findAll() {
        return positionRepository.findByOrderByDatePublicationDesc();
    }

    public List<SessionTruckPosition> findBySession(Long sessionId) {
        return positionRepository.findBySessionTruckId(sessionId);
    }
}