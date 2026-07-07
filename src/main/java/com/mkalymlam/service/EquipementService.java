package com.mkalymlam.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mkalymlam.entity.Equipement;
import com.mkalymlam.entity.MouvementEquipement;
import com.mkalymlam.repository.EquipementRepository;
import com.mkalymlam.repository.MouvementEquipementRepository;

@Service
public class EquipementService {

    private final EquipementRepository equipementRepository;
    private final MouvementEquipementRepository mouvementRepository;

    public EquipementService(
            EquipementRepository equipementRepository,
            MouvementEquipementRepository mouvementRepository) {

        this.equipementRepository = equipementRepository;
        this.mouvementRepository = mouvementRepository;
    }


    public Equipement save(Equipement equipement) {
        return equipementRepository.save(equipement);
    }

    public Equipement update(Equipement equipement) {
        return equipementRepository.save(equipement);
    }

    public void delete(Long idEquipement) {
        equipementRepository.deleteById(idEquipement);
    }

    public Equipement find(Long idEquipement) {
        return equipementRepository.findById(idEquipement)
                .orElse(null);
    }

    public List<Equipement> findAll() {
        return equipementRepository.findAll();
    }

    public Double getQuantiteStock(Long idEquipement) {
        List<MouvementEquipement> mouvements = getMouvementsEquipement(idEquipement);
        Double totalEntrees =  calculerTotalEntrees(mouvements);
        Double totalSorties =  calculerTotalSorties(mouvements);
        return totalEntrees - totalSorties;
    }


    private List<MouvementEquipement> getMouvementsEquipement( Long idEquipement) {
        return mouvementRepository.findByEquipement_IdEquipementOrderByDateMouvementAsc( idEquipement);
    }

    private Double calculerTotalEntrees( List<MouvementEquipement> mouvements) {
        return mouvements.stream()
                .filter(m -> m.getTypeMouvement() .getIdTypeMouvement() .equals(1L))
                .mapToDouble(MouvementEquipement::getQuantite)
                .sum();
    }

    private Double calculerTotalSorties(  List<MouvementEquipement> mouvements) {
        return mouvements.stream()
            .filter(m -> m.getTypeMouvement() .getIdTypeMouvement() .equals(2L))
            .mapToDouble(MouvementEquipement::getQuantite)
            .sum();
    }

    public List<Equipement> getEquipementsEnAlerte() {
        return equipementRepository.findAll()
                .stream()
                .filter(e -> getQuantiteStock(  e.getIdEquipement()) <= e.getQuantiteMin())
                .toList();
    }

    //CUMP 
    private Double calculerValeurEntrees( List<MouvementEquipement> mouvements) {
        return mouvements.stream()
                .filter(m -> m.getTypeMouvement().getIdTypeMouvement() .equals(1L))
                .mapToDouble(m -> m.getQuantite() * m.getEquipement().getPrixUnitaire())
                .sum();
    }

    private Double calculerValeurSorties( List<MouvementEquipement> mouvements) {
        return mouvements.stream()
                .filter(m ->  m.getTypeMouvement() .getIdTypeMouvement()  .equals(2L))
                .mapToDouble(m -> m.getQuantite() * m.getEquipement().getPrixUnitaire())
                .sum();
    }

    private Double calculerNumerateurCump(  List<MouvementEquipement> mouvements) {
        return calculerValeurEntrees(mouvements) - calculerValeurSorties(mouvements);
    }

    private Double calculerDenominateurCump(  List<MouvementEquipement> mouvements) {
        return calculerTotalEntrees(mouvements)- calculerTotalSorties(mouvements);
    }


    public Double calculerCump( Long idEquipement) {
        List<MouvementEquipement> mouvements = getMouvementsEquipement(idEquipement);
        Double numerateur = calculerNumerateurCump(mouvements);
        Double denominateur = calculerDenominateurCump(mouvements);
        if (denominateur == 0) {
            return 0.0;
        }
        return numerateur / denominateur;
    }
}