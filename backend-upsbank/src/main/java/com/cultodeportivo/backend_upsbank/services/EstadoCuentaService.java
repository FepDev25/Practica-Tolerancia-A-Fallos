package com.cultodeportivo.backend_upsbank.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cultodeportivo.backend_upsbank.models.EstadoCuenta;
import com.cultodeportivo.backend_upsbank.repositories.EstadoCuentaRepository;

@Service
public class EstadoCuentaService {
    
    private final EstadoCuentaRepository estadoCuentaRepository;

    public EstadoCuentaService(EstadoCuentaRepository estadoCuentaRepository) {
        this.estadoCuentaRepository = estadoCuentaRepository;
    }

    @Transactional(readOnly = true)
    public List<EstadoCuenta> findAll() {
        return estadoCuentaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<EstadoCuenta> findById(Long id) {
        return estadoCuentaRepository.findById(id);
    }

}
