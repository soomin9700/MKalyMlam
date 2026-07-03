package com.mkalymlam.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mkalymlam.entity.InventaireJournalier;
import com.mkalymlam.entity.TypeItem;

@Repository
public interface InventaireJournalierRepository extends JpaRepository<InventaireJournalier, Long>{
    
    List<InventaireJournalier> findBySessionTruckId(Long idSession);
    List<InventaireJournalier> findByDateInventaire(LocalDate dateInventaire);
    
    List<InventaireJournalier> findAllByOrderByDateInventaireAsc();
    List<InventaireJournalier> findAllByOrderByDateInventaireDesc();

    @Query("SELECT i FROM InventaireJournalier i WHERE i.typeItem.idTypeItem = :idTypeItem AND i.sessionTruck.id = :idSession")
    List<InventaireJournalier> findByTypeItemAndIdItem(
            @Param("idTypeItem") Integer idTypeItem, 
            @Param("idSession") Long idSession);
            
    @Query("SELECT i FROM InventaireJournalier i WHERE i.ecartInventaire <> 0")
    List<InventaireJournalier> findWithEcart();
}
