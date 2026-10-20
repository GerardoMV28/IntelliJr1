package com.proyecto.usuarios.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Entidad principal Usuario mapeada a la tabla 'usuarios'.
 * Hereda de la clase abstracta BaseEntity.
 */
@Entity
@Table(
    name = "usuarios",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_usuarios_correo", columnNames = "correo")
    }
)
public class Usuario extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, unique = true, length = 150)
    private String correo;

    @Column(nullable = false, length = 15)
    private String telefono;

    @Column(nullable = false)
    private Boolean activo;

    public Usuario() {
        super();
        this.activo = true; // Valor predeterminado
    }

    public Usuario(String nombre, String correo, String telefono, Boolean activo) {
        super();
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.activo = activo != null ? activo : true;
    }

    public Usuario(Long id, LocalDateTime fechaCreacion, String nombre, String correo, String telefono, Boolean activo) {
        super(id, fechaCreacion);
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.activo = activo != null ? activo : true;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(getId(), usuario.getId()) &&
               Objects.equals(correo, usuario.correo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), correo);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + getId() +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", telefono='" + telefono + '\'' +
                ", activo=" + activo +
                ", fechaCreacion=" + getFechaCreacion() +
                '}';
    }
}
