package com.cultodeportivo.backend_upsbank.dao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CuentaDAO {

    private Long id;
    private BigDecimal saldo;
    private LocalDateTime fechaCreacion;
    private Long usuarioId;
    private Long estadoCuentaId;

    public CuentaDAO(Long id, BigDecimal saldo, LocalDateTime fechaCreacion, Long usuarioId, Long estadoCuentaId) {
        this.id = id;
        this.saldo = saldo;
        this.fechaCreacion = fechaCreacion;
        this.usuarioId = usuarioId;
        this.estadoCuentaId = estadoCuentaId;
    }

    public CuentaDAO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getEstadoCuentaId() {
        return estadoCuentaId;
    }

    public void setEstadoCuentaId(Long estadoCuentaId) {
        this.estadoCuentaId = estadoCuentaId;
    }

    
    
}
