package com.mkalymlam.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mkalymlam.entity.SessionTruck;
import com.mkalymlam.entity.StatutSession;
import com.mkalymlam.entity.Truck;

@Repository
public interface SessionTruckRepository extends JpaRepository<SessionTruck, Long> {
    
    List<SessionTruck> findByDateSession(LocalDate dateSession);
    
    List<SessionTruck> findByStatutSession_Libelle(String libelle);
    
    Optional<SessionTruck> findByTruck_IdTruckAndStatutSession_Libelle(Long idTruck, String libelle);
}
