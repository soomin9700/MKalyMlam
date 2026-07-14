package com.mkalymlam.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mkalymlam.entity.Ingredient;
import com.mkalymlam.entity.LotIngredient;
import com.mkalymlam.entity.TypeMouvement;
import com.mkalymlam.repository.IngredientRepository;
import com.mkalymlam.repository.LotIngredientRepository;
import com.mkalymlam.repository.MouvementLotIngredientRepository;

@Service
public class LotIngredientService {

    private final LotIngredientRepository lotIngredientRepository;
    private final IngredientRepository ingredientRepository;
    private final TypeMouvementService typeMouvementService;
    private final MouvementLotIngredientRepository mouvementRepository;

    public LotIngredientService(LotIngredientRepository lotIngredientRepository,
            IngredientRepository ingredientRepository,
            TypeMouvementService typeMouvementService,
            MouvementLotIngredientRepository mouvementRepository) {
        this.lotIngredientRepository = lotIngredientRepository;
        this.ingredientRepository = ingredientRepository;
        this.typeMouvementService = typeMouvementService;
        this.mouvementRepository = mouvementRepository;
    }

    public List<LotIngredient> getAll() {
        return lotIngredientRepository.findAllByOrderByDateReceptionAsc();
    }

    public List<LotIngredient> findByIngredientId(Long ingredientId) {
        return lotIngredientRepository.findByIngredient_IdIngredient(ingredientId);
    }

    public List<LotIngredient> findByIngredientName(String nomIngredient) {
        return lotIngredientRepository.findByIngredient_NomIngredientContainingIgnoreCase(nomIngredient);
    }

    public List<LotIngredient> getIngredientsBientotPerimes() {
        LocalDate today = LocalDate.now();
        return lotIngredientRepository.findByDatePeremptionBetween(today, today.plusDays(3));
    }

    public List<LotIngredient> getIngredientsBientotPerimesByIdIngredient(Long idIngredient) {
        LocalDate today = LocalDate.now();
        return lotIngredientRepository.findByDatePeremptionBetweenAndIngredient_IdIngredient(today, today.plusDays(3),
                idIngredient);
    }

    public List<LotIngredient> getIngredientsBientotPerimesFiltered(LocalDate startDate, LocalDate endDate,
            Long idIngredient) {
        if (idIngredient != null && startDate != null && endDate != null) {
            return lotIngredientRepository.findByDatePeremptionBetweenAndIngredient_IdIngredient(startDate, endDate,
                    idIngredient);
        }
        if (startDate != null && endDate != null) {
            return lotIngredientRepository.findByDatePeremptionBetween(startDate, endDate);
        }
        if (idIngredient != null) {
            return lotIngredientRepository.findByIngredient_IdIngredient(idIngredient);
        }
        return getIngredientsBientotPerimes();
    }

    public List<LotIngredient> getIngredientsPerimes() {
        List<LotIngredient> lots = lotIngredientRepository.findByDatePeremptionBefore(LocalDate.now());
        return lots.stream()
                .filter(lot -> getQuantiteRestantePourLot(lot) > 0)
                .toList();
    }

    public double getPerteByPeremption() {
        return getIngredientsPerimes().stream()
                .filter(lot -> lot.getPrixAchatUnitaire() != null)
                .mapToDouble(lot -> getQuantiteRestantePourLot(lot) * lot.getPrixAchatUnitaire())
                .sum();
    }

    public List<LotIngredient> getIngredientsPerimesFiltered(LocalDate startDate, LocalDate endDate,
            Long ingredientId) {
        List<LotIngredient> lots;
        if (ingredientId != null && startDate != null && endDate != null) {
            lots = lotIngredientRepository.findByDatePeremptionBetweenAndIngredient_IdIngredient(startDate, endDate,
                    ingredientId);
        } else if (startDate != null && endDate != null) {
            lots = lotIngredientRepository.findByDatePeremptionBetween(startDate, endDate);
        } else if (ingredientId != null) {
            lots = lotIngredientRepository.findByIngredient_IdIngredient(ingredientId);
        } else {
            return getIngredientsPerimes();
        }
        return lots.stream()
                .filter(lot -> getQuantiteRestantePourLot(lot) > 0)
                .toList();
    }

    public List<LotIngredient> filterIngredients(LocalDate startDate, LocalDate endDate, Long ingredientId) {
        if (ingredientId != null && startDate != null && endDate != null) {
            return lotIngredientRepository.findByDatePeremptionBetweenAndIngredient_IdIngredient(startDate, endDate,
                    ingredientId);
        }
        if (startDate != null && endDate != null) {
            return lotIngredientRepository.findByDatePeremptionBetween(startDate, endDate);
        }
        if (ingredientId != null) {
            return lotIngredientRepository.findByIngredient_IdIngredient(ingredientId);
        }
        return getAll();
    }

    public LotIngredient getById(Long id) {
        return lotIngredientRepository.findById(id).orElse(null);
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
        return lotIngredientRepository.save(lotIngredient);
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

        // if (lotIngredient.getTypeMouvement() != null &&
        // lotIngredient.getTypeMouvement().getIdTypeMouvement() != null) {
        // TypeMouvement typeMouvement = typeMouvementService
        // .getById(lotIngredient.getTypeMouvement().getIdTypeMouvement());
        // existing.setTypeMouvement(typeMouvement);
        // }

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

    // <<<<<<< HEAD
    // public List<LotIngredient> getAlertLots() {
    // return lotIngredientRepository.findAll().stream().filter(lot ->
    // lot.getIngredient() != null && lot.getIngredient().getSeuilAlerteQuantite()
    // != null && lot.getQuantiteRestante() != null && lot.getQuantiteRestante() <=
    // lot.getIngredient().getSeuilAlerteQuantite()) .toList();
    // }

    // public boolean verifierAlerte(LotIngredient lotIngredient) {
    // if (lotIngredient.getIngredient() != null &&
    // lotIngredient.getQuantiteRestante() != null) {
    // Double seuilAlerte = lotIngredient.getIngredient().getSeuilAlerteQuantite();
    // if( seuilAlerte != null && lotIngredient.getQuantiteRestante() <=
    // seuilAlerte){
    // return true;
    // }
    // }
    // return false;
    // }

    // public double quantiteLotIngredientActuelleByIngredient(Long idIngredient ){
    // Double quantiteActuelle =
    // lotIngredientRepository.sumQuantiteRestanteByIdIngredient(idIngredient);
    // return quantiteActuelle != null ? quantiteActuelle : 0.0;
    // }

    // public List<LotIngredient> getAllWithAlertStatus() {
    // List<LotIngredient> lots = lotIngredientRepository.findAll();
    // lots.forEach(lot -> {
    // lot.setAlerte(verifierAlerte(lot));
    // });
    // return lots;
    // }

    // public List<Ingredient> getAllIngredients() {
    // return ingredientRepository.findAll();
    // =======
    public List<Ingredient> getAlertLots() {
        return ingredientRepository.findAll().stream()
                .filter(ingredient -> ingredient.getSeuilAlerteQuantite() != null)
                .filter(ingredient -> getStockActuelIngredient(ingredient) <= ingredient.getSeuilAlerteQuantite())
                .toList();
    }

    public List<Ingredient> getIngredientsAlerte() {
        return getAlertLots();
    }

    public java.util.Map<Long, Double> getQuantiteTotaleParIngredientMap() {
        return ingredientRepository.findAll().stream()
                .filter(ingredient -> ingredient.getIdIngredient() != null)
                .collect(java.util.stream.Collectors.toMap(
                        Ingredient::getIdIngredient,
                        this::getStockActuelIngredient));
    }

    private double getStockActuelIngredient(Ingredient ingredient) {
        if (ingredient == null || ingredient.getIdIngredient() == null) {
            return 0.0;
        }
        double totalEntrees = calculerTotalEntreesNonPerimees(ingredient.getIdIngredient());
        double totalSorties = calculerTotalSorties(ingredient.getIdIngredient());
        return totalEntrees - totalSorties;
    }

    private double calculerTotalEntreesNonPerimees(Long idIngredient) {
        java.time.LocalDate today = LocalDate.now();
        double initialEntries = lotIngredientRepository.findByIngredient_IdIngredient(idIngredient).stream()
                .filter(lot -> lot != null && lot.getTypeMouvement() != null
                        && lot.getTypeMouvement().getIdTypeMouvement() != null
                        && lot.getTypeMouvement().getIdTypeMouvement().equals(1L)
                        && lot.getQuantiteInitiale() != null
                        && lot.getDatePeremption() != null
                        && !lot.getDatePeremption().isBefore(today))
                .mapToDouble(LotIngredient::getQuantiteInitiale)
                .sum();

        Double mouvementsEntrees = mouvementRepository.sumQuantiteByIngredientAndType(idIngredient, 1L);
        double entreesMouv = mouvementsEntrees == null ? 0.0 : mouvementsEntrees;
        return initialEntries + entreesMouv;
    }

    public double getQuantiteRestantePourLot(LotIngredient lot) {
        if (lot == null || lot.getIdLot() == null) {
            return 0.0;
        }
        double initial = lot.getQuantiteInitiale() == null ? 0.0 : lot.getQuantiteInitiale();
        Double entrees = mouvementRepository.sumQuantiteByLotAndType(lot.getIdLot(), 1L);
        Double sorties = mouvementRepository.sumQuantiteByLotAndType(lot.getIdLot(), 2L);
        double e = entrees == null ? 0.0 : entrees;
        double s = sorties == null ? 0.0 : sorties;
        return initial + e - s;
    }

    private double calculerTotalSorties(Long idIngredient) {
        double legacySorties = lotIngredientRepository.findByIngredient_IdIngredient(idIngredient).stream()
                .filter(lot -> lot != null && lot.getTypeMouvement() != null
                        && lot.getTypeMouvement().getIdTypeMouvement() != null
                        && lot.getTypeMouvement().getIdTypeMouvement().equals(2L)
                        && lot.getQuantiteInitiale() != null)
                .mapToDouble(LotIngredient::getQuantiteInitiale)
                .sum();

        Double mouvementsSorties = mouvementRepository.sumQuantiteByIngredientAndType(idIngredient, 2L);
        double sortiesMouv = mouvementsSorties == null ? 0.0 : mouvementsSorties;
        return legacySorties + sortiesMouv;
    }
}
