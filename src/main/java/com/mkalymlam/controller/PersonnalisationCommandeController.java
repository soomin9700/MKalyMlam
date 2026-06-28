package com.mkalymlam.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.mkalymlam.entity.PersonnalisationCommande;
import com.mkalymlam.service.PersonnalisationCommandeService;

@RestController
@RequestMapping("/personnalisation")
public class PersonnalisationCommandeController {

    private final PersonnalisationCommandeService service;

    public PersonnalisationCommandeController(PersonnalisationCommandeService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public PersonnalisationCommande save(@RequestBody PersonnalisationCommande p) {
        return service.save(p);
    }

    @PostMapping("/update")
    public PersonnalisationCommande update(@RequestBody PersonnalisationCommande p) {
        return service.save(p);
    }

    @PostMapping("/delete")
    public void delete(@RequestParam("id") Long id) {
        service.delete(id);
    }

    @GetMapping("/find")
    public PersonnalisationCommande find(@RequestParam("id") Long id) {
        return service.findById(id);
    }

    // CORRECTION : On blinde le nom du paramètre pour correspondre au fetch de ton JSP
    @GetMapping("/findAllByLigne")
    public List<PersonnalisationCommande> findAllByLigne(@RequestParam("id_ligne") Long idLigne) {
        return service.findAllByLigne(idLigne);
    }
}