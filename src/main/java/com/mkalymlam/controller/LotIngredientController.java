package com.mkalymlam.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mkalymlam.entity.LotIngredient;
import com.mkalymlam.service.IngredientService;
import com.mkalymlam.service.LotIngredientService;
import com.mkalymlam.service.TypeMouvementService;

@Controller
@RequestMapping("/lot")
public class LotIngredientController {

    private final LotIngredientService service;
    private final IngredientService ingredientService;
    private final TypeMouvementService typeMouvementService;

    @Autowired
    public LotIngredientController(LotIngredientService service, IngredientService ingredientService,
            TypeMouvementService typeMouvementService) {
        this.service = service;
        this.ingredientService = ingredientService;
        this.typeMouvementService = typeMouvementService;
    }

    @GetMapping("/findAll")
    public String findAllView(Model model) {
        List<LotIngredient> lots = service.getAll();
        java.util.Map<Long, Double> quantitesRestantes = new java.util.HashMap<>();
        for (LotIngredient lot : lots) {
            quantitesRestantes.put(lot.getIdLot(), service.getQuantiteRestantePourLot(lot));
        }
        model.addAttribute("lots", lots);
        model.addAttribute("quantitesRestantes", quantitesRestantes);
        model.addAttribute("ingredients", ingredientService.findAll());
        model.addAttribute("activeMenu", "lots");
        return "lot/list";
    }

    @GetMapping("/api")
    @ResponseBody
    public List<LotIngredient> findAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public LotIngredient getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/save")
    public String save(@ModelAttribute LotIngredient lotIngredient, RedirectAttributes redirectAttributes) {
        service.save(lotIngredient);
        redirectAttributes.addFlashAttribute("successMessage", "Lot ajouté avec succès.");
        return "redirect:/lot/findAll";
    }

    @PutMapping("/update/{id}")
    @ResponseBody
    public LotIngredient update(@PathVariable Long id, @RequestBody LotIngredient lotIngredient) {
        return service.update(id, lotIngredient);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok("Lot supprimé");
    }

    @GetMapping("/ingredients/view/bientot-perimes")
    public String viewIngredientsBientotPerimes(
            @RequestParam(required = false) Long ingredientId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateMin,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateMax,
            Model model) {
        List<LotIngredient> lots = service.getIngredientsBientotPerimesFiltered(dateMin, dateMax, ingredientId);
        model.addAttribute("lots", lots);
        model.addAttribute("ingredients", ingredientService.findAll());
        model.addAttribute("selectedIngredientId", ingredientId);
        model.addAttribute("selectedDateMin", dateMin);
        model.addAttribute("selectedDateMax", dateMax);
        java.util.Map<Long, Double> quantitesRestantes = new java.util.HashMap<>();
        for (LotIngredient lot : lots) {
            quantitesRestantes.put(lot.getIdLot(), service.getQuantiteRestantePourLot(lot));
        }
        model.addAttribute("quantitesRestantes", quantitesRestantes);
        return "ingredient/ingredient-bientot-perimes";
    }

    @GetMapping("/ingredients/view/perimes")
    public String viewIngredientsPerimes(
            @RequestParam(required = false) Long ingredientId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateMin,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateMax,
            Model model) {
        model.addAttribute("ingredients", ingredientService.findAll());
        model.addAttribute("montantTotalPerime", service.getPerteByPeremption());
        List<LotIngredient> lots = service.getIngredientsPerimesFiltered(dateMin, dateMax, ingredientId);
        model.addAttribute("lots", lots);
        java.util.Map<Long, Double> quantitesRestantes = new java.util.HashMap<>();
        for (LotIngredient lot : lots) {
            quantitesRestantes.put(lot.getIdLot(), service.getQuantiteRestantePourLot(lot));
        }
        model.addAttribute("quantitesRestantes", quantitesRestantes);
        model.addAttribute("selectedIngredientId", ingredientId);
        model.addAttribute("selectedDateMin", dateMin);
        model.addAttribute("selectedDateMax", dateMax);
        return "ingredient/ingredient-perimes";
    }

    @GetMapping("/ingredients/new")
    public String createLotIngredientForm(
            @RequestParam(required = false) Long ingredientId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateMin,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateMax,
            Model model) {
        model.addAttribute("lotIngredient", new LotIngredient());
        model.addAttribute("ingredients", ingredientService.findAll());
        model.addAttribute("typeMouvements", typeMouvementService.findAll());
        model.addAttribute("actionUrl", "/lot/ingredients");
        model.addAttribute("activeMenu", "lot-ingredients");
        model.addAttribute("ingredientId", ingredientId);
        model.addAttribute("dateMin", dateMin);
        model.addAttribute("dateMax", dateMax);
        model.addAttribute("lots", service.filterIngredients(dateMin, dateMax, ingredientId));
        return "lotIngredien/form";
    }

    @PostMapping("/ingredients")
    public String createLotIngredient(@ModelAttribute LotIngredient lotIngredient) {
        service.save(lotIngredient);
        return "redirect:/lot/ingredients/new";
    }

    @GetMapping("/ingredients/alertes")
    public String viewLotIngredientAlertes(Model model) {
        model.addAttribute("ingredientsAlerte", service.getIngredientsAlerte());
        model.addAttribute("quantitesParIngredient", service.getQuantiteTotaleParIngredientMap());
        model.addAttribute("activeMenu", "lot-ingredients-alertes");
        return "ingredient/ingredient-alertes";
    }

    @GetMapping("/ingredients/filter")
    @ResponseBody
    public List<LotIngredient> filterIngredients(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Long idIngredient) {
        return service.filterIngredients(startDate, endDate, idIngredient);
    }

}
