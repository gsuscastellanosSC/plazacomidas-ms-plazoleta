package com.plazacomidas.plazoleta.adapters.in.web.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.plazacomidas.plazoleta.adapters.in.web.dto.DishRequestDto;
import com.plazacomidas.plazoleta.adapters.in.web.dto.UpdateDishRequestDto;
import com.plazacomidas.plazoleta.application.port.in.UpdateDishUseCasePort;
import com.plazacomidas.plazoleta.application.usecase.CreateDishUseCase;
import com.plazacomidas.plazoleta.domain.model.Dish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(DishController.class)
class DishControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateDishUseCase createDishUseCase;

    @MockBean
    private UpdateDishUseCasePort updateDishUseCasePort;

    @Autowired
    private ObjectMapper objectMapper;

    private DishRequestDto dishRequestDto;
    private UpdateDishRequestDto updateDishRequestDto;

    @BeforeEach
    void setup() {
        dishRequestDto = new DishRequestDto();
        dishRequestDto.setNombre("Hamburguesa");
        dishRequestDto.setPrecio(20000);
        dishRequestDto.setDescripcion("Con papas");
        dishRequestDto.setUrlImagen("http://imagen.com");
        dishRequestDto.setIdCategoria(1L);
        dishRequestDto.setRestauranteId(10L);

        updateDishRequestDto = new UpdateDishRequestDto();
        updateDishRequestDto.setPrice(25000);
        updateDishRequestDto.setDescription("Con papas y queso");
    }

    @Test
    void crearPlato_retornaDishCreado() throws Exception {
        Dish dish = Dish.builder()
                .id(1L)
                .nombre(dishRequestDto.getNombre())
                .precio(dishRequestDto.getPrecio())
                .descripcion(dishRequestDto.getDescripcion())
                .urlImagen(dishRequestDto.getUrlImagen())
                .idCategoria(dishRequestDto.getIdCategoria())
                .restauranteId(dishRequestDto.getRestauranteId())
                .activo(true)
                .build();

        when(createDishUseCase.execute(any(Dish.class), eq(5L))).thenReturn(dish);

        mockMvc.perform(post("/platos")
                        .header("user-id", 5L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dishRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Hamburguesa")))
                .andExpect(jsonPath("$.precio", is(20000)))
                .andExpect(jsonPath("$.descripcion", is("Con papas")));

        verify(createDishUseCase).execute(any(Dish.class), eq(5L));
    }

    @Test
    void actualizarPlato_retornaNoContent() throws Exception {
        mockMvc.perform(put("/platos/1")
                        .header("user-id", 5L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDishRequestDto)))
                .andExpect(status().isNoContent());

        verify(updateDishUseCasePort).execute(1L, 5L, updateDishRequestDto);
    }
}
