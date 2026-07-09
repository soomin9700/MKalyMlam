package com.mkalymlam.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mkalymlam.entity.Depense;
import com.mkalymlam.repository.SessionTruckRepository;
import com.mkalymlam.service.DepenseService;

@Controller
@RequestMapping("/depenses")
public class DepenseGlobaleController {

    private final DepenseService depenseService;
    private final SessionTruckRepository sessionTruckRepository;

    public DepenseGlobaleController(DepenseService depenseService,
                                    SessionTruckRepository sessionTruckRepository) {
        this.depenseService = depenseService;
        this.sessionTruckRepository = sessionTruckRepository;
    }

    @GetMapping
    public String liste(@RequestParam(required = false) Long idTypeDepense,
                        @RequestParam(required = false) Long idStatut,
                        @RequestParam(required = false) Long idSession,
                        @RequestParam(required = false) LocalDate dateDebut,
                        @RequestParam(required = false) LocalDate dateFin,
                        Model model) {
        List<Depense> depenses = depenseService.getAllDepenses();

        if (idTypeDepense != null) {
            depenses = depenses.stream()
                    .filter(d -> d.getTypeDepense().getId().equals(idTypeDepense))
                    .toList();
        }
        if (idStatut != null) {
            depenses = depenses.stream()
                    .filter(d -> d.getStatutValidationAdmin().getId().equals(idStatut))
                    .toList();
        }
        if (idSession != null) {
            depenses = depenses.stream()
                    .filter(d -> d.getSession().getId().equals(idSession))
                    .toList();
        }
        if (dateDebut != null) {
            depenses = depenses.stream()
                    .filter(d -> !d.getDateDepense().isBefore(dateDebut))
                    .toList();
        }
        if (dateFin != null) {
            depenses = depenses.stream()
                    .filter(d -> !d.getDateDepense().isAfter(dateFin))
                    .toList();
        }

        double totalFiltre = depenses.stream()
                .mapToDouble(Depense::getMontantDepense).sum();

        model.addAttribute("depenses", depenses);
        model.addAttribute("types", depenseService.getAllTypes());
        model.addAttribute("statuts", depenseService.getAllStatuts());
        model.addAttribute("sessions", sessionTruckRepository.findAll());
        model.addAttribute("selectedType", idTypeDepense);
        model.addAttribute("selectedStatut", idStatut);
        model.addAttribute("selectedSession", idSession);
        model.addAttribute("dateDebut", dateDebut);
        model.addAttribute("dateFin", dateFin);
        model.addAttribute("totalFiltre", totalFiltre);

        return "depense/all";
    }
}
