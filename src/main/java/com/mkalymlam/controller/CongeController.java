package com.mkalymlam.controller;

import com.mkalymlam.entity.Conge;
import com.mkalymlam.service.CongeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conges")
public class CongeController {

    private final CongeService service;

    public CongeController(CongeService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public Conge save(@RequestBody Conge conge) {
        return service.save(conge);
    }

    @GetMapping
    public List<Conge> findAll() {
        return service.findAll();
    }

    @GetMapping("/employe/{id}")
    public List<Conge> findByEmploye(@PathVariable Long id) {
        return service.findByEmploye(id);
    }

    @PutMapping("/valider/{id}")
    public Conge valider(@PathVariable Long id) {
        return service.valider(id);
    }

    @PutMapping("/refuser/{id}")
    public Conge refuser(@PathVariable Long id) {
        return service.refuser(id);
    }
}