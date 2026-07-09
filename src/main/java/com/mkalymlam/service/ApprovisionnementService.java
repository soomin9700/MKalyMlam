package com.mkalymlam.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mkalymlam.dto.ApprovisionnementForm;
import com.mkalymlam.dto.BesoinApprovisionnement;
import com.mkalymlam.dto.DetailApprovisionnementForm;
import com.mkalymlam.entity.Approvisionnement;
import com.mkalymlam.entity.DetailApprovisionnement;
import com.mkalymlam.entity.Ingredient;
import com.mkalymlam.entity.LotIngredient;
import com.mkalymlam.repository.ApprovisionnementRepository;
import com.mkalymlam.repository.DetailApprovisionnementRepository;
import com.mkalymlam.repository.IngredientRepository;
import com.mkalymlam.repository.LotIngredientRepository;

@Service
public class ApprovisionnementService {

    private final ApprovisionnementRepository approvisionnementRepository;
    private final DetailApprovisionnementRepository detailApprovisionnementRepository;
    private final IngredientRepository ingredientRepository;
    private final LotIngredientRepository lotIngredientRepository;

    public ApprovisionnementService(ApprovisionnementRepository approvisionnementRepository,
                                    DetailApprovisionnementRepository detailApprovisionnementRepository,
                                    IngredientRepository ingredientRepository,
                                    LotIngredientRepository lotIngredientRepository) {
        this.approvisionnementRepository = approvisionnementRepository;
        this.detailApprovisionnementRepository = detailApprovisionnementRepository;
        this.ingredientRepository = ingredientRepository;
        this.lotIngredientRepository = lotIngredientRepository;
    }

    public List<BesoinApprovisionnement> calculerBesoins() {
        return ingredientRepository.findAll().stream()
                .filter(ingredient -> ingredient.getSeuilAlerteQuantite() != null)
                .filter(ingredient -> Boolean.TRUE.equals(ingredient.getActif()))
                .map(this::calculerBesoin)
                .filter(besoin -> besoin.getStockActuel() < besoin.getSeuilStock())
                .toList();
    }

    public List<Approvisionnement> historique() {
        return approvisionnementRepository.findAllByOrderByDateApprovisionnementDescIdApprovisionnementDesc();
    }

    public Approvisionnement findById(Long idApprovisionnement) {
        return approvisionnementRepository.findById(idApprovisionnement)
                .orElseThrow(() -> new IllegalArgumentException("Approvisionnement introuvable"));
    }

    public List<DetailApprovisionnement> details(Long idApprovisionnement) {
        return detailApprovisionnementRepository
                .findByApprovisionnement_IdApprovisionnementOrderByIdDetailApprovisionnementAsc(idApprovisionnement);
    }

    @Transactional
    public Approvisionnement enregistrer(ApprovisionnementForm form) {
        Approvisionnement approvisionnement = new Approvisionnement();
        approvisionnement.setDateApprovisionnement(LocalDate.now());
        approvisionnement.setCoutTotalEstime(0.0);
        approvisionnement = approvisionnementRepository.save(approvisionnement);

        double total = 0.0;
        if (form.getDetails() != null) {
            for (DetailApprovisionnementForm ligne : form.getDetails()) {
                if (ligne.getIdIngredient() == null || value(ligne.getQuantiteAAcheter()) <= 0) {
                    continue;
                }

                Ingredient ingredient = ingredientRepository.findById(ligne.getIdIngredient())
                        .orElseThrow(() -> new IllegalArgumentException("Ingredient introuvable"));
                double prix = value(ligne.getPrixEstimeUnitaire());
                double quantite = value(ligne.getQuantiteAAcheter());
                double cout = prix * quantite;

                DetailApprovisionnement detail = new DetailApprovisionnement();
                detail.setApprovisionnement(approvisionnement);
                detail.setIngredient(ingredient);
                detail.setStockActuel(value(ligne.getStockActuel()));
                detail.setPrixEstimeUnitaire(prix);
                detail.setQuantiteAAcheter(quantite);
                detail.setCoutEstime(cout);
                detailApprovisionnementRepository.save(detail);

                total += cout;
            }
        }

        approvisionnement.setCoutTotalEstime(total);
        return approvisionnementRepository.save(approvisionnement);
    }

    private BesoinApprovisionnement calculerBesoin(Ingredient ingredient) {
        double stockActuel = value(lotIngredientRepository
                .sumQuantiteRestanteByIdIngredient(ingredient.getIdIngredient()));
        double seuil = value(ingredient.getSeuilAlerteQuantite());
        double prix = lotIngredientRepository
                .findFirstByIngredient_IdIngredientOrderByDateReceptionDescIdLotDesc(ingredient.getIdIngredient())
                .map(LotIngredient::getPrixAchatUnitaire)
                .map(this::value)
                .orElse(0.0);
        double quantite = Math.max(seuil - stockActuel, 0.0);
        double cout = prix * quantite;

        return new BesoinApprovisionnement(
                ingredient.getIdIngredient(),
                ingredient.getNomIngredient(),
                ingredient.getUniteMesure(),
                stockActuel,
                seuil,
                prix,
                quantite,
                cout);
    }

    private double value(Double nombre) {
        return nombre == null ? 0.0 : nombre;
    }
}
