package com.cultodeportivo.backend_upsbank.validations;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.cultodeportivo.backend_upsbank.dao.TransaccionDAO;

@Component
@SuppressWarnings("null") 
public class TransaccionValidation implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return TransaccionDAO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        
        TransaccionDAO transaccion = (TransaccionDAO) target;

        if (transaccion.getMonto() == null) {
            errors.rejectValue("monto", null, "La transacción debe tener un monto");
        } else if (transaccion.getMonto().compareTo(BigDecimal.ZERO) < 0) {
            errors.rejectValue("monto", null, "El monto no puede ser negativo");
        }

        if (transaccion.getFecha() == null) {
            errors.rejectValue("fecha", null, "La transacción debe tener una fecha");
        } 
    }
    
}
