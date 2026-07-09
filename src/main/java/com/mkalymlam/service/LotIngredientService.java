package com.mkalymlam.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mkalymlam.entity.Ingredient;
import com.mkalymlam.entity.LotIngredient;
import com.mkalymlam.entity.MouvementLotIngredient;
import com.mkalymlam.entity.TypeMouvement;
import com.mkalymlam.repository.IngredientRepository;
import com.mkalymlam.repository.LotIngredientRepository;
import com.mkalymlam.repository.MouvementLotIngredientRepository;
import com.mkalymlam.repository.TypeMouvementRepository;

@Service
public class LotIngredientService {

    private final LotIngredientRepository lotIngredientRepository;
    private final IngredientRepository ingredientRepository;
    private final MouvementLotIngredientRepository mouvementRepository;
    private final TypeMouvementRepository typeMouvementRepository;

    public LotIngredientService(LotIngredientRepository lotIngredientRepository,
                                IngredientRepository ingredientRepository,
                                MouvementLotIngredientRepository mouvementRepository,
                                TypeMouvementRepository typeMouvementRepository) {
        this.lotIngredientRepository = lotIngredientRepository;
        this.ingredientRepository = ingredientRepository;
        this.mouvementRepository = mouvementRepository;
        this.typeMouvementRepository = typeMouvementRepository;
    }

    public List<LotIngredient> getAll() {
        List<LotIngredient> lots = lotIngredientRepository.findAllByOrderByDateReceptionAsc();
        lots.forEach(this::setQuantiteRestanteAndAlerte);
        return lots;
    }

    public List<LotIngredient> findByIngredientId(Long ingredientId) {
        List<LotIngredient> lots = lotIngredientRepository.findByIngredient_IdIngredient(ingredientId);
        lots.forEach(this::setQuantiteRestanteAndAlerte);
        return lots;
    }

    public List<LotIngredient> findByIngredientName(String nomIngredient) {
        List<LotIngredient> lots = lotIngredientRepository.findByIngredient_NomIngredientContainingIgnoreCase(nomIngredient);
        lots.forEach(this::setQuantiteRestanteAndAlerte);
        return lots;
    }

    public LotIngredient getById(Long id) {
        LotIngredient lot = lotIngredientRepository.findById(id).orElse(null);
        if (lot != null) {
            setQuantiteRestanteAndAlerte(lot);
        }
        return lot;
    }

    public double calculQuantiteRestante(Long idLot) {
        List<MouvementLotIngredient> mouvements = mouvementRepository.findByLot_IdLot(idLot);
        double entree = 0.0;
        double sortie = 0.0;
        for (MouvementLotIngredient m : mouvements) {
            if ("ENTREE".equals(m.getTypeMouvement().getLibelle())) {
                entree += m.getQuantite();
            } else if ("SORTIE".equals(m.getTypeMouvement().getLibelle())) {
                sortie += m.getQuantite();
            }
        }
        return entree - sortie;
    }

    public boolean estPerime(Long idLot) {
        LotIngredient lot = lotIngredientRepository.findById(idLot).orElse(null);
        if (lot == null || lot.getDatePeremption() == null) return false;
        return lot.getDatePeremption().isBefore(LocalDate.now());
    }

    private void setQuantiteRestanteAndAlerte(LotIngredient lot) {
        double qte = calculQuantiteRestante(lot.getIdLot());
        lot.setQuantiteRestante(qte);
        lot.setAlerte(verifierAlerte(lot));
    }

    @Transactional
    public LotIngredient save(LotIngredient lotIngredient) {
        if (lotIngredient.getIngredient() != null && lotIngredient.getIngredient().getIdIngredient() != null) {
            Ingredient ingredient = ingredientRepository.findById(lotIngredient.getIngredient().getIdIngredient())
                    .orElse(null);
            lotIngredient.setIngredient(ingredient);
        }
        if (lotIngredient.getDateReception() == null) {
            lotIngredient.setDateReception(LocalDate.now());
        }
        LotIngredient saved = lotIngredientRepository.save(lotIngredient);

        TypeMouvement typeEntree = typeMouvementRepository.findByLibelle("ENTREE")
                .orElseThrow(() -> new RuntimeException("Type de mouvement ENTREE introuvable"));

        MouvementLotIngredient mouvement = new MouvementLotIngredient(
                saved, typeEntree, saved.getQuantiteInitiale());
        mouvement.setDateMouvement(LocalDateTime.now());
        mouvementRepository.save(mouvement);

        return saved;
    }

    @Transactional
    public LotIngredient update(Long id, LotIngredient lotIngredient) {
        LotIngredient existing = getById(id);
        if (existing == null) {
            return null;
        }

        if (lotIngredient.getIngredient() != null && lotIngredient.getIngredient().getIdIngredient() != null) {
            Ingredient ingredient = ingredientRepository.findById(lotIngredient.getIngredient().getIdIngredient())
                    .orElse(null);
            existing.setIngredient(ingredient);
        }
        if (lotIngredient.getDateReception() != null) {
            existing.setDateReception(lotIngredient.getDateReception());
        }
        if (lotIngredient.getDatePeremption() != null) {
            existing.setDatePeremption(lotIngredient.getDatePeremption());
        }
        if (lotIngredient.getQuantiteInitiale() != null) {
            existing.setQuantiteInitiale(lotIngredient.getQuantiteInitiale());
        }
        if (lotIngredient.getPrixAchatUnitaire() != null) {
            existing.setPrixAchatUnitaire(lotIngredient.getPrixAchatUnitaire());
        }

        return lotIngredientRepository.save(existing);
    }

    @Transactional
    public void deleteById(Long id) {
        lotIngredientRepository.deleteById(id);
    }

    public List<LotIngredient> getAlertLots() {
        return getAll().stream()
                .filter(lot -> lot.getQuantiteRestante() != null
                        && lot.getQuantiteRestante() <= lot.getIngredient().getSeuilAlerteQuantite())
                .toList();
    }

    public boolean verifierAlerte(LotIngredient lotIngredient) {
        if (lotIngredient.getIngredient() != null && lotIngredient.getQuantiteRestante() != null) {
            Double seuilAlerte = lotIngredient.getIngredient().getSeuilAlerteQuantite();
            if (seuilAlerte != null && lotIngredient.getQuantiteRestante() <= seuilAlerte) {
                return true;
            }
        }
        return false;
    }

    public double quantiteLotIngredientActuelleByIngredient(Long idIngredient) {
        List<LotIngredient> lots = lotIngredientRepository.findByIngredient_IdIngredient(idIngredient);
        return lots.stream()
                .filter(lot -> !estPerime(lot.getIdLot()))
                .mapToDouble(lot -> calculQuantiteRestante(lot.getIdLot()))
                .sum();
    }

    public List<LotIngredient> getAllWithAlertStatus() {
        return getAll();
    }

    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll().stream()
                .filter(i -> Boolean.TRUE.equals(i.getActif()))
                .toList();
    }

    @Transactional
    public MouvementLotIngredient ajouterSortie(Long idLot, Double quantite) {
        LotIngredient lot = lotIngredientRepository.findById(idLot)
                .orElseThrow(() -> new IllegalArgumentException("Lot introuvable"));

        TypeMouvement typeSortie = typeMouvementRepository.findByLibelle("SORTIE")
                .orElseThrow(() -> new RuntimeException("Type de mouvement SORTIE introuvable"));

        double restant = calculQuantiteRestante(idLot);
        if (quantite > restant) {
            throw new IllegalArgumentException("Quantité insuffisante. Restant: " + restant);
        }

        MouvementLotIngredient mouvement = new MouvementLotIngredient(lot, typeSortie, quantite);
        mouvement.setDateMouvement(LocalDateTime.now());
        return mouvementRepository.save(mouvement);
    }
}
