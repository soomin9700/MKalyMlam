package com.mkalymlam.service;

import com.mkalymlam.dto.TopProduitDTO;
import com.mkalymlam.dto.VentePeriodDTO;
import com.mkalymlam.dto.VenteStatsDTO;
import com.mkalymlam.repository.CommandeRepository;
import com.mkalymlam.repository.LigneCommandeRepository;
import com.mkalymlam.repository.ProduitRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class PerformanceService {

    private final CommandeRepository commandeRepository;
    private final LigneCommandeRepository ligneCommandeRepository;
    private final ProduitRepository produitRepository;

    public PerformanceService(CommandeRepository commandeRepository,
                              LigneCommandeRepository ligneCommandeRepository,
                              ProduitRepository produitRepository) {
        this.commandeRepository = commandeRepository;
        this.ligneCommandeRepository = ligneCommandeRepository;
        this.produitRepository = produitRepository;
    }

    
    //  statistiques des ventes gal
    public VenteStatsDTO getVenteStats() {
        Long nombreVentes = commandeRepository.countCommandes();
        Double chiffreAffaires = commandeRepository.sumMontantTotal();
        Double moyennePanier = commandeRepository.averageMontantTotal();
        Long nombreProduitsVendus = ligneCommandeRepository.sumTotalQuantite();

        return new VenteStatsDTO(nombreVentes, chiffreAffaires, moyennePanier, nombreProduitsVendus);
    }

    public VenteStatsDTO getVenteStatsBetween(String dateDebut, String dateFin) {
        LocalDateTime debut = parseDateDebut(dateDebut);
        LocalDateTime fin = parseDateFin(dateFin);

        Long nombreVentes = commandeRepository.countCommandesBetween(debut, fin);
        Double chiffreAffaires = commandeRepository.sumMontantTotalBetween(debut, fin);
        Double moyennePanier = commandeRepository.averageMontantTotalBetween(debut, fin);
        Long nombreProduitsVendus = ligneCommandeRepository.sumTotalQuantiteBetween(debut, fin);

        return new VenteStatsDTO(nombreVentes, chiffreAffaires, moyennePanier, nombreProduitsVendus);
    }

    
    //  popularite produit
    public List<TopProduitDTO> getTopProduits() {
        List<Object[]> results = ligneCommandeRepository.findTop5Produits();
        return convertToTopProduitDTO(results);
    }

    public List<TopProduitDTO> getTopProduitsBetween(String dateDebut, String dateFin) {
        LocalDateTime debut = parseDateDebut(dateDebut);
        LocalDateTime fin = parseDateFin(dateFin);

        List<Object[]> results = ligneCommandeRepository.findTop5ProduitsBetween(debut, fin);
        return convertToTopProduitDTO(results);
    }

    private List<TopProduitDTO> convertToTopProduitDTO(List<Object[]> results) {
        List<TopProduitDTO> dtos = new ArrayList<>();
        for (Object[] row : results) {
            Long idProduit = ((Number) row[0]).longValue();
            String nomProduit = (String) row[1];
            Long quantiteVendue = ((Number) row[2]).longValue();
            Double chiffreAffaires = ((Number) row[3]).doubleValue();
            Double prixMoyen = ((Number) row[4]).doubleValue();  

            dtos.add(new TopProduitDTO(idProduit, nomProduit, quantiteVendue, chiffreAffaires, prixMoyen));
        }
        return dtos;
    }

    
    //  popularite d un produit 
    public Long getPopulariteProduit(Long idProduit) {
        return ligneCommandeRepository.getQuantiteVendueByProduit(idProduit);
    }

    public Long getPopulariteProduitBetween(Long idProduit, String dateDebut, String dateFin) {
        LocalDateTime debut = parseDateDebut(dateDebut);
        LocalDateTime fin = parseDateFin(dateFin);
        return ligneCommandeRepository.getQuantiteVendueByProduitBetween(idProduit, debut, fin);
    }

    
    //  qtt vendue par produit
    public List<Object[]> getQuantiteVendueParProduit() {
        return ligneCommandeRepository.sumQuantiteByProduit();
    }

    public List<Object[]> getQuantiteVendueParProduitBetween(String dateDebut, String dateFin) {
        LocalDateTime debut = parseDateDebut(dateDebut);
        LocalDateTime fin = parseDateFin(dateFin);
        return ligneCommandeRepository.sumQuantiteByProduitBetween(debut, fin);
    }

    
    //  CA par produit
    public List<Object[]> getCAParProduit() {
        return ligneCommandeRepository.sumCAByProduit();
    }

    public List<Object[]> getCAParProduitBetween(String dateDebut, String dateFin) {
        LocalDateTime debut = parseDateDebut(dateDebut);
        LocalDateTime fin = parseDateFin(dateFin);
        return ligneCommandeRepository.sumCAByProduitBetween(debut, fin);
    }

    
    //  ventes par mois (graphiques)
    public List<VentePeriodDTO> getVentesParMois() {
        List<Object[]> results = commandeRepository.countByMonth();
        List<VentePeriodDTO> dtos = new ArrayList<>();
        for (Object[] row : results) {
            String mois = (String) row[0];
            Long nombreVentes = ((Number) row[1]).longValue();
            dtos.add(new VentePeriodDTO(mois, nombreVentes, null, null));
        }
        return dtos;
    }

    public List<VentePeriodDTO> getCAParMois() {
        List<Object[]> results = commandeRepository.sumByMonth();
        List<VentePeriodDTO> dtos = new ArrayList<>();
        for (Object[] row : results) {
            String mois = (String) row[0];
            Double ca = ((Number) row[1]).doubleValue();
            dtos.add(new VentePeriodDTO(mois, null, ca, null));
        }
        return dtos;
    }

    
    //  statistiques des 7 derniers jours
    public List<Object[]> getLast7DaysStats() {
        LocalDateTime dateDebut = LocalDateTime.now().minusDays(7);
        return commandeRepository.findLast7DaysStats(dateDebut);
    }

    
    // methodes utilitaires
    private LocalDateTime parseDateDebut(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return LocalDateTime.of(2000, 1, 1, 0, 0);
        }
        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
        return date.atStartOfDay();
    }

    private LocalDateTime parseDateFin(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return LocalDateTime.now();
        }
        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
        return date.atTime(LocalTime.MAX);
    }
}