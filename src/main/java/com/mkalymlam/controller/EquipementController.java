package com.mkalymlam.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.mkalymlam.entity.Equipement;
import com.mkalymlam.service.EquipementService;

@RestController
@RequestMapping("/equipement")
public class EquipementController {

    private final EquipementService service;

    public EquipementController(
            EquipementService service) {

        this.service = service;
    }

    @PostMapping("/save")
    public Equipement save( @RequestBody Equipement equipement) {
        return service.save(equipement);
    }

    @PutMapping("/update")
    public Equipement update(
            @RequestBody Equipement equipement) {

        return service.update(equipement);
    }

    @DeleteMapping("/delete/{id}")
    public void delete( @PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/find/{id}")
    public Equipement find(
            @PathVariable Long id) {

        return service.find(id);
    }

    @GetMapping("/findAll")
    public List<Equipement> findAll() {
        return service.findAll();
    }

    @GetMapping("/alertes")
    public List<Equipement> alertes() {
        return service.getEquipementsEnAlerte();
    }
}