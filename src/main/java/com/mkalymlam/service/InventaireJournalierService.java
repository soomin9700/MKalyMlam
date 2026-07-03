package com.mkalymlam.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mkalymlam.entity.*;
import com.mkalymlam.repository.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
    public InventaireJournalier save(InventaireJournalier inventaire) {
        if (inventaire.getQuantitePhysiqueConstatee() == null) {
            throw new IllegalArgumentException("La quantité physique est obligatoire");
        }
        
        double ecart = inventaire.getQuantitePhysiqueConstatee() 
                       - inventaire.getQuantiteTheoriqueSysteme();
        inventaire.setEcartInventaire(ecart);
        
        return inventaireRepository.save(inventaire);
    }

    // ✅ Méthode pour enrichir les inventaires avec le nom de l'item
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

    // ✅ Méthode privée pour enrichir un inventaire avec le nom de l'item
    private void enrichirAvecNomItem(InventaireJournalier inventaire) {
        if (inventaire == null || inventaire.getTypeItem() == null || inventaire.getIdItem() == null) {
            return;
        }

        String nomItem = getNomItem(inventaire.getTypeItem().getIdTypeItem(), inventaire.getIdItem());
        inventaire.setNomItem(nomItem);
    }

    // ✅ Méthode privée pour enrichir une liste d'inventaires
    private List<InventaireJournalier> enrichirAvecNomItem(List<InventaireJournalier> inventaires) {
        if (inventaires == null) {
            return null;
        }
        
        for (InventaireJournalier inventaire : inventaires) {
            enrichirAvecNomItem(inventaire);
        }
        return inventaires;
    }

    // ✅ Méthode pour récupérer le nom de l'item selon son type
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

    // ✅ Méthodes existantes (avec enrichissement)
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

    // ✅ Méthode utilitaire pour récupérer tous les ingrédients et équipements
    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public List<Equipement> getAllEquipements() {
        return equipementRepository.findAll();
    }
}