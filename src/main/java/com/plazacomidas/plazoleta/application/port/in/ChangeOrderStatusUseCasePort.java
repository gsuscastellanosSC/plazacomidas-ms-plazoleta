package com.plazacomidas.plazoleta.application.port.in;

public interface ChangeOrderStatusUseCasePort {
    void markOrderAsReady(Long orderId, Long employeeId);
}
