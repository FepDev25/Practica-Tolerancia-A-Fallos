package com.cultodeportivo.backend_upsbank.exceptions;

public class CuentaNoActivaException extends RuntimeException {

    public CuentaNoActivaException(Long id) {
        super("Cuenta no activa: " + id);
    }
}
