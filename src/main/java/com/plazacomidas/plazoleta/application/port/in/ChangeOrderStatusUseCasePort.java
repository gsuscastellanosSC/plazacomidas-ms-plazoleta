package com.plazacomidas.plazoleta.application.port.in;

public interface ChangeOrderStatusUseCasePort {
    void markOrderAsReady(Long orderId, Long employeeId);
    void markOrderAsDelivered(Long orderId, Long employeeId, String pin);
}
