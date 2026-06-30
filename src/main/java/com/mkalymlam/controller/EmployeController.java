package com.mkalymlam.controller;

import com.mkalymlam.entity.Employe;
import com.mkalymlam.service.EmployeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employes")
public class EmployeController {

    private final EmployeService service;

    public EmployeController(EmployeService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public Employe save(@RequestBody Employe employe) {
        return service.save(employe);
    }

    @GetMapping
    public List<Employe> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Employe getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/update/{id}")
    public Employe update(@PathVariable Long id, @RequestBody Employe employe) {
        return service.update(id, employe);
    }
}