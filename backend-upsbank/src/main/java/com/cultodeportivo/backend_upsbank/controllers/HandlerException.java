package com.cultodeportivo.backend_upsbank.controllers;

import java.util.Date;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cultodeportivo.backend_upsbank.exceptions.CuentaNotFoundException;
import com.cultodeportivo.backend_upsbank.exceptions.EstadoCuentaNotFoundException;
import com.cultodeportivo.backend_upsbank.exceptions.UserNotFoundException;
import com.cultodeportivo.backend_upsbank.exceptions.TipoTransaccionNotFoundException;
import com.cultodeportivo.backend_upsbank.exceptions.CuentaNoActivaException;
import com.cultodeportivo.backend_upsbank.exceptions.FondosInsuficientesException;


@RestControllerAdvice
public class HandlerException {
    
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> userNotFound(Exception ex) {
        return Map.of(
            "message", ex.getMessage(),
            "error", "El usuario no existe",
            "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "date", new Date()
        );
    }

    @ExceptionHandler(EstadoCuentaNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> estadoCuentaNotFound(Exception ex) {
        return Map.of(
            "message", ex.getMessage(),
            "error", "El estado de la cuenta no existe",
            "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "date", new Date()
        );
    }

    @ExceptionHandler(CuentaNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> cuentaNotFound(Exception ex) {
        return Map.of(
            "message", ex.getMessage(),
            "error", "La cuenta no existe",
            "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "date", new Date()
        );
    }

    @ExceptionHandler(TipoTransaccionNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> tipoTransaccionNotFound(Exception ex) {
        return Map.of(
            "message", ex.getMessage(),
            "error", "El tipo de transaccion no existe",
            "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "date", new Date()
        );
    }

    @ExceptionHandler(CuentaNoActivaException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> cuentaNoActiva(Exception ex) {
        return Map.of(
            "message", ex.getMessage(),
            "error", "La cuenta no esta activa",
            "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "date", new Date()
        );
    }

    @ExceptionHandler(FondosInsuficientesException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> fondosInsuficientes(Exception ex) {
        return Map.of(
            "message", ex.getMessage(),
            "error", "Fondos insuficientes",
            "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "date", new Date()
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> fechaMalFormato(Exception ex) {
        return Map.of(
            "message", ex.getMessage(),
            "error", "El formato de la fecha es incorrecto. Ej: 2025-12-31T00:00:00",
            "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "date", new Date()
        );
    }

}
