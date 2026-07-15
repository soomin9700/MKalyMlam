package com.mkalymlam.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mkalymlam.entity.LotIngredient;
import com.mkalymlam.entity.MouvementLotIngredient;
import com.mkalymlam.entity.TypeMouvement;
import com.mkalymlam.repository.LotIngredientRepository;
import com.mkalymlam.repository.MouvementLotIngredientRepository;

@Service
public class MouvementLotIngredientService {

    private final MouvementLotIngredientRepository mouvementRepository;
    private final LotIngredientRepository lotRepository;
    private final TypeMouvementService typeMouvementService;
    private final LotIngredientService lotService;  // AJOUTÉ

    public MouvementLotIngredientService(MouvementLotIngredientRepository mouvementRepository,
                                         LotIngredientRepository lotRepository, 
                                         TypeMouvementService typeMouvementService,
                                         LotIngredientService lotService) {  // AJOUTÉ
        this.mouvementRepository = mouvementRepository;
        this.lotRepository = lotRepository;
        this.typeMouvementService = typeMouvementService;
        this.lotService = lotService;  // AJOUTÉ
    }

    public Double sumByLotAndType(Long lotId, Long typeId) {
        Double val = mouvementRepository.sumQuantiteByLotAndType(lotId, typeId);
        return val == null ? 0.0 : val;
    }

    public Double sumByIngredientAndType(Long ingredientId, Long typeId) {
        Double val = mouvementRepository.sumQuantiteByIngredientAndType(ingredientId, typeId);
        return val == null ? 0.0 : val;
    }

    public List<MouvementLotIngredient> findByLotId(Long lotId) {
        if (lotId == null) {
            return mouvementRepository.findAll();
        }
        return mouvementRepository.findAll().stream()
                .filter(m -> m.getLot() != null && m.getLot().getIdLot() != null
                        && m.getLot().getIdLot().equals(lotId))
                .toList();
    }

    @Transactional
    public MouvementLotIngredient save(MouvementLotIngredient mouvement) {
        if (mouvement.getLot() == null || mouvement.getLot().getIdLot() == null) {
            throw new IllegalArgumentException("Lot invalide");
        }
        LotIngredient lot = lotRepository.findById(mouvement.getLot().getIdLot()).orElse(null);
        if (lot == null) {
            throw new IllegalArgumentException("Lot introuvable");
        }

        if (mouvement.getTypeMouvement() == null || mouvement.getTypeMouvement().getIdTypeMouvement() == null) {
            throw new IllegalArgumentException("Type de mouvement requis");
        }
        TypeMouvement type = typeMouvementService.getById(mouvement.getTypeMouvement().getIdTypeMouvement());
        if (type == null) {
            throw new IllegalArgumentException("Type de mouvement introuvable");
        }

        if (type.getLibelle() != null && type.getLibelle().equalsIgnoreCase("SORTIE")) {
            LocalDate today = LocalDate.now();
            if (lot.getDatePeremption() != null && lot.getDatePeremption().isBefore(today)) {
                throw new IllegalArgumentException("Impossible de sortir d'un lot périmé");
            }
            double disponible = calculerQuantiteDisponiblePourLot(lot);
            if (mouvement.getQuantite() == null || mouvement.getQuantite() <= 0
                    || mouvement.getQuantite() > disponible) {
                throw new IllegalArgumentException("Quantité de sortie invalide ou supérieure à la quantité disponible");
            }
        }

        if (mouvement.getDateMouvement() == null) {
            mouvement.setDateMouvement(LocalDate.now());  // CORRIGÉ : LocalDate au lieu de LocalDateTime
        }
        mouvement.setLot(lot);
        mouvement.setTypeMouvement(type);
        return mouvementRepository.save(mouvement);
    }

    private double calculerQuantiteDisponiblePourLot(LotIngredient lot) {
        double initial = lot.getQuantiteInitiale() == null ? 0.0 : lot.getQuantiteInitiale();
        Double entrees = mouvementRepository.sumQuantiteByLotAndType(lot.getIdLot(), 1L);
        Double sorties = mouvementRepository.sumQuantiteByLotAndType(lot.getIdLot(), 2L);
        double e = entrees == null ? 0.0 : entrees;
        double s = sorties == null ? 0.0 : sorties;
        return initial + e - s;
    }

    @Transactional
    public MouvementLotIngredient enregistrerMouvement(
            Long idLot,  // CORRIGÉ : idLot au lieu de idmouvementLot
            TypeMouvement typeMouvement,
            Double quantite,
            LocalDate dateMouvement) {

        MouvementLotIngredient mouvement = new MouvementLotIngredient();
        mouvement.setLot(lotService.getById(idLot));  // CORRIGÉ
        mouvement.setTypeMouvement(typeMouvement);
        mouvement.setQuantite(quantite);
        mouvement.setDateMouvement(dateMouvement);

        // Mise à jour du stock du lot
        LotIngredient lot = lotService.getById(idLot);
        if (typeMouvement.getIdTypeMouvement() == 2) { // SORTIE
            Double nouvelleQuantite = lot.getQuantiteRestante() - quantite;  // CORRIGÉ
            if (nouvelleQuantite < 0) {
                throw new IllegalStateException("Stock insuffisant");
            }
            lot.setQuantiteRestante(nouvelleQuantite);
        } else { // ENTREE
            lot.setQuantiteRestante(lot.getQuantiteRestante() + quantite);
        }
        lotService.update(idLot, lot);

        return mouvementRepository.save(mouvement);
    }

    public List<MouvementLotIngredient> getMouvementsByLot(Long idLot) {  // CORRIGÉ : idLot
        return mouvementRepository.findByLotIdOrderByDateMouvementDesc(idLot);
    }

    public Double getStockReel(Long idLot) {  // CORRIGÉ : idLot
        Double entree = mouvementRepository.sumEntreeByLot(idLot);
        Double sortie = mouvementRepository.sumSortieByLot(idLot);
        return (entree != null ? entree : 0) - (sortie != null ? sortie : 0);
    }
}