package com.proyecto.usuarios.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Envoltorio consistente para las respuestas estándar exitosas de la API REST.
 * Permite devolver mensajes y datos tipados.
 */
@Schema(description = "Respuesta estándar de la API")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    @Schema(description = "Mensaje informativo sobre el resultado de la operación", example = "Operación realizada exitosamente.")
    private String mensaje;

    @Schema(description = "Cuerpo de datos devueltos por el servicio")
    private T datos;

    public ApiResponse() {
    }

    public ApiResponse(String mensaje, T datos) {
        this.mensaje = mensaje;
        this.datos = datos;
    }

    public static <T> ApiResponse<T> de(String mensaje, T datos) {
        return new ApiResponse<>(mensaje, datos);
    }

    public static <T> ApiResponse<T> soloMensaje(String mensaje) {
        return new ApiResponse<>(mensaje, null);
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public T getDatos() {
        return datos;
    }

    public void setDatos(T datos) {
        this.datos = datos;
    }
}
