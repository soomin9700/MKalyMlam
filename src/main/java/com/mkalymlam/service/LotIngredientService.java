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

@Service
public class LotIngredientService {

    private final LotIngredientRepository lotIngredientRepository;
    private final IngredientRepository ingredientRepository;
    private final TypeMouvementService typeMouvementService;

    public LotIngredientService(LotIngredientRepository lotIngredientRepository,
            IngredientRepository ingredientRepository,
            TypeMouvementService typeMouvementService) {
        this.lotIngredientRepository = lotIngredientRepository;
        this.ingredientRepository = ingredientRepository;
        this.typeMouvementService = typeMouvementService;
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
        return lotIngredientRepository.findByDatePeremptionBefore(LocalDate.now());
    }

    public double getPerteByPeremption() {
        return getIngredientsPerimes().stream()
                .filter(lot -> lot.getQuantiteInitiale() != null && lot.getPrixAchatUnitaire() != null)
                .mapToDouble(lot -> lot.getQuantiteInitiale() * lot.getPrixAchatUnitaire())
                .sum();
    }

    public List<LotIngredient> getIngredientsPerimesFiltered(LocalDate startDate, LocalDate endDate,
            Long ingredientId) {
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
        return getIngredientsPerimes();
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
        if (lotIngredient.getTypeMouvement() != null && lotIngredient.getTypeMouvement().getIdTypeMouvement() != null) {
            TypeMouvement typeMouvement = typeMouvementService
                    .getById(lotIngredient.getTypeMouvement().getIdTypeMouvement());
            lotIngredient.setTypeMouvement(typeMouvement);
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
        if (lotIngredient.getTypeMouvement() != null && lotIngredient.getTypeMouvement().getIdTypeMouvement() != null) {
            TypeMouvement typeMouvement = typeMouvementService
                    .getById(lotIngredient.getTypeMouvement().getIdTypeMouvement());
            existing.setTypeMouvement(typeMouvement);
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
        return lotIngredientRepository.findByIngredient_IdIngredient(idIngredient).stream()
                .filter(lot -> lot != null && lot.getTypeMouvement() != null
                        && lot.getTypeMouvement().getIdTypeMouvement() != null
                        && lot.getTypeMouvement().getIdTypeMouvement().equals(1L)
                        && lot.getQuantiteInitiale() != null
                        && lot.getDatePeremption() != null
                        && !lot.getDatePeremption().isBefore(today))
                .mapToDouble(LotIngredient::getQuantiteInitiale)
                .sum();
    }

    private double calculerTotalSorties(Long idIngredient) {
        return lotIngredientRepository.findByIngredient_IdIngredient(idIngredient).stream()
                .filter(lot -> lot != null && lot.getTypeMouvement() != null
                        && lot.getTypeMouvement().getIdTypeMouvement() != null
                        && lot.getTypeMouvement().getIdTypeMouvement().equals(2L)
                        && lot.getQuantiteInitiale() != null)
                .mapToDouble(LotIngredient::getQuantiteInitiale)
                .sum();
    }
}
