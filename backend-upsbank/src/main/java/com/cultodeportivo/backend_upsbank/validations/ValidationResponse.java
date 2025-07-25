package com.cultodeportivo.backend_upsbank.validations;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public class ValidationResponse {
    
    public static ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), "El campo " + error.getField() + ": " + error.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errors);
    }
}
