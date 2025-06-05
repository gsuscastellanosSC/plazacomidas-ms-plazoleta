package com.plazacomidas.plazoleta.application.usecase;

import com.plazacomidas.plazoleta.application.dto.RegisterTraceabilityDto;
import com.plazacomidas.plazoleta.application.port.in.RegisterOrderTraceUseCasePort;
import com.plazacomidas.plazoleta.domain.repository.OrderRepository;
import com.plazacomidas.plazoleta.infrastructure.adapter.out.database.entity.Traceability;
import com.plazacomidas.plazoleta.infrastructure.adapter.out.database.repository.TraceabilityJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RegisterOrderTraceUseCase implements RegisterOrderTraceUseCasePort {

    private final OrderRepository orderRepository;
    private final TraceabilityJpaRepository traceabilityRepository;

    @Override
    public void registerTrace(RegisterTraceabilityDto registerTraceabilityDto) {
        Traceability trace = Traceability.builder()
                .orderId(registerTraceabilityDto.getOrderId())
                .previousStatus(registerTraceabilityDto.getPreviousStatus())
                .newStatus(registerTraceabilityDto.getNewStatus())
                .clientId(String.valueOf(registerTraceabilityDto.getClientId()))
                .clientEmail(registerTraceabilityDto.getClientEmail())
                .employeeId(registerTraceabilityDto.getEmployeeId())
                .employeeEmail(registerTraceabilityDto.getEmployeeEmail())
                .date(LocalDateTime.now())
                .build();
        traceabilityRepository.save(trace);
    }
}
