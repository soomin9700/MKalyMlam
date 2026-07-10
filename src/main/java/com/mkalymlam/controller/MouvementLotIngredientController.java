package com.mkalymlam.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mkalymlam.entity.MouvementLotIngredient;
import com.mkalymlam.service.MouvementLotIngredientService;

@Controller
@RequestMapping("/mouvement")
public class MouvementLotIngredientController {

    private final MouvementLotIngredientService service;

    public MouvementLotIngredientController(MouvementLotIngredientService service) {
        this.service = service;
    }

    @PostMapping("/save")
    @ResponseBody
    public MouvementLotIngredient save(@RequestBody MouvementLotIngredient mouvement) {
        return service.save(mouvement);
    }

    @GetMapping("/findByLot/{idLot}")
    @ResponseBody
    public List<MouvementLotIngredient> findByLot(@PathVariable Long idLot) {
        return service.findByLotId(idLot);
    }

    @GetMapping("/findAll")
    @ResponseBody
    public List<MouvementLotIngredient> findAll() {
        return service.findByLotId(null);
    }
}
