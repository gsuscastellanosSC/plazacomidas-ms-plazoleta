package com.plazacomidas.plazoleta.domain.repository;

import com.plazacomidas.plazoleta.infrastructure.adapter.out.database.entity.Traceability;

import java.util.List;

public interface TraceabilityRepository {
    List<Traceability> findByOrderId(Long orderId);
    Traceability save(Traceability traceability);
}
