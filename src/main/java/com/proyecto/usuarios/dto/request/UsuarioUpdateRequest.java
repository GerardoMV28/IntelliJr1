package com.proyecto.usuarios.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO para la actualización parcial de un usuario.
 * Utiliza la llave compuesta (nombre y correo) heredada para localizar el registro,
 * y permite actualizar campos como teléfono, estado (activo), nuevo nombre o nuevo correo.
 */
@Schema(description = "Datos para la actualización parcial de un usuario mediante llave compuesta")
public class UsuarioUpdateRequest extends UsuarioIdentificadorRequest {

    @Schema(description = "Nuevo número de teléfono (opcional, debe contener 10 dígitos si se proporciona)", example = "4779876543")
    @Pattern(regexp = "^[0-9]{10}$", message = "El teléfono debe contener exactamente 10 dígitos.")
    private String telefono;

    @Schema(description = "Nuevo estado de actividad del usuario (opcional)", example = "true")
    private Boolean activo;

    @Schema(description = "Nuevo nombre para el usuario en caso de modificación (opcional)", example = "Juan Carlos Pérez")
    @Size(min = 2, max = 100, message = "El nuevo nombre debe tener entre 2 y 100 caracteres.")
    private String nuevoNombre;

    @Schema(description = "Nuevo correo electrónico en caso de modificación (opcional, debe ser único)", example = "juan.nuevo@correo.com")
    @Email(message = "El nuevo correo electrónico no tiene un formato válido.")
    @Size(max = 150, message = "El nuevo correo electrónico no debe superar los 150 caracteres.")
    private String nuevoCorreo;

    public UsuarioUpdateRequest() {
        super();
    }

    public UsuarioUpdateRequest(String nombre, String correo, String telefono, Boolean activo) {
        super(nombre, correo);
        this.telefono = telefono;
        this.activo = activo;
    }

    public UsuarioUpdateRequest(String nombre, String correo, String telefono, Boolean activo, String nuevoNombre, String nuevoCorreo) {
        super(nombre, correo);
        this.telefono = telefono;
        this.activo = activo;
        this.nuevoNombre = nuevoNombre;
        this.nuevoCorreo = nuevoCorreo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getNuevoNombre() {
        return nuevoNombre;
    }

    public void setNuevoNombre(String nuevoNombre) {
        this.nuevoNombre = nuevoNombre;
    }

    public String getNuevoCorreo() {
        return nuevoCorreo;
    }

    public void setNuevoCorreo(String nuevoCorreo) {
        this.nuevoCorreo = nuevoCorreo;
    }
}
