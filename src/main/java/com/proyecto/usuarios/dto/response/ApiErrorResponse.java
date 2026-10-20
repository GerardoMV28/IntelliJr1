package com.proyecto.usuarios.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map;

/**
 * DTO para estandarizar las respuestas de error capturadas por el GlobalExceptionHandler.
 * Cumple con los formatos exactos de error solicitados en la especificación.
 */
@Schema(description = "Estructura estandarizada de errores de la API")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse {

    @Schema(description = "Código de error de negocio o validación", example = "DATOS_INVALIDOS")
    private String codigo;

    @Schema(description = "Descripción entendible del problema", example = "Verifique la información capturada e intente nuevamente.")
    private String mensaje;

    @Schema(description = "Mapa de campos específicos con error de validación y su motivo")
    private Map<String, String> errores;

    public ApiErrorResponse() {
    }

    public ApiErrorResponse(String codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public ApiErrorResponse(String codigo, String mensaje, Map<String, String> errores) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.errores = errores;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Map<String, String> getErrores() {
        return errores;
    }

    public void setErrores(Map<String, String> errores) {
        this.errores = errores;
    }
}
