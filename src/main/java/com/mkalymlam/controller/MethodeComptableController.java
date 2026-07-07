package com.mkalymlam.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.mkalymlam.entity.MethodeComptable;
import com.mkalymlam.entity.TypeEquipement;
import com.mkalymlam.service.MethodeComptableService;

@RestController
@RequestMapping("/methodes-comptables")
public class MethodeComptableController {

    private final MethodeComptableService service;

    public MethodeComptableController(MethodeComptableService service) {
        this.service = service;
    }

    @GetMapping
    public List<MethodeComptable> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public MethodeComptable getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public MethodeComptable save(@RequestBody MethodeComptable entity) {
        return service.save(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}