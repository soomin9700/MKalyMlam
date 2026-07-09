package com.mkalymlam.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mkalymlam.entity.Depense;
import com.mkalymlam.entity.SessionTruck;
import com.mkalymlam.entity.TypeDepense;
import com.mkalymlam.entity.StatutValidationAdmin;
import com.mkalymlam.repository.DepenseRepository;
import com.mkalymlam.repository.SessionTruckRepository;
import com.mkalymlam.repository.StatutValidationAdminRepository;
import com.mkalymlam.repository.TypeDepenseRepository;

@Service
public class DepenseService {

    private final DepenseRepository depenseRepository;
    private final SessionTruckRepository sessionTruckRepository;
    private final TypeDepenseRepository typeDepenseRepository;
    private final StatutValidationAdminRepository statutValidationAdminRepository;

    public DepenseService(DepenseRepository depenseRepository,
                          SessionTruckRepository sessionTruckRepository,
                          TypeDepenseRepository typeDepenseRepository,
                          StatutValidationAdminRepository statutValidationAdminRepository) {
        this.depenseRepository = depenseRepository;
        this.sessionTruckRepository = sessionTruckRepository;
        this.typeDepenseRepository = typeDepenseRepository;
        this.statutValidationAdminRepository = statutValidationAdminRepository;
    }

    public List<Depense> findBySession(Long idSession) {
        return depenseRepository.findBySession_IdOrderByIdDesc(idSession);
    }

    public List<Depense> getAllDepenses() {
        return depenseRepository.findAllByOrderByDateDepenseDesc();
    }

    public List<TypeDepense> getAllTypes() {
        return typeDepenseRepository.findAll();
    }

    public List<StatutValidationAdmin> getAllStatuts() {
        return statutValidationAdminRepository.findAll();
    }

    @Transactional
    public Depense ajouter(Long idSession, Long idTypeDepense, Double montant, String raison) {
        SessionTruck session = sessionTruckRepository.findById(idSession)
                .orElseThrow(() -> new IllegalArgumentException("Session introuvable"));

        TypeDepense type = typeDepenseRepository.findById(idTypeDepense)
                .orElseThrow(() -> new IllegalArgumentException("Type de depense introuvable"));

        StatutValidationAdmin statut = statutValidationAdminRepository.findAll().stream()
                .filter(s -> "EN_ATTENTE".equals(s.getLibelle()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Statut EN_ATTENTE introuvable"));

        Depense depense = new Depense();
        depense.setSession(session);
        depense.setTypeDepense(type);
        depense.setMontantDepense(montant);
        depense.setRaisonDetaillee(raison);
        depense.setDateDepense(LocalDate.now());
        depense.setStatutValidationAdmin(statut);

        return depenseRepository.save(depense);
    }

    public Depense getById(Long id) {
        return depenseRepository.findById(id).orElse(null);
    }

    @Transactional
    public Depense modifier(Long id, Long idTypeDepense, Double montant, String raison) {
        Depense depense = depenseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Depense introuvable"));

        if (!"EN_ATTENTE".equals(depense.getStatutValidationAdmin().getLibelle())) {
            throw new IllegalArgumentException("Seules les depenses en attente peuvent etre modifiees");
        }

        TypeDepense type = typeDepenseRepository.findById(idTypeDepense)
                .orElseThrow(() -> new IllegalArgumentException("Type de depense introuvable"));

        depense.setTypeDepense(type);
        depense.setMontantDepense(montant);
        depense.setRaisonDetaillee(raison);

        return depenseRepository.save(depense);
    }

    @Transactional
    public void supprimer(Long id) {
        Depense depense = depenseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Depense introuvable"));

        if (!"EN_ATTENTE".equals(depense.getStatutValidationAdmin().getLibelle())) {
            throw new IllegalArgumentException("Seules les depenses en attente peuvent etre supprimees");
        }

        depenseRepository.deleteById(id);
    }

    public List<Depense> getEnAttente() {
        return depenseRepository.findByStatutValidationAdmin_LibelleOrderByIdDesc("EN_ATTENTE");
    }

    @Transactional
    public Depense valider(Long id) {
        Depense depense = depenseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Depense introuvable"));
        StatutValidationAdmin statut = statutValidationAdminRepository.findByLibelle("VALIDE_ADMIN");
        depense.setStatutValidationAdmin(statut);
        return depenseRepository.save(depense);
    }

    @Transactional
    public Depense refuser(Long id, String commentaire) {
        Depense depense = depenseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Depense introuvable"));
        StatutValidationAdmin statut = statutValidationAdminRepository.findByLibelle("REFUSE_ADMIN");
        depense.setStatutValidationAdmin(statut);
        depense.setCommentaireAdminRetour(commentaire);
        return depenseRepository.save(depense);
    }
}
