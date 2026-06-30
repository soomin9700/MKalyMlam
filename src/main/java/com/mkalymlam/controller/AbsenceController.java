package com.mkalymlam.controller;

import com.mkalymlam.entity.Absence;
import com.mkalymlam.service.AbsenceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/absences")
public class AbsenceController {

    private final AbsenceService service;

    public AbsenceController(AbsenceService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public Absence save(@RequestBody Absence absence) {
        return service.save(absence);
    }

    @GetMapping
    public List<Absence> findAll() {
        return service.findAll();
    }

    @GetMapping("/employe/{id}")
    public List<Absence> findByEmploye(@PathVariable Long id) {
        return service.findByEmploye(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}