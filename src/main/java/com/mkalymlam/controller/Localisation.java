package com.mkalymlam.controller;

import java.time.*;
import java.util.*;

import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.mkalymlam.entity.*;
import com.mkalymlam.service.ItineraireService;

@Controller
@RequestMapping("/localisation")
public class Localisation {

    private final ItineraireService itineraire;

    public Localisation(ItineraireService itineraire) {
        this.itineraire = itineraire;
    }

    @GetMapping("/form")
    public String formLocalisation(Model model){
        model.addAttribute("itineraire", itineraire.findAll());
        return "localisation/form";
    }

}
