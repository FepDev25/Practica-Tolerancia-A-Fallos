package com.cultodeportivo.backend_upsbank.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cultodeportivo.backend_upsbank.dao.CuentaDAO;
import com.cultodeportivo.backend_upsbank.dao.TransaccionDAO;
import com.cultodeportivo.backend_upsbank.exceptions.CuentaNoActivaException;
import com.cultodeportivo.backend_upsbank.exceptions.FondosInsuficientesException;
import com.cultodeportivo.backend_upsbank.exceptions.TipoTransaccionNotFoundException;
import com.cultodeportivo.backend_upsbank.models.Cuenta;
import com.cultodeportivo.backend_upsbank.models.TipoTransaccion;
import com.cultodeportivo.backend_upsbank.models.Transaccion;
import com.cultodeportivo.backend_upsbank.repositories.TransaccionRepository;

@Service
public class TransaccionService {

    private final TransaccionRepository transaccionRepository;
    private final CuentaService cuentaService;
    private final TipoTransaccionService tipoTransaccionService;

    public TransaccionService(TransaccionRepository transaccionRepository, CuentaService cuentaService, TipoTransaccionService tipoTransaccionService) {
        this.transaccionRepository = transaccionRepository;
        this.cuentaService = cuentaService;
        this.tipoTransaccionService = tipoTransaccionService;
    }

    @Transactional(readOnly = true)
    public List<Transaccion> findAll() {
        return this.transaccionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Transaccion> findById(Long id) {
        return this.transaccionRepository.findById(id);
    }

    @Transactional
    public Transaccion save(TransaccionDAO transaccionDAO) {

        Transaccion transaccion = new Transaccion();
        transaccion.setMonto(transaccionDAO.getMonto());
        transaccion.setFecha(transaccionDAO.getFecha());

        Cuenta cuentaOrigen = this.cuentaService.findById(transaccionDAO.getCuentaOrigenId());
        transaccion.setCuentaOrigen(cuentaOrigen);

        Cuenta cuentaDestino = this.cuentaService.findById(transaccionDAO.getCuentaDestinoId());
        transaccion.setCuentaDestino(cuentaDestino);

        TipoTransaccion tipoTransaccion = this.tipoTransaccionService.findById(transaccionDAO.getTipoTransaccionId())
                .orElseThrow(() -> new TipoTransaccionNotFoundException(transaccionDAO.getTipoTransaccionId()));
        transaccion.setTipoTransaccion(tipoTransaccion);

        this.realizarTransaccion(transaccion);

        this.cuentaService.save(parsearCuentaDAO(cuentaOrigen));
        this.cuentaService.save(parsearCuentaDAO(cuentaDestino));

        return this.transaccionRepository.save(transaccion);
    }


    @Transactional
    public Optional<Transaccion> updateTransaccion (Long id, TransaccionDAO transaccionDAO) {
        Optional<Transaccion> op = transaccionRepository.findById(id);
        if (op.isPresent()) {
            transaccionDAO.setId(id);
            return Optional.of(this.save(transaccionDAO));
        }
        return Optional.empty();
    }

    @Transactional
    public Optional<Transaccion> delete (Long id) {
        Optional<Transaccion> op = findById(id);
        if (op.isPresent()) {
            this.transaccionRepository.delete(op.get());
            return op;
        }
        return Optional.empty();
    }

    public void realizarTransaccion(Transaccion transaccion) {
        
        BigDecimal monto = transaccion.getMonto();

        if (!transaccion.getCuentaOrigen().getEstadoCuenta().getNombre().equals("ACTIVO")) {
            throw new CuentaNoActivaException(transaccion.getCuentaOrigen().getId());
        }

        switch (transaccion.getTipoTransaccion().getNombre()) {
            case "DEPOSITO" -> transaccion.getCuentaDestino().setSaldo(
                    transaccion.getCuentaDestino().getSaldo().add(monto)
                );

            case "RETIRO" -> {
                if (monto.compareTo(transaccion.getCuentaOrigen().getSaldo()) > 0) {
                    throw new FondosInsuficientesException(transaccion.getTipoTransaccion().getNombre(), monto);
                }
                transaccion.getCuentaOrigen().setSaldo(
                        transaccion.getCuentaOrigen().getSaldo().subtract(monto)
                );
            }

            case "TRANSFERENCIA" -> {
                
                if (!transaccion.getCuentaDestino().getEstadoCuenta().getNombre().equals("ACTIVO")) {
                    throw new CuentaNoActivaException(transaccion.getCuentaOrigen().getId());
                }

                if (monto.compareTo(transaccion.getCuentaOrigen().getSaldo()) > 0) {
                    throw new FondosInsuficientesException(transaccion.getTipoTransaccion().getNombre(), monto);
                }
                transaccion.getCuentaOrigen().setSaldo(
                        transaccion.getCuentaOrigen().getSaldo().subtract(monto)
                );
                transaccion.getCuentaDestino().setSaldo(
                        transaccion.getCuentaDestino().getSaldo().add(monto)
                );
            }

            default -> throw new RuntimeException("Tipo de transacción no soportado");
        }
    }

    public CuentaDAO parsearCuentaDAO (Cuenta cuenta) {
        CuentaDAO cuentaDAO = new CuentaDAO();
        cuentaDAO.setId(cuenta.getId());
        cuentaDAO.setSaldo(cuenta.getSaldo());
        cuentaDAO.setFechaCreacion(cuenta.getFechaCreacion());
        cuentaDAO.setUsuarioId(cuenta.getUsuario().getId());
        cuentaDAO.setEstadoCuentaId(cuenta.getEstadoCuenta().getId());
        return cuentaDAO;
    }

    
}
