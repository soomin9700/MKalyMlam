package com.mkalymlam.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.mkalymlam.entity.TypeMouvement;
import com.mkalymlam.service.TypeMouvementService;

@RestController
@RequestMapping("/types-mouvement")
public class TypeMouvementController {

    private final TypeMouvementService service;

    public TypeMouvementController(TypeMouvementService service) {
        this.service = service;
    }

    @GetMapping
    public List<TypeMouvement> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public TypeMouvement getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public TypeMouvement save(@RequestBody TypeMouvement entity) {
        return service.save(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}