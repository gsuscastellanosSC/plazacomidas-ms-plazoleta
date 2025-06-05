package com.plazacomidas.plazoleta.application.port.in;

public interface AssignOrderUseCasePort {
    void assignOrder(Long orderId, Long employeeId);
}
