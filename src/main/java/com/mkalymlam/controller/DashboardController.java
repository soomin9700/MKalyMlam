package com.mkalymlam.controller;

import com.mkalymlam.entity.Utilisateur;
import com.mkalymlam.repository.UtilisateurRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final UtilisateurRepository utilisateurRepository;

    public DashboardController(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @GetMapping({ "/", "/dashboard", "/dashboard-statistique", "/statistique", "/statistique/dashboard",
            "/statistique/dashboard-statistique" })
    public String dashboardStatistique(Model model) {
        // Récupération de l'utilisateur connecté
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); // email utilisé comme username
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email).orElse(null);

        String nomComplet = "";
        if (utilisateur != null) {
            nomComplet = utilisateur.getPrenom() + " " + utilisateur.getNom();
        } else {
            nomComplet = email; // fallback
        }

        model.addAttribute("titre", "Dashboard statistique");
        model.addAttribute("nomUtilisateur", nomComplet);
        return "dashboard/dashboard";
    }
}