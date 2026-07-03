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

    public InventaireJournalierService(
            InventaireJournalierRepository inventaireRepository, 
            SessionTruckRepository sessionTruckRepository, 
            TypeItemRepository typeItemRepository,
            IngredientRepository ingredientRepository,
            EquipementRepository equipementRepository) {
        this.inventaireRepository = inventaireRepository;
        this.sessionTruckRepository = sessionTruckRepository;
        this.typeItemRepository = typeItemRepository;
        this.ingredientRepository = ingredientRepository;
        this.equipementRepository = equipementRepository;
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
        if (inventaire.getQuantiteTheoriqueSysteme() != null) {
            existing.setQuantiteTheoriqueSysteme(inventaire.getQuantiteTheoriqueSysteme());
        }
        
        // recalculer l'écart
        double ecart = existing.getQuantitePhysiqueConstatee() - existing.getQuantiteTheoriqueSysteme();
        existing.setEcartInventaire(ecart);
        
        return inventaireRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        inventaireRepository.deleteById(id);
    }
    
    @Transactional
    public InventaireJournalier save(InventaireJournalier inventaire) {
        if (inventaire.getQuantitePhysiqueConstatee() == null) {
            throw new IllegalArgumentException("La quantité physique est obligatoire");
        }
        
        if (inventaire.getTypeItem() != null && inventaire.getIdItem() != null) {
            if (inventaire.getTypeItem().getIdTypeItem() == 1) {
                // INGREDIENT
                ingredientRepository.findById(inventaire.getIdItem())
                    .orElseThrow(() -> new RuntimeException("Ingrédient non trouvé"));
            } else if (inventaire.getTypeItem().getIdTypeItem() == 2) {
                // EQUIPEMENT
                equipementRepository.findById(inventaire.getIdItem())
                    .orElseThrow(() -> new RuntimeException("Équipement non trouvé"));
            }
        }
        
        double ecart = inventaire.getQuantitePhysiqueConstatee() 
                    - inventaire.getQuantiteTheoriqueSysteme();
        inventaire.setEcartInventaire(ecart);
        
        return inventaireRepository.save(inventaire);
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

    // récupérer le nom de l'item selon son type
    private String getNomItem(Long idTypeItem, Long idItem) {
        if (idTypeItem == null || idItem == null) {
            return "Non défini";
        }

        try {
            if (idTypeItem == 1) { // INGREDIENT
                Ingredient ingredient = ingredientRepository.findById(idItem).orElse(null);
                return ingredient != null ? ingredient.getNomIngredient() : "Ingrédient inconnu (ID: " + idItem + ")";
            } else if (idTypeItem == 2) { // EQUIPEMENT
                Equipement equipement = equipementRepository.findById(idItem).orElse(null);
                return equipement != null ? equipement.getNomEquipement() : "Équipement inconnu (ID: " + idItem + ")";
            } else {
                return "Type inconnu (ID: " + idItem + ")";
            }
        } catch (Exception e) {
            return "Erreur chargement (ID: " + idItem + ")";
        }
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