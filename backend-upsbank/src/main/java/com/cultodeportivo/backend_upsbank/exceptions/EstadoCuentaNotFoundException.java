package com.cultodeportivo.backend_upsbank.exceptions;

public class EstadoCuentaNotFoundException extends RuntimeException {
    
    public EstadoCuentaNotFoundException(Long id) {
        super("No se encontro el estado de la cuenta con id: " + id);
    }
    
}
