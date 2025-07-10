package com.cultodeportivo.backend_upsbank.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cultodeportivo.backend_upsbank.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByCorreo(String correo);
    
}
