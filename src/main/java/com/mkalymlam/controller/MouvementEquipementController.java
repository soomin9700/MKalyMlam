package com.mkalymlam.controller;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.mkalymlam.entity.Equipement;
import com.mkalymlam.entity.MouvementEquipement;
import com.mkalymlam.service.EquipementService;
import com.mkalymlam.service.ExportService;
import com.mkalymlam.service.MouvementEquipementService;
import com.mkalymlam.service.TypeMouvementService;

@Controller
@RequestMapping("/mouvements-equipement")
public class MouvementEquipementController {

    private final MouvementEquipementService service;
    private final TypeMouvementService typeMouvementService;
    private final EquipementService equipementService;
    private final ExportService exportService;

    public MouvementEquipementController(
            MouvementEquipementService service,
            TypeMouvementService typeMouvementService,
            EquipementService equipementService,
            ExportService exportService) {

        this.service = service;
        this.typeMouvementService = typeMouvementService;
        this.equipementService = equipementService;
        this.exportService = exportService;
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

    @GetMapping("/export")
    public ResponseEntity<InputStreamResource> exportCsv() throws Exception {
        File fichier = exportService.exportCsv("mouvementEquipement");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(fichier));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fichier.getName())
                .contentType(MediaType.parseMediaType("text/csv"))
                .contentLength(fichier.length())
                .body(resource);
    }

    @GetMapping("/etat-stock")
    public String etatStock(Model model) {
        List<Equipement> equipements = equipementService.findAll();
        Map<Long, Double> stockMap = new HashMap<>();

        for (Equipement equipement : equipements) {
            stockMap.put(equipement.getIdEquipement(),
                    equipementService.getQuantiteStock(equipement.getIdEquipement()));
        }

        model.addAttribute("equipements", equipements);
        model.addAttribute("stockMap", stockMap);
        model.addAttribute("activeMenu", "mouvements-equipement");

        return "mouvementEquipement/etatStock";
    }
}