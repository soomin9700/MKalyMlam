package com.mkalymlam.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mkalymlam.entity.*;
import com.mkalymlam.repository.*;

import java.util.List;

@Service
@Transactional
public class MouvementEquipementService {

    private final MouvementEquipementRepository mouvementRepository;
    private final EquipementRepository equipementRepository;

    public MouvementEquipementService(
            MouvementEquipementRepository mouvementRepository,
            EquipementRepository equipementRepository) {
        this.mouvementRepository = mouvementRepository;
        this.equipementRepository = equipementRepository;
    }

    @Transactional
    public MouvementEquipement save(MouvementEquipement mouvement) {
        // Vérifier que l'équipement existe
        if (mouvement.getEquipement() == null || mouvement.getEquipement().getIdEquipement() == null) {
            throw new IllegalArgumentException("L'équipement est obligatoire");
        }
        
        equipementRepository.findById(mouvement.getEquipement().getIdEquipement())
                .orElseThrow(() -> new RuntimeException("Équipement non trouvé"));
        
        return mouvementRepository.save(mouvement);
    }

    public List<MouvementEquipement> getMouvementsByEquipement(Long idEquipement) {
        return mouvementRepository.findByEquipementIdEquipementOrderByDateMouvementDesc(idEquipement);
    }

    public Double getStockReel(Long idEquipement) {
        Double entree = mouvementRepository.sumEntreeByEquipement(idEquipement);
        Double sortie = mouvementRepository.sumSortieByEquipement(idEquipement);
        
        entree = entree != null ? entree : 0;
        sortie = sortie != null ? sortie : 0;
        
        return entree - sortie;
    }
    
    public List<MouvementEquipement> getAll() {
        return mouvementRepository.findAll();
    }
    
    public MouvementEquipement getById(Long id) {
        return mouvementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mouvement non trouvé"));
    }
}