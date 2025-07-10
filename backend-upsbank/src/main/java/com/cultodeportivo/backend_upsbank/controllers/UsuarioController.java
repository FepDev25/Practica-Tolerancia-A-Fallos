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

import com.cultodeportivo.backend_upsbank.models.LoginRequest;
import com.cultodeportivo.backend_upsbank.models.Usuario;
import com.cultodeportivo.backend_upsbank.services.UsuarioService;
import com.cultodeportivo.backend_upsbank.validations.UserValidation;
import static com.cultodeportivo.backend_upsbank.validations.ValidationResponse.validation;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UserValidation userValidation;

    public UsuarioController(UsuarioService usuarioService, UserValidation userValidation) {
        this.usuarioService = usuarioService;
        this.userValidation = userValidation;
    }

    @GetMapping()
    public List<Usuario> getUsuarios() {
        return this.usuarioService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUsuariosById(@PathVariable Long id) {
        return ResponseEntity.ok(this.usuarioService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<?> postMethodName(@Valid @RequestBody Usuario user, BindingResult result) {
        this.userValidation.validate(user, result);
        if (result.hasFieldErrors()) {
            return validation(result); 
        }

        return ResponseEntity.ok(this.usuarioService.saveUser(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putMethodName(@Valid @RequestBody Usuario user, BindingResult result, @PathVariable Long id) {
        this.userValidation.validate(user, result);
        if (result.hasFieldErrors()) {
            return validation(result); 
        }

        Optional<Usuario> op = this.usuarioService.updateUser(id, user);
        return op.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());   
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMethodName(@PathVariable Long id) {
        Optional<Usuario> op = this.usuarioService.delete(id);
        return op.map( r -> ResponseEntity.ok(id))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<Usuario> usuario = usuarioService.login(loginRequest.getCorreo(), loginRequest.getContrasena());
        return usuario.map(u -> ResponseEntity.ok("Login correcto. Bienvenido " + u.getCorreo()))
                    .orElseGet(() -> ResponseEntity.status(401).body("Credenciales incorrectas"));
    }

    
}
