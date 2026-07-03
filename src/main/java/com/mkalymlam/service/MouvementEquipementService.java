package com.mkalymlam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mkalymlam.entity.MouvementEquipement;
import com.mkalymlam.repository.MouvementEquipementRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MouvementEquipementService {
    
    @Autowired
    private MouvementEquipementRepository mouvementRepository;
    
    public List<MouvementEquipement> getMouvementsByIdEquipement(Long idIdEquipement) {
        return mouvementRepository.findByIdEquipement(idIdEquipement);
    }
    
    public Double getStockReel(Long idEquipement) {
        Double entree = mouvementRepository.sumEntreeByEquipement(idEquipement);
        Double sortie = mouvementRepository.sumSortieByEquipement(idEquipement);
        return (entree != null ? entree : 0) - (sortie != null ? sortie : 0);
    }

    // update not done
}