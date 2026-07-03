package com.mkalymlam.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mkalymlam.entity.*;
import com.mkalymlam.repository.*;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class InventaireJournalierService {
    
    private final InventaireJournalierRepository inventaireRepository;
    private final SessionTruckRepository sessionTruckRepository;
    private final TypeItemRepository typeItemRepository;

    public InventaireJournalierService (InventaireJournalierRepository inventaireRepository, SessionTruckRepository sessionTruckRepository, TypeItemRepository typeItemRepository) {
        this.inventaireRepository = inventaireRepository;
        this.sessionTruckRepository = sessionTruckRepository;
        this.typeItemRepository = typeItemRepository;
    }
    
    @Transactional
    public InventaireJournalier save(InventaireJournalier inventaire) {

        if (inventaire.getQuantitePhysiqueConstatee() == null) {
            throw new IllegalArgumentException("La quantité physique est obligatoire");
        }
        
        double ecart = inventaire.getQuantitePhysiqueConstatee() 
                       - inventaire.getQuantiteTheoriqueSysteme();
        inventaire.setEcartInventaire(ecart);
        
        return inventaireRepository.save(inventaire);
    }

    public List<InventaireJournalier> getAll() {
        return inventaireRepository.findAllByOrderByDateInventaireAsc();
    }
    public List<InventaireJournalier> getAllDesc() {
        return inventaireRepository.findAllByOrderByDateInventaireDesc();
    }
    public InventaireJournalier getById(Long id) {
        return inventaireRepository.findById(id).orElse(null);
    }

    public List<TypeItem> getAllTypeItems() {
        return typeItemRepository.findAll();
    }
    
    public List<InventaireJournalier> findBySession(Long idSession) {
        return inventaireRepository.findBySessionTruckId(idSession);
    }
    public List<SessionTruck> getAllSessionTrucks(){
        return sessionTruckRepository.findAll();
    }

    public List<InventaireJournalier> findByDateInventaire(LocalDate dateInventaire) {
        return inventaireRepository.findByDateInventaire(dateInventaire);
    }
    
    public List<InventaireJournalier> findByAvecEcart() {
        return inventaireRepository.findWithEcart();
    }
    
    public boolean verifierEcartExistant(InventaireJournalier inventaire) {
        if (inventaire.getEcartInventaire() != 0) {
            return true;
        }
        return false;
    }
}
