package com.cultodeportivo.backend_upsbank.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cultodeportivo.backend_upsbank.dao.CuentaDAO;
import com.cultodeportivo.backend_upsbank.models.Cuenta;
import com.cultodeportivo.backend_upsbank.models.EstadoCuenta;
import com.cultodeportivo.backend_upsbank.services.CuentaService;
import com.cultodeportivo.backend_upsbank.services.EstadoCuentaService;
import com.cultodeportivo.backend_upsbank.validations.CuentaValidation;
import static com.cultodeportivo.backend_upsbank.validations.ValidationResponse.validation;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cuenta")
@CrossOrigin(origins = "http://localhost:4200", originPatterns = "*")
public class CuentaController {

    private final CuentaService cuentaService;
    private final EstadoCuentaService estadoCuentaService;
    private final CuentaValidation cuentaValidation;

    public CuentaController(CuentaService cuentaService, EstadoCuentaService estadoCuentaService, CuentaValidation cuentaValidation) {
        this.cuentaService = cuentaService;
        this.estadoCuentaService = estadoCuentaService;
        this.cuentaValidation = cuentaValidation;
    }
    
    @GetMapping()
    public List<Cuenta> getAll() {
        return this.cuentaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(this.cuentaService.findById(id));
    }

    @GetMapping("/estadoCuenta")
    public List<EstadoCuenta> getAllEstadoCuenta() {
        return this.estadoCuentaService.findAll();
    }

    @GetMapping("/estadoCuenta/{id}")
    public ResponseEntity<?> getEstadoCuentaById(@PathVariable Long id) {
        Optional<EstadoCuenta> op = this.estadoCuentaService.findById(id);
        return op.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<?> post(@Valid @RequestBody CuentaDAO cuentaDAO, BindingResult result) {
        this.cuentaValidation.validate(cuentaDAO, result);
        if (result.hasFieldErrors()) {
            return validation(result); 
        }

        return ResponseEntity.ok(this.cuentaService.save(cuentaDAO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@Valid @RequestBody CuentaDAO cuentaDAO, BindingResult result, @PathVariable Long id) {
        this.cuentaValidation.validate(cuentaDAO, result);
        if (result.hasFieldErrors()) {
            return validation(result); 
        }
        
        Optional<Cuenta> cuentaActualizada = this.cuentaService.updateCuenta(id, cuentaDAO);
        return cuentaActualizada.map(ResponseEntity::ok)
                                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Cuenta> cuentaEliminada = this.cuentaService.delete(id);
        return cuentaEliminada.map(c -> ResponseEntity.ok(id))
                            .orElseGet(() -> ResponseEntity.notFound().build());
    }


}
