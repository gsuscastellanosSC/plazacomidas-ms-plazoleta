package com.plazacomidas.plazoleta.application.usecase;

import static org.junit.jupiter.api.Assertions.*;

import com.plazacomidas.plazoleta.adapters.in.web.dto.OrderResponseDto;
import com.plazacomidas.plazoleta.adapters.in.web.dto.UserResponseDto;
import com.plazacomidas.plazoleta.application.port.in.UserServicePort;
import com.plazacomidas.plazoleta.domain.exception.InvalidCredentialsException;
import com.plazacomidas.plazoleta.domain.model.Order;
import com.plazacomidas.plazoleta.domain.model.OrderStatus;
import com.plazacomidas.plazoleta.domain.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;

class GetOrdersUseCaseTest {

    @InjectMocks
    private GetOrdersUseCase useCase;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserServicePort userClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetOrdersByStatusForEmployee_success() {
        Long employeeId = 1L;
        UserResponseDto user = new UserResponseDto();
        user.setRestaurantId(String.valueOf(5L));

        Order order = Order.builder()
                .id(100L)
                .clientId(200L)
                .restaurantId(5L)
                .creationDate(LocalDateTime.now())
                .status(OrderStatus.PENDIENTE)
                .dishes(List.of())
                .build();

        Page<Order> orderPage = new PageImpl<>(List.of(order));

        when(userClient.getUserById(employeeId)).thenReturn(user);
        when(orderRepository.findByRestaurantIdAndStatus(eq(5L), eq(OrderStatus.PENDIENTE), (PageRequest) any(Pageable.class)))
                .thenReturn(orderPage);

        Page<OrderResponseDto> result = useCase.getOrdersByStatusForEmployee(employeeId, "pendiente", 0, 10);

        assertEquals(1, result.getContent().size());
        OrderResponseDto responseDto = result.getContent().get(0);
        assertEquals(100L, responseDto.getId());
        assertEquals(0, responseDto.getDishes().size());
        assertEquals("PENDIENTE", responseDto.getStatus());
    }

    @Test
    void testGetOrdersByStatusForEmployee_invalidStatus() {
        Long employeeId = 1L;
        UserResponseDto user = new UserResponseDto();
        user.setRestaurantId(String.valueOf(5L));

        when(userClient.getUserById(employeeId)).thenReturn(user);

        assertThrows(IllegalArgumentException.class, () ->
                useCase.getOrdersByStatusForEmployee(employeeId, "estado_invalido", 0, 10));
    }

    @Test
    void testGetOrdersByStatusForEmployee_userWithoutRestaurant() {
        Long employeeId = 1L;
        UserResponseDto user = new UserResponseDto();
        user.setRestaurantId(null);

        when(userClient.getUserById(employeeId)).thenReturn(user);

        assertThrows(InvalidCredentialsException.class, () ->
                useCase.getOrdersByStatusForEmployee(employeeId, "pendiente", 0, 10));
    }

    @Test
    void testGetOrdersByStatusForEmployee_userNotFound() {
        Long employeeId = 1L;
        when(userClient.getUserById(employeeId)).thenReturn(null);

        assertThrows(InvalidCredentialsException.class, () ->
                useCase.getOrdersByStatusForEmployee(employeeId, "pendiente", 0, 10));
    }
}
