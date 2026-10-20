package com.proyecto.usuarios.exception;

/**
 * Excepción lanzada al intentar registrar o modificar un usuario con un correo ya existente.
 */
public class CorreoDuplicadoException extends NegocioException {

    public static final String CODIGO = "CORREO_DUPLICADO";
    public static final String MENSAJE_DEFAULT = "El correo ya se encuentra registrado. Intente con otro correo.";

    public CorreoDuplicadoException() {
        super(CODIGO, MENSAJE_DEFAULT);
    }

    public CorreoDuplicadoException(String mensaje) {
        super(CODIGO, mensaje);
    }
}
