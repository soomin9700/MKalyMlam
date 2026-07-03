package com.mkalymlam.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mkalymlam.entity.LotIngredient;
import com.mkalymlam.entity.MouvementLotIngredient;
import com.mkalymlam.entity.TypeMouvement;
import com.mkalymlam.repository.MouvementLotIngredientRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MouvementLotIngredientService {
    
    private final MouvementLotIngredientRepository mouvementRepository;
    private final LotIngredientService lotService;

    public MouvementLotIngredientService(
            MouvementLotIngredientRepository mouvementRepository,
            LotIngredientService lotService) {
        this.mouvementRepository = mouvementRepository;
        this.lotService = lotService;
    }
    
    public MouvementLotIngredient enregistrerMouvement(
            Long idMouvementLotIngredient, 
            TypeMouvement typeMouvement, 
            Double quantite, 
            LocalDate dateMouvement) {
        
        MouvementLotIngredient mouvement = new MouvementLotIngredient();
            mouvement.setIdMouvementLotIngredient(idMouvementLotIngredient);
            mouvement.setTypeMouvement(typeMouvement);
            mouvement.setQuantite(quantite);
            mouvement.setDateMouvement(dateMouvement);
        
        // màj le stock du lot
        LotIngredient lot = lotService.getById(idMouvementLotIngredient);
        if (typeMouvement.getId() == 2) { // SORTIE
            Double nouvelleQuantite = lot.getQuantiteRestante() + quantite;
            if (nouvelleQuantite < 0) {
                throw new IllegalStateException("Stock insuffisant");
            }
            lot.setQuantiteRestante(nouvelleQuantite);
        } else { // ENTREE
            lot.setQuantiteRestante(lot.getQuantiteRestante() + quantite);
        }
        lotService.update(idMouvementLotIngredient, lot);
        
        return mouvementRepository.save(mouvement);
    }
    
    public List<MouvementLotIngredient> getMouvementsByLot(Long idMouvementLotIngredient) {
        return mouvementRepository.findByIdLotOrderByDateMouvementDesc(idMouvementLotIngredient);
    }
    
    public Double getStockReel(Long idMouvementLotIngredient) {
        Double entree = mouvementRepository.sumEntreeByLot(idMouvementLotIngredient);
        Double sortie = mouvementRepository.sumSortieByLot(idMouvementLotIngredient);
        return (entree != null ? entree : 0) - (sortie != null ? sortie : 0);
    }
}