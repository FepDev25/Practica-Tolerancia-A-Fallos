package com.cultodeportivo.backend_upsbank.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cultodeportivo.backend_upsbank.dao.CuentaDAO;
import com.cultodeportivo.backend_upsbank.exceptions.CuentaNotFoundException;
import com.cultodeportivo.backend_upsbank.exceptions.EstadoCuentaNotFoundException;
import com.cultodeportivo.backend_upsbank.models.Cuenta;
import com.cultodeportivo.backend_upsbank.models.EstadoCuenta;
import com.cultodeportivo.backend_upsbank.models.Usuario;
import com.cultodeportivo.backend_upsbank.repositories.CuentaRepository;

@Service
public class CuentaService {
    
    private final CuentaRepository cuentaRepository;
    private final UsuarioService usuarioService;
    private final EstadoCuentaService estadoCuentaService;

    public CuentaService(CuentaRepository cuentaRepository, UsuarioService usuarioService, EstadoCuentaService estadoCuentaService) {
        this.cuentaRepository = cuentaRepository;
        this.usuarioService = usuarioService;
        this.estadoCuentaService = estadoCuentaService;
    }

    @Transactional(readOnly = true)
    public List<Cuenta> findAll() {
        return cuentaRepository.findAll();
    }

    @Retryable(
        value = { Exception.class }, 
        maxAttempts = 3, 
        backoff = @Backoff(delay = 2000)
    )
    @Transactional(readOnly = true)
    public Cuenta findById(Long id) {
        return cuentaRepository.findById(id)
                .orElseThrow(() -> new CuentaNotFoundException(id));
    }

    @Retryable(
        value = { Exception.class },
        maxAttempts = 3,
        backoff = @Backoff(delay = 1500)
    )
    @Transactional
    public Cuenta save(CuentaDAO cuentaDAO) {
        Cuenta cuenta = new Cuenta();
        if (null != cuentaDAO.getId()) {
            cuenta.setId(cuentaDAO.getId());
        }
        cuenta.setSaldo(cuentaDAO.getSaldo());
        cuenta.setFechaCreacion(cuentaDAO.getFechaCreacion());

        Usuario usuario = this.usuarioService.findById(cuentaDAO.getUsuarioId());
        cuenta.setUsuario(usuario);

        EstadoCuenta estadoCuenta = this.estadoCuentaService.findById(cuentaDAO.getEstadoCuentaId())
                .orElseThrow(() -> new EstadoCuentaNotFoundException(cuentaDAO.getEstadoCuentaId()));
        cuenta.setEstadoCuenta(estadoCuenta);

        return cuentaRepository.save(cuenta);
    }

    @Transactional
    public Cuenta crearCuentaParaUsuario(Long userId) {
        CuentaDAO cuentaDAO = new CuentaDAO();
        cuentaDAO.setSaldo(BigDecimal.ZERO);
        cuentaDAO.setFechaCreacion(LocalDateTime.now());
        cuentaDAO.setEstadoCuentaId(1L);
        cuentaDAO.setUsuarioId(userId);
        return this.save(cuentaDAO);
    }

    @Retryable(
        value = { Exception.class },
        maxAttempts = 3,
        backoff = @Backoff(delay = 1000)
    )
    @Transactional
    public Optional<Cuenta> updateCuenta(Long id, CuentaDAO cuentaDAO) {
        Optional<Cuenta> op = cuentaRepository.findById(id);
        if (op.isPresent()) {
            cuentaDAO.setId(id);
            return Optional.of(this.save(cuentaDAO));
        } else {
            throw new CuentaNotFoundException(id);
        }
    }

    @Retryable(
        value = { Exception.class },
        maxAttempts = 3,
        backoff = @Backoff(delay = 1000)
    )
    @Transactional
    public Optional<Cuenta> delete(Long id) {
        Optional<Cuenta> op = cuentaRepository.findById(id);
        if (op.isPresent()) {
            this.cuentaRepository.delete(op.get());
            return op;
        } else {
            throw new CuentaNotFoundException(id);
        }
    }
}
