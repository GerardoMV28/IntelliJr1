package com.proyecto.usuarios.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO para la consulta de un usuario mediante llave compuesta (nombre + correo).
 * Hereda las validaciones y campos de UsuarioIdentificadorRequest.
 */
@Schema(description = "Solicitud de consulta de usuario por llave compuesta (nombre y correo)")
public class UsuarioConsultaRequest extends UsuarioIdentificadorRequest {

    public UsuarioConsultaRequest() {
        super();
    }

    public UsuarioConsultaRequest(String nombre, String correo) {
        super(nombre, correo);
    }
}
