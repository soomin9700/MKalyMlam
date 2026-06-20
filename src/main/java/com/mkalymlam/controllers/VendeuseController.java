package com.mkalymlam.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VendeuseController {

    @GetMapping("/vendeuse")
    public String vendeuse() {
        return "vendeuse";
    }
}
