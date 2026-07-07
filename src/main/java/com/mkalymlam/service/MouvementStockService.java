package com.mkalymlam.service;

import com.mkalymlam.dto.MouvementStockDTO;
import com.mkalymlam.entity.Ingredient;
import com.mkalymlam.entity.LotIngredient;
import com.mkalymlam.entity.MouvementStock;
import com.mkalymlam.repository.IngredientRepository;
import com.mkalymlam.repository.LotIngredientRepository;
import com.mkalymlam.repository.MouvementStockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class MouvementStockService {

    private final MouvementStockRepository mouvementRepository;
    private final LotIngredientRepository lotIngredientRepository;
    private final IngredientRepository ingredientRepository;

    public MouvementStockService(MouvementStockRepository mouvementRepository,
                                 LotIngredientRepository lotIngredientRepository,
                                 IngredientRepository ingredientRepository) {
        this.mouvementRepository = mouvementRepository;
        this.lotIngredientRepository = lotIngredientRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public List<MouvementStockDTO> getAllMouvements() {
        return mouvementRepository.findAllWithDetails();
    }

    public List<MouvementStockDTO> getMouvementsByIngredient(Long ingredientId) {
        return mouvementRepository.findDetailsByIngredientId(ingredientId);
    }

    public List<MouvementStockDTO> getMouvementsDuJour() {
        return mouvementRepository.findMouvementsDuJour();
    }

    public List<MouvementStockDTO> getMouvementsWithFilters(String dateDebut, String dateFin,
                                                            String typeMouvement, Long idIngredient) {
        LocalDateTime debut = null;
        LocalDateTime fin = null;

        if (dateDebut != null && !dateDebut.isEmpty()) {
            debut = LocalDate.parse(dateDebut).atStartOfDay();
        }
        if (dateFin != null && !dateFin.isEmpty()) {
            fin = LocalDate.parse(dateFin).atTime(LocalTime.MAX);
        }

        return mouvementRepository.findMouvementsWithFilters(debut, fin, typeMouvement, idIngredient);
    }

    public List<MouvementStock> getDerniersMouvements() {
        return mouvementRepository.findTop10ByOrderByDateMouvementDesc();
    }

    @Transactional
    public MouvementStock enregistrerEntree(Long idLot, Double quantite, String motif, Long idUtilisateur) {
        LotIngredient lot = lotIngredientRepository.findById(idLot)
                .orElseThrow(() -> new RuntimeException("Lot non trouvé"));

        Ingredient ingredient = lot.getIngredient();

        Double quantiteAvant = lot.getQuantiteRestante();
        Double quantiteApres = quantiteAvant + quantite;

        lot.setQuantiteRestante(quantiteApres);
        lotIngredientRepository.save(lot);

        MouvementStock mouvement = new MouvementStock(
            ingredient,
            lot,
            "ENTREE",
            quantite,
            quantiteAvant,
            quantiteApres,
            motif,
            idUtilisateur
        );

        return mouvementRepository.save(mouvement);
    }

    @Transactional
    public MouvementStock enregistrerSortie(Long idLot, Double quantite, String motif, Long idUtilisateur) {
        LotIngredient lot = lotIngredientRepository.findById(idLot)
                .orElseThrow(() -> new RuntimeException("Lot non trouvé"));

        Ingredient ingredient = lot.getIngredient();

        Double quantiteAvant = lot.getQuantiteRestante();

        if (quantiteAvant < quantite) {
            throw new RuntimeException("Quantité insuffisante en stock");
        }

        Double quantiteApres = quantiteAvant - quantite;

        lot.setQuantiteRestante(quantiteApres);
        lotIngredientRepository.save(lot);

        MouvementStock mouvement = new MouvementStock(
            ingredient,
            lot,
            "SORTIE",
            quantite,
            quantiteAvant,
            quantiteApres,
            motif,
            idUtilisateur
        );

        return mouvementRepository.save(mouvement);
    }

    @Transactional
    public MouvementStock enregistrerAjustement(Long idLot, Double nouvelleQuantite, String motif, Long idUtilisateur) {
        LotIngredient lot = lotIngredientRepository.findById(idLot)
                .orElseThrow(() -> new RuntimeException("Lot non trouvé"));

        Ingredient ingredient = lot.getIngredient();

        Double quantiteAvant = lot.getQuantiteRestante();
        Double quantiteApres = nouvelleQuantite;
        Double quantiteChangee = Math.abs(quantiteApres - quantiteAvant);

        lot.setQuantiteRestante(quantiteApres);
        lotIngredientRepository.save(lot);

        MouvementStock mouvement = new MouvementStock(
            ingredient,
            lot,
            "AJUSTEMENT",
            quantiteChangee,
            quantiteAvant,
            quantiteApres,
            motif + " (Ajustement)",
            idUtilisateur
        );

        return mouvementRepository.save(mouvement);
    }

    public long countEntrees() {
        return mouvementRepository.countByTypeMouvement("ENTREE");
    }

    public long countSorties() {
        return mouvementRepository.countByTypeMouvement("SORTIE");
    }

    public long countAjustements() {
        return mouvementRepository.countByTypeMouvement("AJUSTEMENT");
    }

    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }
}