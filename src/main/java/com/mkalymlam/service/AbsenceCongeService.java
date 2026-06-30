package com.mkalymlam.service;

import com.mkalymlam.entity.*;
import com.mkalymlam.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class AbsenceCongeService {

    private final AbsenceCongeRepository absenceRepo;
    private final UtilisateurRepository userRepo;
    private final HistoriqueSalaireRepository histRepo;

    public AbsenceCongeService(
            AbsenceCongeRepository absenceRepo,
            UtilisateurRepository userRepo,
            HistoriqueSalaireRepository histRepo
    ) {
        this.absenceRepo = absenceRepo;
        this.userRepo = userRepo;
        this.histRepo = histRepo;
    }

    // DEMANDE
    public AbsenceConge demander(AbsenceConge a) {
        a.setStatutValidation(StatutValidation.EN_ATTENTE);
        return absenceRepo.save(a);
    }

    // VALIDER
    public AbsenceConge valider(Long id) {

        AbsenceConge a = absenceRepo.findById(id).orElseThrow();
        a.setStatutValidation(StatutValidation.VALIDE);
        absenceRepo.save(a);

        recalculSalaire(a.getUtilisateur());

        return a;
    }

    // REFUSER
    public AbsenceConge refuser(Long id) {
        AbsenceConge a = absenceRepo.findById(id).orElseThrow();
        a.setStatutValidation(StatutValidation.REFUSE);
        return absenceRepo.save(a);
    }

    // LISTE
    public List<AbsenceConge> findAll() {
        return absenceRepo.findAll();
    }

    // =========================
    // SALAIRE PRO
    // =========================
    public void recalculSalaire(Utilisateur user) {

        List<AbsenceConge> absences = absenceRepo.findAll()
                .stream()
                .filter(a -> a.getUtilisateur().getIdUtilisateur().equals(user.getIdUtilisateur()))
                .filter(a -> a.getStatutValidation() == StatutValidation.VALIDE)
                .toList();

        double base = user.getSalaireBaseFixe();
        double journalier = base / 30;

        double deduction = 0;

        for (AbsenceConge a : absences) {

            long jours = ChronoUnit.DAYS.between(a.getDateDebut(), a.getDateFin()) + 1;

            switch (a.getType()) {

                case ABSENCE_INJUSTIFIEE -> deduction += jours * journalier;

                case ABSENCE_MALADIE -> deduction += jours * journalier * 0.5;

                case CONGE_PAYE, CONGE_EXCEPTIONNEL -> {
                    // rien
                }
            }
        }

        double salaireFinal = base - deduction;

        // =========================
        // HISTORIQUE (IMPORTANT)
        // =========================
        HistoriqueSalaire hist = new HistoriqueSalaire();
        hist.setUtilisateur(user);
        hist.setSalaireBase(salaireFinal);
        hist.setDateDebut(LocalDate.now());
        histRepo.save(hist);

        // on garde le user intact (salaireBaseFixe NON modifié)
        userRepo.save(user);
    }
}