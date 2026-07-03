package com.mkalymlam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mkalymlam.entity.Equipement;
import com.mkalymlam.entity.MethodeComptable;
import com.mkalymlam.entity.TypeEquipement;
import com.mkalymlam.service.EquipementService;
import com.mkalymlam.service.MethodeComptableService;
import com.mkalymlam.service.TypeEquipementService;

@Controller
@RequestMapping("/equipements")
public class EquipementWebController {

    private final EquipementService equipementService;
    private final TypeEquipementService typeEquipementService;
    private final MethodeComptableService methodeComptableService;

    public EquipementWebController(
            EquipementService equipementService,
            TypeEquipementService typeEquipementService,
            MethodeComptableService methodeComptableService) {

        this.equipementService = equipementService;
        this.typeEquipementService = typeEquipementService;
        this.methodeComptableService = methodeComptableService;
    }

    @GetMapping
    public String listAndCreateForm(Model model) {
        model.addAttribute("equipements", equipementService.findAll());
        model.addAttribute("typeEquipements", typeEquipementService.findAll());
        model.addAttribute("methodesComptables", methodeComptableService.findAll());
        model.addAttribute("equipement", new Equipement());
        model.addAttribute("isEdit", false);
        model.addAttribute("actionUrl", "/equipements");

        return "equipement/form";
    }

    @PostMapping
    public String create(@ModelAttribute Equipement equipement) {
        equipementService.save(equipement);
        return "redirect:/equipements";
    }
}
