package com.plazacomidas.plazoleta.application.usecase;

import static org.junit.jupiter.api.Assertions.*;

import com.plazacomidas.plazoleta.domain.model.RestaurantModel;
import com.plazacomidas.plazoleta.domain.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.util.List;
import static org.mockito.Mockito.*;

class GetRestaurantsUseCaseTest {

    @InjectMocks
    private GetRestaurantsUseCase useCase;

    @Mock
    private RestaurantRepository restaurantRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_returnsPageOfRestaurants() {
        int page = 0;
        int size = 2;
        Pageable pageable = PageRequest.of(page, size, Sort.by("nombre").ascending());

        RestaurantModel restaurant1 = new RestaurantModel(1L, "A Restaurante", "http://logo.com/a.png");
        RestaurantModel restaurant2 = new RestaurantModel(2L, "B Restaurante", "http://logo.com/b.png");

        Page<RestaurantModel> restaurantPage = new PageImpl<>(List.of(restaurant1, restaurant2));

        when(restaurantRepository.findAll(pageable)).thenReturn(restaurantPage);

        Page<RestaurantModel> result = useCase.execute(page, size);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals("A Restaurante", result.getContent().get(0).getNombre());
    }
}
