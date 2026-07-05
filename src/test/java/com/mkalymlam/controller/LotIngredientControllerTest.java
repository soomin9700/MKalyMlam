package com.mkalymlam.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.mkalymlam.entity.LotIngredient;
import com.mkalymlam.service.IngredientService;
import com.mkalymlam.service.LotIngredientService;

@WebMvcTest(LotIngredientController.class)
class LotIngredientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LotIngredientService lotIngredientService;

    @MockBean
    private IngredientService ingredientService;

    @Test
    void bientotPerimesEndpointShouldReturnJsonPayload() throws Exception {
        LotIngredient lot = new LotIngredient();
        lot.setIdLot(1L);
        when(lotIngredientService.getIngredientsBientotPerimes()).thenReturn(List.of(lot));

        mockMvc.perform(get("/lot/ingredients/bientot-perimes"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}
