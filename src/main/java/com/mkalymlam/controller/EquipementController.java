package com.mkalymlam.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.mkalymlam.entity.Equipement;
import com.mkalymlam.service.EquipementService;
import com.mkalymlam.service.MethodeComptableService;
import com.mkalymlam.service.TypeEquipementService;

@Controller
public class EquipementController {

    private final EquipementService service;
    private final TypeEquipementService typeEquipementService;
    private final MethodeComptableService methodeComptableService;

    public EquipementController(
            EquipementService service,
            TypeEquipementService typeEquipementService,
            MethodeComptableService methodeComptableService) {

        this.service = service;
        this.typeEquipementService = typeEquipementService;
        this.methodeComptableService = methodeComptableService;
    }

    @ResponseBody
    @PostMapping("/equipement/save")
    public Equipement save(@RequestBody Equipement equipement) {
        return service.save(equipement);
    }

    @ResponseBody
    @PutMapping("/equipement/update")
    public Equipement update(@RequestBody Equipement equipement) {
        return service.update(equipement);
    }

    @ResponseBody
    @DeleteMapping("/equipement/delete/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @ResponseBody
    @GetMapping("/equipement/find/{id}")
    public Equipement find(@PathVariable Long id) {
        return service.find(id);
    }

    @ResponseBody
    @GetMapping("/equipement/findAll")
    public List<Equipement> findAll() {
        return service.findAll();
    }

    @ResponseBody
    @GetMapping("/equipement/alertes")
    public List<Equipement> alertes() {
        return service.getEquipementsEnAlerte();
    }

    @GetMapping("/equipements/alertes")
    public String alertesPage(Model model) {
        model.addAttribute("equipements", service.getEquipementsEnAlerte());
        model.addAttribute("activeMenu", "equipements-alertes");
        return "equipement/equipementAlerte";
    }

    @GetMapping("/equipements")
    public String listAndCreateForm(Model model) {
        model.addAttribute("equipements", service.findAll());
        model.addAttribute("typeEquipements", typeEquipementService.findAll());
        model.addAttribute("methodesComptables", methodeComptableService.findAll());
        model.addAttribute("equipement", new Equipement());
        model.addAttribute("isEdit", false);
        model.addAttribute("actionUrl", "/equipements");
        model.addAttribute("activeMenu", "equipements");

        return "equipement/form";
    }

    @PostMapping("/equipements")
    public String create(@ModelAttribute Equipement equipement) {
        service.save(equipement);
        return "redirect:/equipements";
    }
}