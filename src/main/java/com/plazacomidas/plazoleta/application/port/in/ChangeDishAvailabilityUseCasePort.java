package com.plazacomidas.plazoleta.application.port.in;

public interface ChangeDishAvailabilityUseCasePort {
    void execute(Long dishId, Long userId, boolean active);
}
