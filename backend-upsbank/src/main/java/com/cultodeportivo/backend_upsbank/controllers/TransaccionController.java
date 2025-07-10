package com.cultodeportivo.backend_upsbank.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cultodeportivo.backend_upsbank.dao.TransaccionDAO;
import com.cultodeportivo.backend_upsbank.models.TipoTransaccion;
import com.cultodeportivo.backend_upsbank.models.Transaccion;
import com.cultodeportivo.backend_upsbank.services.TipoTransaccionService;
import com.cultodeportivo.backend_upsbank.services.TransaccionService;
import com.cultodeportivo.backend_upsbank.validations.TransaccionValidation;
import static com.cultodeportivo.backend_upsbank.validations.ValidationResponse.validation;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/transaccion")
public class TransaccionController {

    private final TransaccionService transaccionService;
    private final TipoTransaccionService tipoTransaccionService;
    private final TransaccionValidation transaccionValidation;

    public TransaccionController(TransaccionService transaccionService, TipoTransaccionService tipoTransaccionService, TransaccionValidation transaccionValidation) {
        this.transaccionService = transaccionService;
        this.tipoTransaccionService = tipoTransaccionService;
        this.transaccionValidation = transaccionValidation;
    }

    @GetMapping()
    public List<Transaccion> get() {
        return this.transaccionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        Optional<Transaccion> op = this.transaccionService.findById(id);
        return op.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/tipoTransaccion")
    public List<TipoTransaccion> getTipoTransaccions() {
        return this.tipoTransaccionService.findAll();
    }

    @GetMapping("/tipoTransaccion/{id}")
    public ResponseEntity<?> getTipoTransaccionsById(@PathVariable Long id) {
        Optional<TipoTransaccion> op = this.tipoTransaccionService.findById(id);
        return op.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<?> post(@Valid @RequestBody TransaccionDAO transaccionDAO, BindingResult result) {
        this.transaccionValidation.validate(transaccionDAO, result);
        if (result.hasFieldErrors()) {
            return validation(result); 
        }
        return ResponseEntity.ok(this.transaccionService.save(transaccionDAO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@Valid @RequestBody TransaccionDAO transaccionDAO, BindingResult result, @PathVariable Long id) {
        this.transaccionValidation.validate(transaccionDAO, result);
        if (result.hasFieldErrors()) {
            return validation(result); 
        }
        Optional<Transaccion> op = this.transaccionService.updateTransaccion(id, transaccionDAO);
        return op.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Transaccion> transaccionEliminada = this.transaccionService.delete(id);
        return transaccionEliminada.map(t -> ResponseEntity.ok(id))
                                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
}
