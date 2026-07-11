package com.mkalymlam.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mkalymlam.entity.*;
import com.mkalymlam.repository.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class InventaireJournalierService {

    private final InventaireJournalierRepository inventaireRepository;
    private final SessionTruckRepository sessionTruckRepository;
    private final TypeItemRepository typeItemRepository;
    private final IngredientRepository ingredientRepository;
    private final EquipementRepository equipementRepository;
    private final LotIngredientRepository lotIngredientRepository;
    private final MouvementEquipementRepository mouvementEquipementRepository;

    public InventaireJournalierService(
            InventaireJournalierRepository inventaireRepository,
            SessionTruckRepository sessionTruckRepository,
            TypeItemRepository typeItemRepository,
            IngredientRepository ingredientRepository,
            EquipementRepository equipementRepository,
            LotIngredientRepository lotIngredientRepository,
            MouvementEquipementRepository mouvementEquipementRepository) {
        this.inventaireRepository = inventaireRepository;
        this.sessionTruckRepository = sessionTruckRepository;
        this.typeItemRepository = typeItemRepository;
        this.ingredientRepository = ingredientRepository;
        this.equipementRepository = equipementRepository;
        this.lotIngredientRepository = lotIngredientRepository;
        this.mouvementEquipementRepository = mouvementEquipementRepository;
    }

    @Transactional
    public InventaireJournalier save(InventaireJournalier inventaire) {
        if (inventaire.getQuantitePhysiqueConstatee() == null) {
            throw new IllegalArgumentException("La quantité physique est obligatoire");
        }
        if (inventaire.getSessionTruck() == null) {
            throw new IllegalArgumentException("La session est obligatoire");
        }
        if (inventaire.getTypeItem() == null) {
            throw new IllegalArgumentException("Le type d'item est obligatoire");
        }
        if (inventaire.getIdItem() == null) {
            throw new IllegalArgumentException("L'ID de l'item est obligatoire");
        }

        Double quantiteTheorique = calculerQuantiteTheorique(inventaire);
        inventaire.setQuantiteTheoriqueSysteme(quantiteTheorique);

        double ecart = inventaire.getQuantitePhysiqueConstatee() - quantiteTheorique;
        inventaire.setEcartInventaire(ecart);

        if (inventaire.getDateInventaire() == null) {
            inventaire.setDateInventaire(LocalDate.now());
        }

        return inventaireRepository.save(inventaire);
    }

    private Double calculerQuantiteTheorique(InventaireJournalier inventaire) {
        if (inventaire.getTypeItem() == null || inventaire.getIdItem() == null) {
            return 0.0;
        }

        Long idTypeItem = inventaire.getTypeItem().getIdTypeItem();

        // INGREDIENT (idTypeItem = 1)
        if (idTypeItem == 1) {
            return lotIngredientRepository.sumQuantiteRestanteByIdIngredient(inventaire.getIdItem());
        }

        // EQUIPEMENT (idTypeItem = 2)
        if (idTypeItem == 2) {
            Double entree = mouvementEquipementRepository.sumEntreeByEquipement(inventaire.getIdItem());
            Double sortie = mouvementEquipementRepository.sumSortieByEquipement(inventaire.getIdItem());

            entree = entree != null ? entree : 0.0;
            sortie = sortie != null ? sortie : 0.0;

            return entree - sortie;
        }

        return 0.0;
    }

    @Transactional
    public InventaireJournalier update(Long id, InventaireJournalier inventaire) {
        InventaireJournalier existing = getById(id);
        if (existing == null) {
            throw new RuntimeException("Inventaire non trouvé avec l'ID : " + id);
        }

        if (inventaire.getSessionTruck() != null) {
            existing.setSessionTruck(inventaire.getSessionTruck());
        }
        if (inventaire.getDateInventaire() != null) {
            existing.setDateInventaire(inventaire.getDateInventaire());
        }
        if (inventaire.getTypeItem() != null) {
            existing.setTypeItem(inventaire.getTypeItem());
        }
        if (inventaire.getIdItem() != null) {
            existing.setIdItem(inventaire.getIdItem());
        }
        if (inventaire.getQuantitePhysiqueConstatee() != null) {
            existing.setQuantitePhysiqueConstatee(inventaire.getQuantitePhysiqueConstatee());

            // recalculer la quantité théorique et l'écart
            Double quantiteTheorique = calculerQuantiteTheorique(existing);
            existing.setQuantiteTheoriqueSysteme(quantiteTheorique);

            double ecart = existing.getQuantitePhysiqueConstatee() - quantiteTheorique;
            existing.setEcartInventaire(ecart);
        }

        return inventaireRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        inventaireRepository.deleteById(id);
    }

    // méthodes de recherche
    public List<InventaireJournalier> getAll() {
        return inventaireRepository.findAllByOrderByDateInventaireDesc();
    }

    public InventaireJournalier getById(Long id) {
        return inventaireRepository.findById(id).orElse(null);
    }

    public List<InventaireJournalier> findBySession(Long idSession) {
        return inventaireRepository.findBySessionTruckId(idSession);
    }

    public List<InventaireJournalier> findByDateInventaire(LocalDate dateInventaire) {
        return inventaireRepository.findByDateInventaire(dateInventaire);
    }

    public List<InventaireJournalier> findByAvecEcart() {
        return inventaireRepository.findWithEcart();
    }

    // méthodes utilitaires
    public List<SessionTruck> getAllSessionTrucks() {
        return sessionTruckRepository.findAll();
    }

    public List<TypeItem> getAllTypeItems() {
        return typeItemRepository.findAll();
    }

    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public List<Equipement> getAllEquipements() {
        return equipementRepository.findAll();
    }
}