package com.cultodeportivo.backend_upsbank.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cultodeportivo.backend_upsbank.dao.CuentaDAO;
import com.cultodeportivo.backend_upsbank.exceptions.EstadoCuentaNotFoundException;
import com.cultodeportivo.backend_upsbank.exceptions.CuentaNotFoundException;
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

    @Transactional(readOnly = true)
    public Cuenta findById(Long id) {
        return cuentaRepository.findById(id)
                .orElseThrow(() -> new CuentaNotFoundException(id));
    }

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
    public Optional<Cuenta> updateCuenta (Long id, CuentaDAO cuentaDAO) {
        Optional<Cuenta> op = cuentaRepository.findById(id);
        if (op.isPresent()) {
            cuentaDAO.setId(id);
            return Optional.of(this.save(cuentaDAO));
        } else{
            throw new CuentaNotFoundException(id);
        }
        
    }

    @Transactional
    public Optional<Cuenta> delete (Long id) {
        Optional<Cuenta> op = cuentaRepository.findById(id);
        if (op.isPresent()) {
            this.cuentaRepository.delete(op.get());
            return op;
        } else{
            throw new CuentaNotFoundException(id);
        }
    }

}
