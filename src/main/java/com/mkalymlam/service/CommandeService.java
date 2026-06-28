package com.mkalymlam.service;

import org.springframework.stereotype.Service;

import com.mkalymlam.entity.Commande;
import com.mkalymlam.entity.StatutCommande;
import com.mkalymlam.repository.CommandeRepository;

@Service
public class CommandeService {

    private final CommandeRepository commandeRepository;

    public CommandeService(CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }

    public Commande changerStatut(Long idCommande, StatutCommande nouveauStatut) {
        Commande commande = commandeRepository.findById(idCommande)
                .orElseThrow(() -> new RuntimeException("Commande introuvable avec l'id : " + idCommande));
        commande.setStatutCommande(nouveauStatut);
        return commandeRepository.save(commande);
    }
}
