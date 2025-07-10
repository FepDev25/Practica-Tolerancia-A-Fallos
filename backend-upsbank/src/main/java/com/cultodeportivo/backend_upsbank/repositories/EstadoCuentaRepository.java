package com.cultodeportivo.backend_upsbank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cultodeportivo.backend_upsbank.models.EstadoCuenta;

public interface EstadoCuentaRepository extends JpaRepository<EstadoCuenta, Long> {
    
}
