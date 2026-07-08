package com.mkalymlam.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mkalymlam.entity.Notification;
import com.mkalymlam.entity.Produit;
import com.mkalymlam.entity.SessionTruck;
import com.mkalymlam.entity.TypeNotification;
import com.mkalymlam.entity.Utilisateur;
import com.mkalymlam.repository.NotificationRepository;
import com.mkalymlam.repository.ProduitRepository;
import com.mkalymlam.repository.SessionTruckRepository;
import com.mkalymlam.repository.UtilisateurRepository;

@Service
public class NotificationService {

    private final NotificationRepository repository;
    private final ProduitRepository produitRepository;
    private final SessionTruckRepository sessionTruckRepository;
    private final UtilisateurRepository utilisateurRepository;

    public NotificationService(NotificationRepository repository,
                                ProduitRepository produitRepository,
                                SessionTruckRepository sessionTruckRepository,
                                UtilisateurRepository utilisateurRepository) {
        this.repository = repository;
        this.produitRepository = produitRepository;
        this.sessionTruckRepository = sessionTruckRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    private Utilisateur resoudreAuteur(Integer auteurId) {
        if (auteurId == null) {
            return null;
        }
        return utilisateurRepository.findById(auteurId)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable : " + auteurId));
    }

    // ---------- 6.3.1 Publier une notification (générale) ----------
    public Notification publier(String titre, String contenu, Integer auteurId) {
        Notification notification = new Notification(titre, contenu, TypeNotification.GENERALE);
        notification.setAuteur(resoudreAuteur(auteurId));
        notification.setDateCreation(LocalDateTime.now());
        notification.setPublie(true);
        return repository.save(notification);
    }

    // ---------- 6.3.2 Annoncer un nouveau produit ----------
    public Notification annoncerNouveauProduit(Long idProduit, String titre, String contenu, Integer auteurId) {
        Produit produit = produitRepository.findById(idProduit)
                .orElseThrow(() -> new IllegalArgumentException("Produit introuvable : " + idProduit));

        Notification notification = new Notification(titre, contenu, TypeNotification.NOUVEAU_PRODUIT);
        notification.setProduit(produit);
        notification.setAuteur(resoudreAuteur(auteurId));
        notification.setDateCreation(LocalDateTime.now());
        notification.setPublie(true);
        return repository.save(notification);
    }

    // ---------- 6.3.3 Annoncer le point de vente actuel ----------
    // Publication manuelle par un employé ou l'administrateur (pas de suivi GPS).
    public Notification annoncerPointDeVente(Long sessionId, String contenu, Integer auteurId) {
        SessionTruck session = sessionTruckRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Session introuvable : " + sessionId));

        Notification notification = new Notification("Emplacement actuel", contenu, TypeNotification.POINT_DE_VENTE);
        notification.setSession(session);
        notification.setAuteur(resoudreAuteur(auteurId));
        notification.setDateCreation(LocalDateTime.now());
        notification.setPublie(true);
        return repository.save(notification);
    }

    // ---------- 6.3.4 Consulter les notifications ----------
    public List<Notification> findAll() {
        return repository.findAll();
    }

    public List<Notification> findDernieres() {
        return repository.findByPublieTrueOrderByDateCreationDesc();
    }

    public List<Notification> findByProduit(Long idProduit) {
        return repository.findByProduit_IdProduitOrderByDateCreationDesc(idProduit);
    }

    public List<Notification> findBySession(Long sessionId) {
        return repository.findBySession_IdOrderByDateCreationDesc(sessionId);
    }

    public List<Notification> findByType(TypeNotification type) {
        return repository.findByTypeOrderByDateCreationDesc(type);
    }

    public Notification getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    // ---------- 6.3.5 Modifier une notification ----------
    public Notification modifier(Long id, String titre, String contenu, Boolean publie) {
        Notification notification = getById(id);
        if (notification == null) {
            throw new IllegalArgumentException("Notification introuvable : " + id);
        }
        notification.setTitre(titre);
        notification.setContenu(contenu);
        if (publie != null) {
            notification.setPublie(publie);
        }
        notification.setDateModification(LocalDateTime.now());
        return repository.save(notification);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}