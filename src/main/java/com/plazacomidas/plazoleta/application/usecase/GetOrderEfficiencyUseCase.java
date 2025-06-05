package com.plazacomidas.plazoleta.application.usecase;


import com.plazacomidas.plazoleta.adapters.in.web.dto.EmployeeEfficiencyDto;
import com.plazacomidas.plazoleta.adapters.in.web.dto.OrderEfficiencyDto;
import com.plazacomidas.plazoleta.adapters.in.web.dto.OrderEfficiencyResponseDto;
import com.plazacomidas.plazoleta.application.port.in.GetOrderEfficiencyUseCasePort;
import com.plazacomidas.plazoleta.application.port.in.OrderEfficiencyRepositoryPort;
import com.plazacomidas.plazoleta.domain.exception.BusinessException;
import com.plazacomidas.plazoleta.domain.exception.InvalidCredentialsException;
import com.plazacomidas.plazoleta.domain.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetOrderEfficiencyUseCase implements GetOrderEfficiencyUseCasePort {

    private final OrderEfficiencyRepositoryPort repository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public OrderEfficiencyResponseDto getEfficiency(Long restaurantId, Long ownerId) {
        if (!restaurantRepository.existsByIdAndOwnerId(restaurantId, ownerId)) {
            throw new InvalidCredentialsException("El restaurante no pertenece al propietario autenticado");
        }

        List<OrderEfficiencyDto> orders = repository.findOrderEfficiencyByRestaurantId(restaurantId);
        List<EmployeeEfficiencyDto> ranking = repository.findEmployeeRankingByRestaurantId(restaurantId);

        return OrderEfficiencyResponseDto.builder()
                .orderEfficiency(orders)
                .employeeRanking(ranking)
                .build();
    }
}
