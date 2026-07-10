package com.mkalymlam.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mkalymlam.entity.*;
import com.mkalymlam.repository.*;

import java.time.LocalDate;
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
        
        Double quantiteTheorique = calculerQuantiteTheorique(inventaire);
        inventaire.setQuantiteTheoriqueSysteme(quantiteTheorique);
        
        double ecart = inventaire.getQuantitePhysiqueConstatee() - quantiteTheorique;
        inventaire.setEcartInventaire(ecart);
        
        return inventaireRepository.save(inventaire);
    }

    private Double calculerQuantiteTheorique(InventaireJournalier inventaire) {
        if (inventaire.getTypeItem() == null || inventaire.getIdItem() == null) {
            return 0.0;
        }

        // // INGREDIENT
        // if (inventaire.getTypeItem().getIdTypeItem() == 1) {
        //     List<LotIngredient> lots = lotIngredientRepository.findByIngredient_IdIngredient(
        //         inventaire.getIdItem()
        //     );
            
        //     return lots.stream()
        //         .mapToDouble(LotIngredient::getQuantiteRestante)
        //         .sum();
        // }
        
        // EQUIPEMENT
        if (inventaire.getTypeItem().getIdTypeItem() == 2) {
            return calculerStockEquipement(inventaire.getIdItem());
        }
        
        return 0.0;
    }

    private Double calculerStockEquipement(Long idEquipement) {
        Double entree = mouvementEquipementRepository.sumEntreeByEquipement(idEquipement);
        Double sortie = mouvementEquipementRepository.sumSortieByEquipement(idEquipement);
        
        entree = entree != null ? entree : 0.0;
        sortie = sortie != null ? sortie : 0.0;
        
        return entree - sortie;
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
        }
        
        Double quantiteTheorique = calculerQuantiteTheorique(existing);
        existing.setQuantiteTheoriqueSysteme(quantiteTheorique);
        
        double ecart = existing.getQuantitePhysiqueConstatee() - quantiteTheorique;
        existing.setEcartInventaire(ecart);
        
        return inventaireRepository.save(existing);
    }

    private String getNomItem(Long idTypeItem, Long idItem) {
        if (idTypeItem == null || idItem == null) {
            return "Non défini";
        }

        try {
            if (idTypeItem == 1) { 
                Ingredient ingredient = ingredientRepository.findById(idItem).orElse(null);
                return ingredient != null ? ingredient.getNomIngredient() : "Ingrédient inconnu (ID: " + idItem + ")";
            } else if (idTypeItem == 2) { 
                Equipement equipement = equipementRepository.findById(idItem).orElse(null);
                return equipement != null ? equipement.getNomEquipement() : "Équipement inconnu (ID: " + idItem + ")";
            } else {
                return "Type inconnu (ID: " + idItem + ")";
            }
        } catch (Exception e) {
            return "Erreur chargement (ID: " + idItem + ")";
        }
    }

    @Transactional
    public void delete(Long id) {
        inventaireRepository.deleteById(id);
    }

    public List<InventaireJournalier> getAllWithItemNames() {
        List<InventaireJournalier> inventaires = inventaireRepository.findAllByOrderByDateInventaireAsc();
        return enrichirAvecNomItem(inventaires);
    }

    public List<InventaireJournalier> getAllDescWithItemNames() {
        List<InventaireJournalier> inventaires = inventaireRepository.findAllByOrderByDateInventaireDesc();
        return enrichirAvecNomItem(inventaires);
    }

    public InventaireJournalier getByIdWithItemName(Long id) {
        InventaireJournalier inventaire = inventaireRepository.findById(id).orElse(null);
        if (inventaire != null) {
            enrichirAvecNomItem(inventaire);
        }
        return inventaire;
    }

    public List<InventaireJournalier> findBySessionWithItemNames(Long idSession) {
        List<InventaireJournalier> inventaires = inventaireRepository.findBySessionTruckId(idSession);
        return enrichirAvecNomItem(inventaires);
    }

    public List<InventaireJournalier> findByAvecEcartWithItemNames() {
        List<InventaireJournalier> inventaires = inventaireRepository.findWithEcart();
        return enrichirAvecNomItem(inventaires);
    }

    private void enrichirAvecNomItem(InventaireJournalier inventaire) {
        if (inventaire == null || inventaire.getTypeItem() == null || inventaire.getIdItem() == null) {
            return;
        }

        String nomItem = getNomItem(inventaire.getTypeItem().getIdTypeItem(), inventaire.getIdItem());
        inventaire.setNomItem(nomItem);
    }

    private List<InventaireJournalier> enrichirAvecNomItem(List<InventaireJournalier> inventaires) {
        if (inventaires == null) {
            return null;
        }
        
        for (InventaireJournalier inventaire : inventaires) {
            enrichirAvecNomItem(inventaire);
        }
        return inventaires;
    }

    

    public List<InventaireJournalier> getAll() {
        return getAllWithItemNames();
    }

    public List<InventaireJournalier> getAllDesc() {
        return getAllDescWithItemNames();
    }

    public InventaireJournalier getById(Long id) {
        return getByIdWithItemName(id);
    }

    public List<InventaireJournalier> findBySession(Long idSession) {
        return findBySessionWithItemNames(idSession);
    }

    public List<InventaireJournalier> findByAvecEcart() {
        return findByAvecEcartWithItemNames();
    }

    public List<TypeItem> getAllTypeItems() {
        return typeItemRepository.findAll();
    }

    public List<SessionTruck> getAllSessionTrucks() {
        return sessionTruckRepository.findAll();
    }

    public List<InventaireJournalier> findByDateInventaire(LocalDate dateInventaire) {
        List<InventaireJournalier> inventaires = inventaireRepository.findByDateInventaire(dateInventaire);
        return enrichirAvecNomItem(inventaires);
    }

    public boolean verifierEcartExistant(InventaireJournalier inventaire) {
        return inventaire.getEcartInventaire() != 0;
    }

    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public List<Equipement> getAllEquipements() {
        return equipementRepository.findAll();
    }
}