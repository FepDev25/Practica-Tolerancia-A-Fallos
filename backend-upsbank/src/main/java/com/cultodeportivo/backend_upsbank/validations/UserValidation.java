package com.cultodeportivo.backend_upsbank.validations;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.cultodeportivo.backend_upsbank.models.Usuario;

@Component
@SuppressWarnings("null") 
public class UserValidation implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Usuario.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Usuario user = (Usuario) target;

        if (user.getNombre() == null || user.getNombre().isBlank()) {
            errors.rejectValue("nombre", null, "El usuario debe tener un nombre");
        } else if (user.getNombre().length() < 2 || user.getNombre().length() > 50) {
            errors.rejectValue("nombre", null, "El nombre debe tener entre 2 y 50 caracteres");
        }

        if (user.getContrasena() == null || user.getContrasena().isBlank()) {
            errors.rejectValue("contrasena", "", "El usuario debe tener una contraseña");
        } else if (user.getContrasena().length() < 4 || user.getContrasena().length() > 20) {
            errors.rejectValue("contrasena", null, "La contraseña debe tener entre 4 y 20 caracteres");
        }

        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        if (user.getCorreo() == null || user.getCorreo().isBlank()) {
            errors.rejectValue("correo", "", "El usuario debe tener un correo");
        } else if (!user.getCorreo().matches(regex)) {
            errors.rejectValue("correo", "", "El correo debe ser válido");
        }

    }
    
}
