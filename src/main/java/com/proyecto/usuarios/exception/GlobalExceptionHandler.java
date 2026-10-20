package com.proyecto.usuarios.exception;

import com.proyecto.usuarios.dto.response.ApiErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Manejador global centralizado de excepciones mediante @RestControllerAdvice.
 * Intercepta los errores de validación, negocio, integridad y fallos del sistema,
 * devolviendo respuestas estructuradas y códigos HTTP correspondientes.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Manejo de errores de validación lanzados por Bean Validation (@Valid).
     * Devuelve HTTP 400 Bad Request con el mapa de campos con error.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> manejarValidaciones(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new LinkedHashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            // Evitamos sobreescribir si ya existe un error para el campo, preservando el primero
            errores.putIfAbsent(error.getField(), error.getDefaultMessage());
        }

        ApiErrorResponse respuesta = new ApiErrorResponse(
                "DATOS_INVALIDOS",
                "Verifique la información capturada e intente nuevamente.",
                errores
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }

    /**
     * Manejo de usuario no encontrado (búsqueda por llave compuesta).
     * Devuelve HTTP 404 Not Found.
     */
    @ExceptionHandler(UsuarioNoEncontradoException.class)
    public ResponseEntity<ApiErrorResponse> manejarUsuarioNoEncontrado(UsuarioNoEncontradoException ex) {
        ApiErrorResponse respuesta = new ApiErrorResponse(ex.getCodigo(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
    }

    /**
     * Manejo de intento de duplicación de correo electrónico.
     * Devuelve HTTP 409 Conflict.
     */
    @ExceptionHandler(CorreoDuplicadoException.class)
    public ResponseEntity<ApiErrorResponse> manejarCorreoDuplicado(CorreoDuplicadoException ex) {
        ApiErrorResponse respuesta = new ApiErrorResponse(ex.getCodigo(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
    }

    /**
     * Manejo de errores de integridad de base de datos (por ejemplo constraints únicos no capturados).
     * No expone nombres de tablas ni trazas SQL. Devuelve HTTP 409 Conflict.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorResponse> manejarErrorIntegridad(DataIntegrityViolationException ex) {
        log.warn("Violación de integridad de datos en la base de datos: {}", ex.getMessage());
        ApiErrorResponse respuesta = new ApiErrorResponse(
                "ERROR_INTEGRIDAD_DATOS",
                "No se pudo completar la operación debido a una restricción de datos únicos o relaciones en el sistema."
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
    }

    /**
     * Manejo de solicitudes con JSON mal formado o sintaxis inválida.
     * Devuelve HTTP 400 Bad Request.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> manejarCuerpoInvalido(HttpMessageNotReadableException ex) {
        ApiErrorResponse respuesta = new ApiErrorResponse(
                "CUERPO_PETICION_INVALIDO",
                "El cuerpo de la solicitud no contiene un formato JSON válido o los tipos de datos son incompatibles."
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }

    /**
     * Manejo general para cualquier excepción no prevista.
     * Registra en log y devuelve HTTP 500 Internal Server Error con mensaje controlado.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> manejarErrorInesperado(Exception ex) {
        log.error("Error inesperado en el servidor: ", ex);
        ApiErrorResponse respuesta = new ApiErrorResponse(
                "ERROR_INTERNO_SERVIDOR",
                "Ocurrió un error inesperado al procesar la solicitud. Por favor contacte al administrador."
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
    }
}
