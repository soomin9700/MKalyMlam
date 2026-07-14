package com.mkalymlam.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mkalymlam.entity.MouvementLotIngredient;
import com.mkalymlam.service.IngredientService;
import com.mkalymlam.service.LotIngredientService;
import com.mkalymlam.service.MouvementLotIngredientService;
import com.mkalymlam.service.TypeMouvementService;

@Controller
@RequestMapping("/mouvement")
public class MouvementLotIngredientController {

    private final MouvementLotIngredientService service;
    private final LotIngredientService lotIngredientService;
    private final TypeMouvementService typeMouvementService;
    private final IngredientService ingredientService;

    public MouvementLotIngredientController(MouvementLotIngredientService service,
            LotIngredientService lotIngredientService,
            TypeMouvementService typeMouvementService,
            IngredientService ingredientService) {
        this.service = service;
        this.lotIngredientService = lotIngredientService;
        this.typeMouvementService = typeMouvementService;
        this.ingredientService = ingredientService;
    }

    @GetMapping("/findAll")
    public String findAllView(Model model) {
        List<MouvementLotIngredient> mouvements = service.findByLotId(null);
        model.addAttribute("mouvements", mouvements);
        model.addAttribute("lots", lotIngredientService.getAll());
        model.addAttribute("typeMouvements", typeMouvementService.findAll());
        model.addAttribute("activeMenu", "mouvements-lots");
        return "mouvementLotIngredient/form";
    }

    @PostMapping("/save")
    public String save(MouvementLotIngredient mouvement) {
        try {
            service.save(mouvement);
            return "redirect:/mouvement/findAll";
        } catch (Exception e) {
            return "redirect:/mouvement/findAll?error=" + e.getMessage();
        }
    }

    @GetMapping("/findByLot/{idLot}")
    @ResponseBody
    public List<MouvementLotIngredient> findByLot(@PathVariable Long idLot) {
        return service.findByLotId(idLot);
    }

    @GetMapping("/view")
    public String viewList(Model model) {
        List<MouvementLotIngredient> mouvements = service.findByLotId(null);
        model.addAttribute("mouvements", mouvements);
        model.addAttribute("lots", lotIngredientService.getAll());
        model.addAttribute("ingredients", ingredientService.findAll());
        model.addAttribute("activeMenu", "mouvements-lots");
        return "mouvementLotIngredient/list";
    }

    @GetMapping("/findAllJson")
    @ResponseBody
    public List<MouvementLotIngredient> findAllJson() {
        return service.findByLotId(null);
    }

    @PostMapping("/saveJson")
    @ResponseBody
    public MouvementLotIngredient saveJson(@RequestBody MouvementLotIngredient mouvement) {
        return service.save(mouvement);
    }
}
