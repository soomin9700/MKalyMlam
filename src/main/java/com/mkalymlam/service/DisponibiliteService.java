package com.mkalymlam.service;

import com.mkalymlam.entity.DisponibiliteProduit;
import com.mkalymlam.entity.Produit;
import com.mkalymlam.repository.DisponibiliteProduitRepository;
import com.mkalymlam.repository.ProduitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class DisponibiliteService {

    private final DisponibiliteProduitRepository disponibiliteRepository;
    private final ProduitRepository produitRepository;

    public DisponibiliteService(DisponibiliteProduitRepository disponibiliteRepository,
                                ProduitRepository produitRepository) {
        this.disponibiliteRepository = disponibiliteRepository;
        this.produitRepository = produitRepository;
    }

    @Transactional
    public void activateProduct(Long idProduit) {
        Produit produit = produitRepository.findById(idProduit)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé avec l'ID: " + idProduit));
        
        DisponibiliteProduit dispo = new DisponibiliteProduit();
        dispo.setIdProduit(idProduit);
        dispo.setEstDisponible(true);
        dispo.setDateModification(LocalDate.now());
        
        disponibiliteRepository.save(dispo);
    }

    @Transactional
    public void deactivateProduct(Long idProduit) {
        Produit produit = produitRepository.findById(idProduit)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé avec l'ID: " + idProduit));
        
        DisponibiliteProduit dispo = new DisponibiliteProduit();
        dispo.setIdProduit(idProduit);
        dispo.setEstDisponible(false);
        dispo.setDateModification(LocalDate.now());
        
        disponibiliteRepository.save(dispo);
    }

    // @Transactional
    // public void toggleDisponibilite(Long idProduit) {
    //     Boolean currentStatus = disponibiliteRepository
    //             .findLastDisponibilite(idProduit)
    //             .map(DisponibiliteProduit::getEstDisponible)
    //             .orElse(true);
        
    //     DisponibiliteProduit dispo = new DisponibiliteProduit();
    //     dispo.setIdProduit(idProduit);
    //     dispo.setEstDisponible(!currentStatus);
    //     dispo.setDateModification(LocalDate.now());
        
    //     disponibiliteRepository.save(dispo);
    // }
}