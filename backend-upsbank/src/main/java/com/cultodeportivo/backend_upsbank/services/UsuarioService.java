package com.cultodeportivo.backend_upsbank.services;

import java.util.List;
import java.util.Optional;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cultodeportivo.backend_upsbank.exceptions.UserNotFoundException;
import com.cultodeportivo.backend_upsbank.models.Usuario;
import com.cultodeportivo.backend_upsbank.repositories.UsuarioRepository;

@Service
public class UsuarioService {
    
    private final UsuarioRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UsuarioService(UsuarioRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return this.userRepository.findAll();
    }


    @Retryable(
        value = { Exception.class },
        maxAttempts = 3,
        backoff = @Backoff(delay = 2000)
    )
    @Transactional(readOnly = true)
    public Usuario findById(Long id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }


    @Retryable(
        value = { Exception.class },
        maxAttempts = 3,
        backoff = @Backoff(delay = 2000)
    )
    @Transactional
    public Usuario saveUser(Usuario user) {
        user.setContrasena(passwordEncoder.encode(user.getContrasena()));
        return this.userRepository.save(user);
    }

    @Retryable(
        value = { Exception.class },
        maxAttempts = 3,
        backoff = @Backoff(delay = 2000)
    )
    @Transactional
    public Optional<Usuario> updateUser (Long id, Usuario user){
        Usuario u = this.findById(id);
        u.setNombre(user.getNombre());
        u.setCorreo(user.getCorreo());
        u.setContrasena(passwordEncoder.encode(user.getContrasena()));
        return Optional.of(this.userRepository.save(u));
    }

    @Retryable(
        value = { Exception.class },
        maxAttempts = 3,
        backoff = @Backoff(delay = 2000)
    )
    @Transactional
    public Optional<Usuario> delete (Long id) {
        Optional<Usuario> op = userRepository.findById(id);
        if (op.isPresent()) {
            this.userRepository.delete(op.get());
            return op;
        }
        return Optional.empty();
    }

    @Retryable(
        value = { Exception.class },
        maxAttempts = 3,
        backoff = @Backoff(delay = 2000)
    )
    @Transactional(readOnly = true)
    public Optional<Usuario> login(String correo, String contrasena) {
        Optional<Usuario> usuario = userRepository.findByCorreo(correo);

        if (usuario.isPresent()) {
            boolean passwordMatches = passwordEncoder.matches(contrasena, usuario.get().getContrasena());
            if (passwordMatches) {
                return usuario;
            }
        }

        return Optional.empty();
    }


}