package com.mkalymlam.service;

import java.time.LocalDate;  // IMPORT AJOUTÉ
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mkalymlam.entity.HistoriqueConsommation;
import com.mkalymlam.entity.Ingredient;
import com.mkalymlam.entity.LigneCommande;
import com.mkalymlam.entity.LotIngredient;
import com.mkalymlam.entity.MouvementLotIngredient;
import com.mkalymlam.entity.RecetteBase;
import com.mkalymlam.entity.TypeMouvement;
import com.mkalymlam.repository.HistoriqueConsommationRepository;
import com.mkalymlam.repository.IngredientRepository;
import com.mkalymlam.repository.LigneCommandeRepository;
import com.mkalymlam.repository.LotIngredientRepository;
import com.mkalymlam.repository.MouvementLotIngredientRepository;
import com.mkalymlam.repository.RecetteBaseRepository;
import com.mkalymlam.repository.TypeMouvementRepository;

@Service
public class ConsommationService {

    private final RecetteBaseRepository recetteBaseRepository;
    private final LotIngredientRepository lotIngredientRepository;
    private final MouvementLotIngredientRepository mouvementLotIngredientRepository;
    private final TypeMouvementRepository typeMouvementRepository;
    private final LigneCommandeRepository ligneCommandeRepository;
    private final HistoriqueConsommationRepository historiqueConsommationRepository;
    private final IngredientRepository ingredientRepository;

    public ConsommationService(RecetteBaseRepository recetteBaseRepository,
                               LotIngredientRepository lotIngredientRepository,
                               MouvementLotIngredientRepository mouvementLotIngredientRepository,
                               TypeMouvementRepository typeMouvementRepository,
                               LigneCommandeRepository ligneCommandeRepository,
                               HistoriqueConsommationRepository historiqueConsommationRepository,
                               IngredientRepository ingredientRepository) {
        this.recetteBaseRepository = recetteBaseRepository;
        this.lotIngredientRepository = lotIngredientRepository;
        this.mouvementLotIngredientRepository = mouvementLotIngredientRepository;
        this.typeMouvementRepository = typeMouvementRepository;
        this.ligneCommandeRepository = ligneCommandeRepository;
        this.historiqueConsommationRepository = historiqueConsommationRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Transactional
    public void deduireIngredients(Long idCommande, Long idSession) {
        List<LigneCommande> lignes = ligneCommandeRepository.findByIdCommande(idCommande);
        TypeMouvement sortie = typeMouvementRepository.findByLibelle("SORTIE")
                .orElseThrow(() -> new RuntimeException("TypeMouvement SORTIE introuvable"));

        for (LigneCommande ligne : lignes) {
            List<RecetteBase> recettes = recetteBaseRepository.findByIdProduit(ligne.getIdProduit());
            for (RecetteBase recette : recettes) {
                double quantiteNecessaire = recette.getQuantiteRecette() * ligne.getQuantite();
                Ingredient ingredient = ingredientRepository.findById(recette.getIdIngredient())
                        .orElseThrow(() -> new RuntimeException("Ingredient introuvable : " + recette.getIdIngredient()));

                List<LotIngredient> lots = lotIngredientRepository.findByIngredient_IdIngredient(ingredient.getIdIngredient());
                double restante = quantiteNecessaire;

                for (LotIngredient lot : lots) {
                    if (restante <= 0) break;
                    if (lot.getQuantiteRestante() == null || lot.getQuantiteRestante() <= 0) continue;

                    double aDeduire = Math.min(lot.getQuantiteRestante(), restante);
                    lot.setQuantiteRestante(lot.getQuantiteRestante() - aDeduire);
                    lotIngredientRepository.save(lot);

                    // Correction : utilisation du bon constructeur avec LocalDate.now()
                    MouvementLotIngredient mouvement = new MouvementLotIngredient(lot, sortie, aDeduire, LocalDate.now());
                    mouvementLotIngredientRepository.save(mouvement);

                    HistoriqueConsommation historique = new HistoriqueConsommation(null, ingredient, aDeduire, null);
                    historiqueConsommationRepository.save(historique);

                    restante -= aDeduire;
                }

                if (restante > 0) {
                    throw new RuntimeException("Stock insuffisant pour l'ingredient : " + ingredient.getNomIngredient()
                            + " (manque " + restante + " " + ingredient.getUniteMesure() + ")");
                }
            }
        }
    }

    @Transactional
    public void remettreEnStock(Long idCommande, Long idSession) {
        List<HistoriqueConsommation> historiques = historiqueConsommationRepository.findByCommande_IdCommande(idCommande);
        TypeMouvement entree = typeMouvementRepository.findByLibelle("ENTREE")
                .orElseThrow(() -> new RuntimeException("TypeMouvement ENTREE introuvable"));

        for (HistoriqueConsommation h : historiques) {
            List<LotIngredient> lots = lotIngredientRepository.findByIngredient_IdIngredient(h.getIngredient().getIdIngredient());
            if (!lots.isEmpty()) {
                LotIngredient dernierLot = lots.get(lots.size() - 1);
                dernierLot.setQuantiteRestante(dernierLot.getQuantiteRestante() + h.getQuantiteConsommee());
                lotIngredientRepository.save(dernierLot);

                // Correction : utilisation du bon constructeur avec LocalDate.now()
                MouvementLotIngredient mouvement = new MouvementLotIngredient(dernierLot, entree, h.getQuantiteConsommee(), LocalDate.now());
                mouvementLotIngredientRepository.save(mouvement);
            }
        }
    }

    public List<HistoriqueConsommation> getHistoriqueParSession(Long idSession) {
        return historiqueConsommationRepository.findBySession_Id(idSession);
    }

    public List<HistoriqueConsommation> getHistoriqueParIngredient(Long idIngredient) {
        return historiqueConsommationRepository.findByIngredient_IdIngredient(idIngredient);
    }

    public List<HistoriqueConsommation> getHistoriqueParSessionEtIngredient(Long idSession, Long idIngredient) {
        return historiqueConsommationRepository.findBySession_IdAndIngredient_IdIngredient(idSession, idIngredient);
    }
}