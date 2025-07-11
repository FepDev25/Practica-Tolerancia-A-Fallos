package com.cultodeportivo.backend_upsbank.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cultodeportivo.backend_upsbank.models.Cuenta;
import com.cultodeportivo.backend_upsbank.models.Usuario;

@Service
public class RegistroService {

    private final UsuarioService usuarioService;
    private final CuentaService cuentaService;

    public RegistroService(UsuarioService usuarioService, CuentaService cuentaService) {
        this.usuarioService = usuarioService;
        this.cuentaService = cuentaService;
    }

    @Transactional
    public Cuenta registrarUsuarioConCuenta(Usuario user) {
        Usuario userSave = usuarioService.saveUser(user);
        Cuenta cuentaSave = cuentaService.crearCuentaParaUsuario(userSave.getId());
        return cuentaSave;
    }
}
