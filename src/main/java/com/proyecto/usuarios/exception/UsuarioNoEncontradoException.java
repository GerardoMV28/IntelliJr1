package com.proyecto.usuarios.exception;

/**
 * Excepción lanzada cuando no se localiza un usuario mediante la llave compuesta (nombre + correo).
 */
public class UsuarioNoEncontradoException extends NegocioException {

    public static final String CODIGO = "USUARIO_NO_ENCONTRADO";
    public static final String MENSAJE_DEFAULT = "No existe un usuario con el nombre y correo electrónico proporcionados.";

    public UsuarioNoEncontradoException() {
        super(CODIGO, MENSAJE_DEFAULT);
    }

    public UsuarioNoEncontradoException(String mensaje) {
        super(CODIGO, mensaje);
    }
}
