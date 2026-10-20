package com.proyecto.usuarios.dto.response;

import com.proyecto.usuarios.entity.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

/**
 * DTO de respuesta para representar la información de un usuario expuesta hacia el cliente.
 * Evita devolver directamente entidades JPA desde los controladores.
 */
@Schema(description = "Datos de respuesta de un usuario")
public class UsuarioResponse {

    @Schema(description = "Identificador único generado por la base de datos", example = "1")
    private Long id;

    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez")
    private String nombre;

    @Schema(description = "Correo electrónico del usuario", example = "juan.perez@correo.com")
    private String correo;

    @Schema(description = "Número telefónico de 10 dígitos", example = "4771234567")
    private String telefono;

    @Schema(description = "Estado de actividad del usuario en el sistema", example = "true")
    private Boolean activo;

    @Schema(description = "Fecha y hora de creación del registro en el servidor", example = "2026-10-14T10:30:00")
    private LocalDateTime fechaCreacion;

    public UsuarioResponse() {
    }

    public UsuarioResponse(Long id, String nombre, String correo, String telefono, Boolean activo, LocalDateTime fechaCreacion) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.activo = activo;
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * Método estático de fábrica para mapear desde la entidad Usuario.
     */
    public static UsuarioResponse desdeEntidad(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getTelefono(),
                usuario.getActivo(),
                usuario.getFechaCreacion()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
