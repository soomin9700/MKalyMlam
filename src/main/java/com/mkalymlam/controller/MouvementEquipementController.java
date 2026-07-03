package com.mkalymlam.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.mkalymlam.entity.Equipement;
import com.mkalymlam.entity.MouvementEquipement;
import com.mkalymlam.entity.TypeMouvement;
import com.mkalymlam.service.EquipementService;
import com.mkalymlam.service.MouvementEquipementService;
import com.mkalymlam.service.TypeMouvementService;

@Controller
@RequestMapping("/mouvements-equipement")
public class MouvementEquipementController {

    private final MouvementEquipementService service;
    private final TypeMouvementService typeMouvementService;
    private final EquipementService equipementService;

    public MouvementEquipementController(
            MouvementEquipementService service,
            TypeMouvementService typeMouvementService,
            EquipementService equipementService) {

        this.service = service;
        this.typeMouvementService = typeMouvementService;
        this.equipementService = equipementService;
    }

    @GetMapping("/findall")
    @ResponseBody
    public List<MouvementEquipement> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public MouvementEquipement getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/save")
    @ResponseBody
    public MouvementEquipement save(@RequestBody MouvementEquipement entity) {
        return service.save(entity);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }

    @GetMapping
    public String showForm(Model model) {
        MouvementEquipement mouvement = new MouvementEquipement();
        mouvement.setDateMouvement(LocalDate.now());

        model.addAttribute("mouvements", service.findAll());
        model.addAttribute("typeMouvements", typeMouvementService.findAll());
        model.addAttribute("equipements", equipementService.findAll());
        model.addAttribute("mouvementEquipement", mouvement);
        model.addAttribute("actionUrl", "/mouvements-equipement");

        return "mouvementEquipement/form";
    }

    @PostMapping
    public String create(@ModelAttribute MouvementEquipement mouvementEquipement) {
        service.save(mouvementEquipement);
        return "redirect:/mouvements-equipement";
    }
}