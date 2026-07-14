package com.mkalymlam.repository;

import com.mkalymlam.entity.SessionTruckPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SessionTruckPositionRepository extends JpaRepository<SessionTruckPosition, Long> {
    List<SessionTruckPosition> findByOrderByDatePublicationDesc();

    List<SessionTruckPosition> findBySessionTruckId(Long sessionId);

    @Query("SELECT stp FROM SessionTruckPosition stp WHERE stp.datePublication = :today ORDER BY stp.datePublication DESC")
    List<SessionTruckPosition> findTodayPositions(@Param("today") LocalDate today);

    default List<SessionTruckPosition> findByOrderByDatePublicationDescToday() {
        return findTodayPositions(LocalDate.now());
    }

    // Récupérer les dernières positions du jour pour chaque truck
    @Query("SELECT stp FROM SessionTruckPosition stp " +
           "WHERE stp.datePublication = CURRENT_DATE " +
           "AND stp.id IN (" +
           "   SELECT MAX(stp2.id) FROM SessionTruckPosition stp2 " +
           "   WHERE stp2.datePublication = CURRENT_DATE " +
           "   GROUP BY stp2.sessionTruck.truck.id" +
           ") " +
           "ORDER BY stp.datePublication DESC")
    List<SessionTruckPosition> findLastPositionsOfDayForEachTruck();
    
    // Version avec paramètre de date
    @Query("SELECT stp FROM SessionTruckPosition stp " +
           "WHERE stp.datePublication = :date " +
           "AND stp.id IN (" +
           "   SELECT MAX(stp2.id) FROM SessionTruckPosition stp2 " +
           "   WHERE stp2.datePublication = :date " +
           "   GROUP BY stp2.sessionTruck.truck.id" +
           ") " +
           "ORDER BY stp.datePublication DESC")
    List<SessionTruckPosition> findLastPositionsForEachTruckByDate(@Param("date") LocalDate date);

        
    List<SessionTruckPosition> findByDatePublicationOrderByDatePublicationDesc(LocalDate datePublication);
    
    @Query("SELECT stp FROM SessionTruckPosition stp " +
           "WHERE stp.datePublication = CURRENT_DATE " +
           "AND stp.id IN (" +
           "   SELECT MAX(stp2.id) FROM SessionTruckPosition stp2 " +
           "   WHERE stp2.datePublication = CURRENT_DATE " +
           "   GROUP BY stp2.sessionTruck.truck.id" +
           ") " +
           "ORDER BY stp.datePublication DESC")
    List<SessionTruckPosition> findLatestPositionsForToday();

}