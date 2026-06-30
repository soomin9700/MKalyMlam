package com.mkalymlam.service;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mkalymlam.entity.Commande;
import com.mkalymlam.entity.FactureRecu;
import com.mkalymlam.entity.ModePaiement;
import com.mkalymlam.entity.StatutCommande;
import com.mkalymlam.repository.CommandeRepository;
import com.mkalymlam.repository.FactureRecuRepository;

@Service
public class FactureRecuService {

    private final FactureRecuRepository factureRecuRepository;
    private final CommandeRepository commandeRepository;

    public FactureRecuService(FactureRecuRepository factureRecuRepository,
                              CommandeRepository commandeRepository) {
        this.factureRecuRepository = factureRecuRepository;
        this.commandeRepository = commandeRepository;
    }

    public FactureRecu genererFacture(Long idCommande, ModePaiement modePaiement) {
        Commande commande = commandeRepository.findById(idCommande)
                .orElseThrow(() -> new RuntimeException("Commande introuvable avec l'id : " + idCommande));

        if (commande.getStatutCommande() != StatutCommande.LIVREE) {
            throw new RuntimeException("Impossible de générer une facture : la commande n'est pas LIVREE");
        }

        boolean existeDeja = factureRecuRepository.findAll()
                .stream().anyMatch(f -> f.getCommande().getIdCommande().equals(idCommande));
        if (existeDeja) {
            throw new RuntimeException("Une facture existe déjà pour cette commande");
        }

        FactureRecu facture = new FactureRecu();
        facture.setCommande(commande);
        facture.setReferenceFacture(genererReferenceFacture());
        facture.setDateFacturation(LocalDateTime.now());
        facture.setModePaiement(modePaiement);

        return factureRecuRepository.save(facture);
    }

    public String genererReferenceFacture() {
        int annee = Year.now().getValue();
        int compteur = 1;

        FactureRecu derniere = factureRecuRepository.findTopByOrderByIdFactureDesc().orElse(null);
        if (derniere != null && derniere.getReferenceFacture() != null) {
            String ref = derniere.getReferenceFacture();
            String[] parties = ref.split("-");
            if (parties.length == 3 && parties[1].equals(String.valueOf(annee))) {
                compteur = Integer.parseInt(parties[2]) + 1;
            }
        }

        return String.format("FACT-%d-%05d", annee, compteur);
    }

    public FactureRecu getFacture(Long id) {
        return factureRecuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facture introuvable avec l'id : " + id));
    }

    public List<FactureRecu> getAllFactures() {
        return factureRecuRepository.findAll();
    }

    public FactureRecu getFactureByCommande(Long idCommande) {
        return factureRecuRepository.findAll().stream()
                .filter(f -> f.getCommande().getIdCommande().equals(idCommande))
                .findFirst()
                .orElse(null);
    }
}
