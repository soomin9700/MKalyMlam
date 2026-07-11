package com.mkalymlam.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mkalymlam.entity.Commande;
import com.mkalymlam.entity.Ingredient;
import com.mkalymlam.entity.LotIngredient;
import com.mkalymlam.entity.NotificationPlateforme;
import com.mkalymlam.entity.TypeNotification;
import com.mkalymlam.repository.IngredientRepository;
import com.mkalymlam.repository.LotIngredientRepository;
import com.mkalymlam.repository.NotificationPlateformeRepository;
import com.mkalymlam.repository.TypeNotificationRepository;

@Service
public class NotificationService {

    private final NotificationPlateformeRepository notificationRepository;
    private final TypeNotificationRepository typeNotificationRepository;
    private final LotIngredientRepository lotIngredientRepository;
    private final IngredientRepository ingredientRepository;

    public NotificationService(NotificationPlateformeRepository notificationRepository,
                               TypeNotificationRepository typeNotificationRepository,
                               LotIngredientRepository lotIngredientRepository,
                               IngredientRepository ingredientRepository) {
        this.notificationRepository = notificationRepository;
        this.typeNotificationRepository = typeNotificationRepository;
        this.lotIngredientRepository = lotIngredientRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Transactional
    public void verifierAlerteStock() {
        TypeNotification typeAlerte = typeNotificationRepository.findByLibelle("ALERTE_STOCK")
                .orElseThrow(() -> new RuntimeException("TypeNotification ALERTE_STOCK introuvable"));

        List<Ingredient> ingredients = ingredientRepository.findAll();
        for (Ingredient ingredient : ingredients) {
            Double stockTotal = lotIngredientRepository.sumQuantiteRestanteByIdIngredient(ingredient.getIdIngredient());
            if (stockTotal != null && stockTotal <= ingredient.getSeuilAlerteQuantite()) {
                NotificationPlateforme notif = new NotificationPlateforme(typeAlerte,
                        "Alerte stock : " + ingredient.getNomIngredient(),
                        "Le stock de " + ingredient.getNomIngredient() + " est à " + stockTotal + " " + ingredient.getUniteMesure()
                        + " (seuil : " + ingredient.getSeuilAlerteQuantite() + ")");
                notificationRepository.save(notif);
            }
        }
    }

    @Transactional
    public void notifierHeureRecuperation(Commande commande) {
        if (commande.getHeureRecuperationPrevue() == null) return;

        long minutesRestantes = ChronoUnit.MINUTES.between(LocalDateTime.now(), commande.getHeureRecuperationPrevue());
        if (minutesRestantes > 0 && minutesRestantes <= 15) {
            TypeNotification typeRecup = typeNotificationRepository.findByLibelle("HEURE_RECUPERATION")
                    .orElseThrow(() -> new RuntimeException("TypeNotification HEURE_RECUPERATION introuvable"));
            NotificationPlateforme notif = new NotificationPlateforme(typeRecup,
                    "Récupération imminent - Commande #" + commande.getIdCommande(),
                    "La commande #" + commande.getIdCommande() + " doit être récupérée dans " + minutesRestantes + " minutes.");
            if (commande.getSessionTruck() != null) {
                notif.setIdSessionLiee(commande.getSessionTruck().getId());
            }
            notificationRepository.save(notif);
        }
    }

    @Transactional
    public void notifierCommandeAnnulee(Commande commande) {
        TypeNotification typeAnnulee = typeNotificationRepository.findByLibelle("COMMANDE_ANNULEE")
                .orElseThrow(() -> new RuntimeException("TypeNotification COMMANDE_ANNULEE introuvable"));
        NotificationPlateforme notif = new NotificationPlateforme(typeAnnulee,
                "Commande annulée #" + commande.getIdCommande(),
                "La commande #" + commande.getIdCommande() + " a été annulée.");
        if (commande.getSessionTruck() != null) {
            notif.setIdSessionLiee(commande.getSessionTruck().getId());
        }
        notificationRepository.save(notif);
    }

    public List<NotificationPlateforme> getNotificationsParSession(Long idSession) {
        return notificationRepository.findBySessionLiee(idSession);
    }

    public List<NotificationPlateforme> getNotificationsParType(String typeLibelle) {
        return notificationRepository.findByTypeNotification_Libelle(typeLibelle);
    }
}
