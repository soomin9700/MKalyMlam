package com.mkalymlam.controller;

import com.mkalymlam.entity.Itineraire;
import com.mkalymlam.entity.Produit;
import com.mkalymlam.service.ItineraireService;
import com.mkalymlam.service.ProduitService;
import com.mkalymlam.service.SessionTruckPositionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FrontController.class)
class FrontControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItineraireService itineraireService;

    @MockBean
    private ProduitService produitService;

    @MockBean
    private SessionTruckPositionService positionTruckService;

    @Test
    void shouldExposeDynamicDataToTheFrontPage() throws Exception {
        Itineraire itineraire = new Itineraire();
        itineraire.setNomZone("Analakely");
        itineraire.setLieuExact("Devant la gare");

        Produit produit = new Produit("Boss Burger", 12500.0, true, LocalDate.now());

        when(itineraireService.findAll()).thenReturn(List.of(itineraire));
        when(produitService.findAll()).thenReturn(List.of(produit));

        mockMvc.perform(get("/front"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("itineraires"))
                .andExpect(model().attributeExists("produits"))
                .andExpect(model().attributeExists("menuItemsJson"))
                .andExpect(model().attributeExists("itinerairesJson"));
    }
}
