package com.cultodeportivo.backend_upsbank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cultodeportivo.backend_upsbank.models.TipoTransaccion;

public interface TipoTransaccionRepository extends JpaRepository<TipoTransaccion, Long> {
}
