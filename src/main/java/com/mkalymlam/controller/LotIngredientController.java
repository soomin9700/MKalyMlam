package com.mkalymlam.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
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

import com.mkalymlam.entity.Ingredient;
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

    public LotIngredientController(LotIngredientService service, IngredientService ingredientService,
            TypeMouvementService typeMouvementService) {
        this.service = service;
        this.ingredientService = ingredientService;
        this.typeMouvementService = typeMouvementService;
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("lot", new LotIngredient());
        model.addAttribute("ingredients", service.getAll());
        model.addAttribute("isEdit", false);
        model.addAttribute("actionUrl", "/lot/save");
        return "lot/form";
    }

    @PostMapping("/save")
    @ResponseBody
    public LotIngredient save(@RequestBody LotIngredient lotIngredient) {
        return service.save(lotIngredient);
    }

    @PutMapping("/update/{id}")
    @ResponseBody
    public LotIngredient update(@PathVariable Long id, @RequestBody LotIngredient lotIngredient) {
        return service.update(id, lotIngredient);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public String delete(@PathVariable Long id) {
        service.deleteById(id);
        return "Lot supprimé";
    }

    @GetMapping("/find")
    @ResponseBody
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

    // @GetMapping("/findAll")
    // <<<<<<< HEAD
    // public String findAll(
    // @RequestParam(required = false) String nomIngredient,
    // @RequestParam(required = false) Boolean alerte,
    // Model model) {

    // List<LotIngredient> lots;

    // // filtre par nom si il y a un ingrédient lors de la recherche
    // if (nomIngredient != null && !nomIngredient.isEmpty()) {
    // lots = service.findByIngredientName(nomIngredient);
    // } else {
    // lots = service.getAllWithAlertStatus();
    // }

    // // si le filtre alerte est activé, on filtre les lots en alerte
    // if (alerte != null && alerte) {
    // lots = lots.stream()
    // .filter(lot -> service.verifierAlerte(lot))
    // .collect(Collectors.toList());
    // }

    // model.addAttribute("lots", lots);
    // return "lot/list";
    // }

    // @GetMapping("/alertes")
    // public String alertes(Model model) {
    // List<LotIngredient> lots = service.getAlertLots();
    // model.addAttribute("lots", lots);
    // return "alertes/list";
    // =======
    @ResponseBody
    public List<LotIngredient> findAll() {
        return service.getAll();
    }

    @GetMapping("/alertes")
    @ResponseBody
    public List<Ingredient> alertes() {
        return service.getAlertLots();
    }

    @GetMapping("/ingredients/bientot-perimes")
    @ResponseBody
    public List<LotIngredient> getIngredientsBientotPerimes() {
        return service.getIngredientsBientotPerimes();
    }

    @GetMapping("/ingredients/bientot-perimes/{idIngredient}")
    @ResponseBody
    public List<LotIngredient> getIngredientsBientotPerimesByIdIngredient(@PathVariable Long idIngredient) {
        return service.getIngredientsBientotPerimesByIdIngredient(idIngredient);
    }

    @GetMapping("/ingredients/perimes")
    @ResponseBody
    public List<LotIngredient> getIngredientsPerimes() {
        return service.getIngredientsPerimes();
    }

    @GetMapping("/ingredients/view/bientot-perimes")
    public String viewIngredientsBientotPerimes(
            @RequestParam(required = false) Long ingredientId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateMin,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateMax,
            Model model) {
        java.util.List<LotIngredient> lots = service.getIngredientsBientotPerimesFiltered(dateMin, dateMax,
                ingredientId);
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

    @GetMapping("/ingredients/view/bientot-perimes/{idIngredient}")
    public String viewIngredientsBientotPerimesByIngredient(@PathVariable Long idIngredient, Model model) {
        Ingredient ingredient = ingredientService.getById(idIngredient);
        model.addAttribute("ingredient", ingredient);
        java.util.List<LotIngredient> lots = service.getIngredientsBientotPerimesByIdIngredient(idIngredient);
        model.addAttribute("lots", lots);
        java.util.Map<Long, Double> quantitesRestantes = new java.util.HashMap<>();
        for (LotIngredient lot : lots) {
            quantitesRestantes.put(lot.getIdLot(), service.getQuantiteRestantePourLot(lot));
        }
        model.addAttribute("quantitesRestantes", quantitesRestantes);
        return "ingredient/ingredient-bientot-perimes-by-idIngredient";
    }

    @GetMapping("/ingredients/view/perimes")
    public String viewIngredientsPerimes(
            @RequestParam(required = false) Long ingredientId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateMin,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateMax,
            Model model) {
        model.addAttribute("ingredients", ingredientService.findAll());
        model.addAttribute("montantTotalPerime", service.getPerteByPeremption());
        // calculer les quantités restantes par lot pour l'affichage
        java.util.List<LotIngredient> lots = service.getIngredientsPerimesFiltered(dateMin, dateMax, ingredientId);
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
        return "lotIngredient/form";
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
