package com.cultodeportivo.backend_upsbank.exceptions;

import java.math.BigDecimal;

public class FondosInsuficientesException extends RuntimeException {

    public FondosInsuficientesException(String tipo, BigDecimal monto) {
        super("Fondos insuficientes para realizar " + tipo + " de $" + monto);
    }
    
}
