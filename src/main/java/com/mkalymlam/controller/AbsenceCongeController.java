package com.mkalymlam.controller;

import com.mkalymlam.entity.AbsenceConge;
import com.mkalymlam.service.AbsenceCongeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/absence")
public class AbsenceCongeController {

    private final AbsenceCongeService service;

    public AbsenceCongeController(AbsenceCongeService service) {
        this.service = service;
    }

    @PostMapping("/demander")
    public AbsenceConge demander(@RequestBody AbsenceConge a) {
        return service.demander(a);
    }

    @PostMapping("/valider")
    public AbsenceConge valider(@RequestParam Long id_absence) {
        return service.valider(id_absence);
    }

    @PostMapping("/refuser")
    public AbsenceConge refuser(@RequestParam Long id_absence) {
        return service.refuser(id_absence);
    }

    @GetMapping("/findAll")
    public List<AbsenceConge> findAll() {
        return service.findAll();
    }
}