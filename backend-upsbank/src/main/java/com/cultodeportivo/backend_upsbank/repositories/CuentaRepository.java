package com.cultodeportivo.backend_upsbank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cultodeportivo.backend_upsbank.models.Cuenta;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    
}
