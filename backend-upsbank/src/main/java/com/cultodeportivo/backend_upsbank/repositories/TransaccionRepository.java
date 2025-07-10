package com.cultodeportivo.backend_upsbank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cultodeportivo.backend_upsbank.models.Transaccion;

public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
    
}
