package com.proyecto.usuarios.exception;

/**
 * Excepción base para los errores de lógica de negocio del sistema.
 * Permite el manejo polimórfico de excepciones en la jerarquía de errores.
 */
public abstract class NegocioException extends RuntimeException {

    private final String codigo;

    public NegocioException(String codigo, String mensaje) {
        super(mensaje);
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
}
