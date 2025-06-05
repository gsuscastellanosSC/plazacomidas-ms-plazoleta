package com.plazacomidas.plazoleta.application.usecase;

import com.plazacomidas.plazoleta.adapters.in.web.dto.TraceabilityResponseDto;
import com.plazacomidas.plazoleta.application.port.in.GetOrderTraceabilityUseCasePort;
import com.plazacomidas.plazoleta.domain.exception.BusinessException;
import com.plazacomidas.plazoleta.domain.model.Order;
import com.plazacomidas.plazoleta.domain.repository.OrderRepository;
import com.plazacomidas.plazoleta.infrastructure.adapter.out.database.entity.Traceability;
import com.plazacomidas.plazoleta.infrastructure.adapter.out.database.repository.TraceabilityJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetOrderTraceabilityUseCase implements GetOrderTraceabilityUseCasePort {

    private final OrderRepository orderRepository;
    private final TraceabilityJpaRepository traceabilityRepository;

    @Override
    public List<TraceabilityResponseDto> getTraceabilityForOrder(Long orderId, Long clientId) {
        Order order = orderRepository.getById(orderId);

        if (!order.getClientId().equals(clientId)) {
            throw new BusinessException("El pedido no pertenece al cliente autenticado");
        }

        List<Traceability> logs = traceabilityRepository.findByOrderId(orderId);

        return logs.stream()
                .map(trace -> TraceabilityResponseDto.builder()
                        .date(trace.getDate())
                        .previousStatus(trace.getPreviousStatus())
                        .newStatus(trace.getNewStatus())
                        .employeeEmail(trace.getEmployeeEmail())
                        .build())
                .toList();
    }
}
