package com.plazacomidas.plazoleta.application.usecase;

import static org.junit.jupiter.api.Assertions.*;

import com.plazacomidas.plazoleta.adapters.in.web.dto.EmployeeEfficiencyDto;
import com.plazacomidas.plazoleta.adapters.in.web.dto.OrderEfficiencyDto;
import com.plazacomidas.plazoleta.adapters.in.web.dto.OrderEfficiencyResponseDto;
import com.plazacomidas.plazoleta.application.port.in.OrderEfficiencyRepositoryPort;
import com.plazacomidas.plazoleta.domain.exception.InvalidCredentialsException;
import com.plazacomidas.plazoleta.domain.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.mockito.Mockito.*;

class GetOrderEfficiencyUseCaseTest {

    @InjectMocks
    private GetOrderEfficiencyUseCase useCase;

    @Mock
    private OrderEfficiencyRepositoryPort repository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetEfficiency_validOwner_returnsEfficiencyData() {
        Long restaurantId = 1L;
        Long ownerId = 10L;

        when(restaurantRepository.existsByIdAndOwnerId(restaurantId, ownerId)).thenReturn(true);

        List<OrderEfficiencyDto> orders = List.of(OrderEfficiencyDto.builder()
                .orderId(123L)
                .totalTimeMinutes(45L)
                .build());

        List<EmployeeEfficiencyDto> ranking = List.of(EmployeeEfficiencyDto.builder()
                .employeeId(5L)
                .employeeName("Empleado A")
                .averageTimeMinutes(15.5)
                .build());

        when(repository.findOrderEfficiencyByRestaurantId(restaurantId)).thenReturn(orders);
        when(repository.findEmployeeRankingByRestaurantId(restaurantId)).thenReturn(ranking);

        OrderEfficiencyResponseDto result = useCase.getEfficiency(restaurantId, ownerId);

        assertNotNull(result);
        assertEquals(1, result.getOrderEfficiency().size());
        assertEquals(123L, result.getOrderEfficiency().get(0).getOrderId());
        assertEquals(45L, result.getOrderEfficiency().get(0).getTotalTimeMinutes());
        assertEquals(1, result.getEmployeeRanking().size());
        assertEquals("Empleado A", result.getEmployeeRanking().get(0).getEmployeeName());
    }

    @Test
    void testGetEfficiency_invalidOwner_throwsException() {
        Long restaurantId = 2L;
        Long ownerId = 99L;

        when(restaurantRepository.existsByIdAndOwnerId(restaurantId, ownerId)).thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> useCase.getEfficiency(restaurantId, ownerId));
    }
}
