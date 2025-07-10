package com.cultodeportivo.backend_upsbank.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cultodeportivo.backend_upsbank.models.TipoTransaccion;
import com.cultodeportivo.backend_upsbank.repositories.TipoTransaccionRepository;

@Service
public class TipoTransaccionService {
    
    private final TipoTransaccionRepository tipoTransaccionRepository;

    public TipoTransaccionService(TipoTransaccionRepository tipoTransaccionRepository) {
        this.tipoTransaccionRepository = tipoTransaccionRepository;
    }

    public List<TipoTransaccion> findAll() {
        return this.tipoTransaccionRepository.findAll();
    }

    public Optional<TipoTransaccion> findById(Long id) {
        return this.tipoTransaccionRepository.findById(id);
    }


}
