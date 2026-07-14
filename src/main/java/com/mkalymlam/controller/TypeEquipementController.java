package com.mkalymlam.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.mkalymlam.entity.TypeEquipement;
import com.mkalymlam.service.TypeEquipementService;

@RestController
@RequestMapping("/type-equipements")
public class TypeEquipementController {

    private final TypeEquipementService service;

    public TypeEquipementController(TypeEquipementService service) {
        this.service = service;
    }

    @GetMapping
    public List<TypeEquipement> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public TypeEquipement getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public TypeEquipement save(@RequestBody TypeEquipement entity) {
        return service.save(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}