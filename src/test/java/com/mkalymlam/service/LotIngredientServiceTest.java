package com.mkalymlam.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mkalymlam.entity.LotIngredient;
import com.mkalymlam.repository.IngredientRepository;
import com.mkalymlam.repository.LotIngredientRepository;
import com.mkalymlam.repository.MouvementLotIngredientRepository;

@ExtendWith(MockitoExtension.class)
class LotIngredientServiceTest {

    @Mock
    private LotIngredientRepository lotIngredientRepository;

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private MouvementLotIngredientRepository mouvementRepository;

    @InjectMocks
    private LotIngredientService lotIngredientService;

    @Test
    void getIngredientsBientotPerimesShouldCallRepositoryWithTodayAndPlusThreeDays() {
        LocalDate today = LocalDate.now();
        LotIngredient lot = new LotIngredient();

        when(lotIngredientRepository.findByDatePeremptionBetween(today, today.plusDays(3))).thenReturn(List.of(lot));

        List<LotIngredient> result = lotIngredientService.getIngredientsBientotPerimes();

        assertEquals(1, result.size());
        verify(lotIngredientRepository).findByDatePeremptionBetween(today, today.plusDays(3));
    }

    @Test
    void getIngredientsBientotPerimesFilteredShouldUseIngredientAndDateCriteria() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(3);
        Long ingredientId = 5L;
        LotIngredient lot = new LotIngredient();

        when(lotIngredientRepository.findByDatePeremptionBetweenAndIngredient_IdIngredient(startDate, endDate,
                ingredientId))
                .thenReturn(List.of(lot));

        List<LotIngredient> result = lotIngredientService.getIngredientsBientotPerimesFiltered(startDate, endDate,
                ingredientId);

        assertEquals(1, result.size());
        verify(lotIngredientRepository).findByDatePeremptionBetweenAndIngredient_IdIngredient(startDate, endDate,
                ingredientId);
    }

    @Test
    void getIngredientsPerimesShouldReturnExpiredLots() {
        LocalDate today = LocalDate.now();
        LotIngredient lot = new LotIngredient();

        when(lotIngredientRepository.findByDatePeremptionBefore(today)).thenReturn(List.of(lot));

        List<LotIngredient> result = lotIngredientService.getIngredientsPerimes();

        assertEquals(1, result.size());
        verify(lotIngredientRepository).findByDatePeremptionBefore(today);
    }

    @Test
    void getIngredientsBientotPerimesByIdIngredientShouldUseIngredientFilter() {
        LocalDate today = LocalDate.now();
        Long ingredientId = 7L;
        LotIngredient lot = new LotIngredient();

        when(lotIngredientRepository.findByDatePeremptionBetweenAndIngredient_IdIngredient(today, today.plusDays(3),
                ingredientId))
                .thenReturn(List.of(lot));

        List<LotIngredient> result = lotIngredientService.getIngredientsBientotPerimesByIdIngredient(ingredientId);

        assertEquals(1, result.size());
        verify(lotIngredientRepository).findByDatePeremptionBetweenAndIngredient_IdIngredient(today, today.plusDays(3),
                ingredientId);
    }
}
