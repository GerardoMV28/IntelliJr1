package com.proyecto.usuarios.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO para la eliminación de un usuario mediante llave compuesta (nombre + correo).
 * Hereda las validaciones y campos de UsuarioIdentificadorRequest.
 */
@Schema(description = "Solicitud de eliminación de usuario por llave compuesta (nombre y correo)")
public class UsuarioEliminacionRequest extends UsuarioIdentificadorRequest {

    public UsuarioEliminacionRequest() {
        super();
    }

    public UsuarioEliminacionRequest(String nombre, String correo) {
        super(nombre, correo);
    }
}
