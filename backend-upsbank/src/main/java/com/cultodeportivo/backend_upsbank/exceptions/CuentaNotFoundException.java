package com.cultodeportivo.backend_upsbank.exceptions;

public class CuentaNotFoundException extends RuntimeException {
    public CuentaNotFoundException(Long id) {
        super("No se encontro la cuenta con id: " + id);
    }
    
}
