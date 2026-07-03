package com.mkalymlam.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.mkalymlam.entity.MouvementEquipement;
import com.mkalymlam.service.MouvementEquipementService;

@RestController
@RequestMapping("/mouvements-equipement")
public class MouvementEquipementController {

    private final MouvementEquipementService service;

    public MouvementEquipementController( MouvementEquipementService service) {
        this.service = service;
    }

    @GetMapping("/findall")
    public List<MouvementEquipement> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public MouvementEquipement getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/save")
    public MouvementEquipement save( @RequestBody MouvementEquipement entity) {
        return service.save(entity);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}