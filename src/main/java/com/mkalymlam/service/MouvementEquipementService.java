package com.mkalymlam.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mkalymlam.entity.MouvementEquipement;
import com.mkalymlam.repository.MouvementEquipementRepository;
import com.mkalymlam.repository.EquipementRepository;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class MouvementEquipementService {

    private final MouvementEquipementRepository repository;
    private final EquipementRepository equipementRepository;

    public MouvementEquipementService(  MouvementEquipementRepository repository, EquipementRepository equipementRepository) {
        this.repository = repository;
        this.equipementRepository = equipementRepository;
    }

    public List<MouvementEquipement> findAll() {
        return repository.findAll();
    }

    // public MouvementEquipement getById(Long id) {
    //     return repository.findById(id).orElse(null);
    // }

    // public MouvementEquipement save( MouvementEquipement mouvementEquipement) {
    //     return repository.save(mouvementEquipement);
    // }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public MouvementEquipement save(MouvementEquipement mouvement) {
        // Vérifier que l'équipement existe
        if (mouvement.getEquipement() == null || mouvement.getEquipement().getIdEquipement() == null) {
            throw new IllegalArgumentException("L'équipement est obligatoire");
        }
        
        equipementRepository.findById(mouvement.getEquipement().getIdEquipement())
                .orElseThrow(() -> new RuntimeException("Équipement non trouvé"));
        
        return repository.save(mouvement);
    }

    public List<MouvementEquipement> getMouvementsByEquipement(Long idEquipement) {
        return repository.findByEquipementIdEquipementOrderByDateMouvementDesc(idEquipement);
    }

    public Double getStockReel(Long idEquipement) {
        Double entree = repository.sumEntreeByEquipement(idEquipement);
        Double sortie = repository.sumSortieByEquipement(idEquipement);
        
        entree = entree != null ? entree : 0;
        sortie = sortie != null ? sortie : 0;
        
        return entree - sortie;
    }
    
    public List<MouvementEquipement> getAll() {
        return repository.findAll();
    }
    
    public MouvementEquipement getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mouvement non trouvé"));
    }
}