package com.mkalymlam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("vente")
public class VendeuseController {

    @GetMapping("/vendeuse")
    public String vendeuse() {
        return "vente/vendeuse";
    }
}
