package com.mkalymlam.controller;

import com.mkalymlam.entity.RoleEntity;
import com.mkalymlam.entity.Utilisateur;
import com.mkalymlam.repository.RoleRepository;
import com.mkalymlam.service.UtilisateurService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/utilisateurs")
public class UtilisateurController {

    private final UtilisateurService service;
    private final RoleRepository roleRepo;

    public UtilisateurController(UtilisateurService service, RoleRepository roleRepo) {
        this.service = service;
        this.roleRepo = roleRepo;
    }

    @GetMapping
    public List<Utilisateur> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Utilisateur getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/save")
    public Utilisateur save(@RequestBody Map<String, Object> payload) {
        // Gestion du rôle
        Object roleField = payload.get("role");
        if (roleField == null) {
            throw new RuntimeException("Le champ 'role' est obligatoire");
        }
        RoleEntity roleEntity = null;
        if (roleField instanceof String libelle) {
            roleEntity = roleRepo.findFirstByLibelle(libelle)
                    .orElseThrow(() -> new RuntimeException("Rôle inexistant : " + libelle));
        } else if (roleField instanceof Map<?, ?> roleMap) {
            if (roleMap.containsKey("idRole")) {
                Long idRole = Long.valueOf(roleMap.get("idRole").toString());
                roleEntity = roleRepo.findById(idRole)
                        .orElseThrow(() -> new RuntimeException("Rôle inexistant avec l'ID : " + idRole));
            } else if (roleMap.containsKey("libelle")) {
                String libelle = roleMap.get("libelle").toString();
                roleEntity = roleRepo.findFirstByLibelle(libelle)
                        .orElseThrow(() -> new RuntimeException("Rôle inexistant : " + libelle));
            } else {
                throw new RuntimeException("Format du rôle non reconnu. Utilisez 'idRole' ou 'libelle'");
            }
        } else {
            throw new RuntimeException("Format du rôle non valide. Doit être une chaîne ou un objet {idRole} ou {libelle}");
        }

        Utilisateur u = new Utilisateur();
        u.setNom((String) payload.get("nom"));
        u.setPrenom((String) payload.get("prenom"));
        u.setEmail((String) payload.get("email"));
        u.setMotDePasse((String) payload.get("motDePasse"));
        if (payload.get("salaireBaseFixe") != null) {
            u.setSalaireBaseFixe(Double.valueOf(payload.get("salaireBaseFixe").toString()));
        }
        u.setStatutActif((Boolean) payload.getOrDefault("statutActif", true));
        u.setRole(roleEntity);

        return service.save(u);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }

    @PostMapping("/desactiver")
    public void desactiver(@RequestParam Long id) {
        service.desactiverUtilisateur(id);
    }

    @PostMapping("/activer")
    public void activer(@RequestParam Long id) {
        Utilisateur u = service.getById(id);
        if (u != null) {
            u.setStatutActif(true);
            service.save(u);
        }
    }

    @GetMapping("/export/csv")
    public String exportCsv() {
        StringBuilder sb = new StringBuilder();
        sb.append("id,nom,prenom,email,role,statut\n");
        service.findAll().forEach(u -> {
            sb.append(u.getIdUtilisateur()).append(",");
            sb.append(u.getNom()).append(",");
            sb.append(u.getPrenom()).append(",");
            sb.append(u.getEmail()).append(",");
            sb.append(u.getRole().getLibelle()).append(",");
            sb.append(u.getStatutActif()).append("\n");
        });
        return sb.toString();
    }
}