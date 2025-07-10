package com.cultodeportivo.backend_upsbank.validations;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.cultodeportivo.backend_upsbank.dao.CuentaDAO;

@Component
@SuppressWarnings("null") 
public class CuentaValidation implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return CuentaDAO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        CuentaDAO cuenta = (CuentaDAO) target;

        if (cuenta.getSaldo() == null) {
            errors.rejectValue("saldo", null, "La cuenta debe tener un saldo");
        } else if (cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0) {
            errors.rejectValue("saldo", null, "El saldo no puede ser negativo");
        }

        if (cuenta.getFechaCreacion() == null) {
            errors.rejectValue("fechaCreacion", null, "El usuario debe tener una fecha de creación");
        } 
    }
    
}
