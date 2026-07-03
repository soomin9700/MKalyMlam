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
    private final MouvementLotIngredientRepository mouvementLotService;
    private final MouvementEquipementService mouvementEquipementService;

    public InventaireJournalierService (InventaireJournalierRepository inventaireRepository, MouvementLotIngredientRepository mouvementLotService, MouvementEquipementService mouvementEquipementService) {
        this.inventaireRepository = inventaireRepository;
        this.mouvementLotService = mouvementLotService;
        this.mouvementEquipementService = mouvementEquipementService;
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

    // TSY MBOLA VITA LE UPDATE!!!!!
    
    // @Transactional
    // public InventaireJournalier update(Long id, InventaireJournalier inventaireJournalier) {
    //     InventaireJournalier existing = getById(id);
    //     if (existing == null) {
    //         return null;
    //     }

    //     if (inventaireJournalier.getSessionTruck() != null && inventaireJournalier.getSessionTruck().getId() != null){
    //         SessionTruck sessionTruck = inventaireRepository.findByIdSession(id)
    //             .orElse(null);
    //             existing.setSessionTruck(inventaireJournalier.getSessionTruck());
    //     }

        

    //     return inventaireRepository.save(existing);
    // }

    public List<InventaireJournalier> getAll() {
        return inventaireRepository.findAllByOrderByDateInventaireAsc();
    }
    public List<InventaireJournalier> getAllDesc() {
        return inventaireRepository.findAllByOrderByDateInventaireDesc();
    }
    public InventaireJournalier getById(Long id) {
        return inventaireRepository.findById(id).orElse(null);
    }
    
    public List<InventaireJournalier> findByBySession(Long idSession) {
        return inventaireRepository.findByIdSession(idSession);
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
