package com.mkalymlam.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mkalymlam.repository.StatistiqueRepository;

@Service
@Transactional(readOnly = true)
public class StatistiqueService {

    private final StatistiqueRepository statistiqueRepository;

    public StatistiqueService(StatistiqueRepository statistiqueRepository) {
        this.statistiqueRepository = statistiqueRepository;
    }

    public Double getChiffreAffaireGlobal() {
        return statistiqueRepository.getChiffreAffaireGlobal();
    }

    public Double getBeneficeTotal() {
        Double depenseTotale = statistiqueRepository.getBeneficeTotal();
        Double chiffreAffaireGlobal = getChiffreAffaireGlobal();
        return chiffreAffaireGlobal - depenseTotale;
    }

    public Double getBeneficeByIdItineraire(Long idItineraire) {
        return statistiqueRepository.getBeneficeByIdItineraire(idItineraire);
    }

    public List<Map<String, Object>> getDonneesGraphique() {
        return toMapList(statistiqueRepository.getDonneesGraphique(),
                new String[] { "periode", "chiffreAffaire", "benefice" });
    }

    public List<Map<String, Object>> getChiffreAffaireParJour() {
        return toMapList(statistiqueRepository.getChiffreAffaireParJour(),
                new String[] { "periode", "chiffreAffaire" });
    }

    public List<Map<String, Object>> getChiffreAffaireParSemaine() {
        return toMapList(statistiqueRepository.getChiffreAffaireParSemaine(),
                new String[] { "periode", "chiffreAffaire" });
    }

    public List<Map<String, Object>> getChiffreAffaireParMois() {
        return toMapList(statistiqueRepository.getChiffreAffaireParMois(),
                new String[] { "periode", "chiffreAffaire" });
    }

    public List<Map<String, Object>> getBeneficeParJour() {
        return toMapList(statistiqueRepository.getBeneficeParJour(), new String[] { "periode", "benefice" });
    }

    public List<Map<String, Object>> getBeneficeParSemaine() {
        return toMapList(statistiqueRepository.getBeneficeParSemaine(), new String[] { "periode", "benefice" });
    }

    public List<Map<String, Object>> getBeneficeParMois() {
        return toMapList(statistiqueRepository.getBeneficeParMois(), new String[] { "periode", "benefice" });
    }

    public List<Map<String, Object>> getBeneficeParItineraireParJour(Long idItineraire) {
        return toMapList(statistiqueRepository.getBeneficeParItineraireParJour(idItineraire),
                new String[] { "periode", "benefice" });
    }

    public List<Map<String, Object>> getBeneficeParItineraireParSemaine(Long idItineraire) {
        return toMapList(statistiqueRepository.getBeneficeParItineraireParSemaine(idItineraire),
                new String[] { "periode", "benefice" });
    }

    public List<Map<String, Object>> getBeneficeParItineraireParMois(Long idItineraire) {
        return toMapList(statistiqueRepository.getBeneficeParItineraireParMois(idItineraire),
                new String[] { "periode", "benefice" });
    }

    private List<Map<String, Object>> toMapList(List<Object[]> rows, String[] columnNames) {
        List<Map<String, Object>> results = new ArrayList<>();
        for (Object[] row : rows) {
            Map<String, Object> mapped = new HashMap<>();
            for (int i = 0; i < columnNames.length && i < row.length; i++) {
                mapped.put(columnNames[i], row[i]);
            }
            results.add(mapped);
        }
        return results;
    }
}
