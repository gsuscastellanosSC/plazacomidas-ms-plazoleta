package com.plazacomidas.plazoleta.application.port.in;


import com.plazacomidas.plazoleta.adapters.in.web.dto.EmployeeEfficiencyDto;
import com.plazacomidas.plazoleta.adapters.in.web.dto.OrderEfficiencyDto;
import java.util.List;

public interface OrderEfficiencyRepositoryPort {
    List<OrderEfficiencyDto> findOrderEfficiencyByRestaurantId(Long restaurantId);
    List<EmployeeEfficiencyDto> findEmployeeRankingByRestaurantId(Long restaurantId);
}
