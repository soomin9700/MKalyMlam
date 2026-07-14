package com.mkalymlam.repository;

import com.mkalymlam.entity.SessionTruckPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SessionTruckPositionRepository extends JpaRepository<SessionTruckPosition, Long> {
    List<SessionTruckPosition> findByOrderByDatePublicationDesc();
    List<SessionTruckPosition> findBySessionTruckId(Long sessionId);
}