package com.mkalymlam.controller;

import java.util.List;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mkalymlam.entity.LotIngredient;
import com.mkalymlam.service.LotIngredientService;

@RestController
@RequestMapping("/lot")
public class LotIngredientController {

    private final LotIngredientService service;

    public LotIngredientController(LotIngredientService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public LotIngredient save(@RequestBody LotIngredient lotIngredient) {
        return service.save(lotIngredient);
    }

    @PutMapping("/update/{id}")
    public LotIngredient update(@PathVariable Long id, @RequestBody LotIngredient lotIngredient) {
        return service.update(id, lotIngredient);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteById(id);
        return "Lot supprimé";
    }

    @GetMapping("/find")
    public List<LotIngredient> find(
            @RequestParam(required = false) Long idLot,
            @RequestParam(required = false) String nomIngredient) {
        if (idLot != null) {
            LotIngredient lot = service.getById(idLot);
            return lot == null ? List.of() : List.of(lot);
        }
        if (nomIngredient != null && !nomIngredient.isBlank()) {
            return service.findByIngredientName(nomIngredient);
        }
        return service.getAll();
    }

    @GetMapping("/findAll")
    public List<LotIngredient> findAll() {
        return service.getAll();
    }

    @GetMapping("/alertes")
    public List<LotIngredient> alertes() {
        return service.getAlertLots();
    }

    @GetMapping("/ingredients/bientot-perimes")
    public List<LotIngredient> getIngredientsBientotPerimes() {
        return service.getIngredientsBientotPerimes();
    }

    @GetMapping("/ingredients/bientot-perimes/{idIngredient}")
    public List<LotIngredient> getIngredientsBientotPerimesByIdIngredient(@PathVariable Long idIngredient) {
        return service.getIngredientsBientotPerimesByIdIngredient(idIngredient);
    }

    @GetMapping("/ingredients/perimes")
    public List<LotIngredient> getIngredientsPerimes() {
        return service.getIngredientsPerimes();
    }

    @GetMapping("/ingredients/filter")
    public List<LotIngredient> filterIngredients(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Long idIngredient) {
        return service.filterIngredients(startDate, endDate, idIngredient);
    }
}
