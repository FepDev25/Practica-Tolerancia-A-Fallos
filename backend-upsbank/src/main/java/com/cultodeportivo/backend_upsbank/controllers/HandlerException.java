package com.cultodeportivo.backend_upsbank.controllers;

import java.util.Date;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cultodeportivo.backend_upsbank.exceptions.CuentaNoActivaException;
import com.cultodeportivo.backend_upsbank.exceptions.CuentaNotFoundException;
import com.cultodeportivo.backend_upsbank.exceptions.EstadoCuentaNotFoundException;
import com.cultodeportivo.backend_upsbank.exceptions.FondosInsuficientesException;
import com.cultodeportivo.backend_upsbank.exceptions.TipoTransaccionNotFoundException;
import com.cultodeportivo.backend_upsbank.exceptions.UserNotFoundException;


@RestControllerAdvice
@CrossOrigin(origins = "http://localhost:4200", originPatterns = "*")
public class HandlerException {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> userNotFound(Exception ex) {
        return buildErrorResponse(ex, "El usuario no existe", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EstadoCuentaNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> estadoCuentaNotFound(Exception ex) {
        return buildErrorResponse(ex, "El estado de la cuenta no existe", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CuentaNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> cuentaNotFound(Exception ex) {
        return buildErrorResponse(ex, "La cuenta no existe", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TipoTransaccionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> tipoTransaccionNotFound(Exception ex) {
        return buildErrorResponse(ex, "El tipo de transacción no existe", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CuentaNoActivaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> cuentaNoActiva(Exception ex) {
        return buildErrorResponse(ex, "La cuenta no está activa", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FondosInsuficientesException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> fondosInsuficientes(Exception ex) {
        return buildErrorResponse(ex, "Fondos insuficientes", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> fechaMalFormato(Exception ex) {
        return buildErrorResponse(ex, "El formato de la fecha es incorrecto. Ej: 2025-12-31T00:00:00", HttpStatus.BAD_REQUEST);
    }

    private Map<String, Object> buildErrorResponse(Exception ex, String error, HttpStatus status) {
        return Map.of(
            "message", ex.getMessage(),
            "error", error,
            "status", status.value(),
            "date", new Date()
        );
    }
}
