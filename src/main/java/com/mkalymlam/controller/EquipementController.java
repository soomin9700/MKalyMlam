package com.mkalymlam.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.mkalymlam.entity.Equipement;
import com.mkalymlam.service.EquipementService;
import com.mkalymlam.service.ExportService;
import com.mkalymlam.service.MethodeComptableService;
import com.mkalymlam.service.TypeEquipementService;

@Controller
public class EquipementController {

    private final EquipementService service;
    private final TypeEquipementService typeEquipementService;
    private final MethodeComptableService methodeComptableService;
    private final ExportService exportService;

    public EquipementController(
            EquipementService service,
            TypeEquipementService typeEquipementService,
            MethodeComptableService methodeComptableService,
            ExportService exportService) {

        this.service = service;
        this.typeEquipementService = typeEquipementService;
        this.methodeComptableService = methodeComptableService;
        this.exportService = exportService;
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
    public String listAndCreateForm(
            @RequestParam(required = false) Long typeEquipementId,
            @RequestParam(required = false) Long methodeComptableId,
            Model model) {
        model.addAttribute("equipements", service.findFilteredEquipements(typeEquipementId, methodeComptableId));
        model.addAttribute("typeEquipements", typeEquipementService.findAll());
        model.addAttribute("methodesComptables", methodeComptableService.findAll());
        model.addAttribute("equipement", new Equipement());
        model.addAttribute("isEdit", false);
        model.addAttribute("actionUrl", "/equipements");
        model.addAttribute("activeMenu", "equipements");
        model.addAttribute("selectedTypeEquipementId", typeEquipementId);
        model.addAttribute("selectedMethodeComptableId", methodeComptableId);

        return "equipement/form";
    }

    @PostMapping("/equipements")
    public String create(@ModelAttribute Equipement equipement) {
        service.save(equipement);
        return "redirect:/equipements";
    }

    @GetMapping("/equipements/export")
    public ResponseEntity<InputStreamResource> exportCsv() throws Exception {
        File fichier = exportService.exportCsv("equipement");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(fichier));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fichier.getName())
                .contentType(MediaType.parseMediaType("text/csv"))
                .contentLength(fichier.length())
                .body(resource);
    }
}
