package com.mkalymlam.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mkalymlam.entity.InventaireJournalier;
import com.mkalymlam.entity.LotIngredient;
import com.mkalymlam.entity.TypeItem;

@Repository
public interface InventaireJournalierRepository extends JpaRepository<InventaireJournalier, Long>{
    
    List<InventaireJournalier> findByIdSession(Long idSession);
    List<InventaireJournalier> findByDateInventaire(LocalDate dateInventaire);
    
    List<InventaireJournalier> findAllByOrderByDateInventaireAsc();
    List<InventaireJournalier> findAllByOrderByDateInventaireDesc();

    List<InventaireJournalier> findByTypeItemAndIdItem(TypeItem typeItem, Long idItem);

    @Query("SELECT i FROM inventaireJournalier i WHERE i.ecartInventaire <> 0")
    List<InventaireJournalier> findWithEcart();
}
