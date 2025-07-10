package com.cultodeportivo.backend_upsbank.exceptions;

public class TipoTransaccionNotFoundException extends RuntimeException {

    public TipoTransaccionNotFoundException(Long id) {
        super("No se encontro el tipo de transaccion con id: " + id);
    }
    
}
