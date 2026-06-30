package com.mkalymlam.controller;

import com.mkalymlam.entity.RoleEntity;
import com.mkalymlam.entity.Utilisateur;
import com.mkalymlam.repository.RoleRepository;
import com.mkalymlam.repository.UtilisateurRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final UtilisateurRepository utilisateurRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UtilisateurRepository utilisateurRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

//     @GetMapping("/login")
// @ResponseBody
// public String loginTest() {
//     return "Login page";
// }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute Utilisateur utilisateur,
                                      RedirectAttributes redirectAttributes) {
        // Vérifier si l'email existe déjà
        if (utilisateurRepository.findByEmail(utilisateur.getEmail()).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Cet email est déjà utilisé.");
            return "redirect:/register";
        }

        // Assigner un rôle par défaut (ex: VENDEUSE)
        RoleEntity roleDefault = roleRepository.findFirstByLibelle("VENDEUSE")
                .orElseThrow(() -> new RuntimeException("Rôle VENDEUSE introuvable"));
        utilisateur.setRole(roleDefault);

        // Encoder le mot de passe
        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));

        // Actif par défaut
        utilisateur.setStatutActif(true);

        utilisateurRepository.save(utilisateur);

        redirectAttributes.addFlashAttribute("success", "Inscription réussie, vous pouvez vous connecter.");
        return "redirect:/login";
    }
}