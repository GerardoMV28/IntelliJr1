package com.proyecto.usuarios.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

/**
 * DTO para recibir criterios combinables de búsqueda dinámica y opciones de paginación.
 * Permite buscar usuarios por coincidencia parcial o exacta según el campo,
 * ignorando automáticamente los campos nulos o vacíos.
 */
@Schema(description = "Criterios opcionales para búsqueda dinámica de usuarios")
public class UsuarioBusquedaRequest {

    @Schema(description = "Filtro por coincidencia parcial en el nombre", example = "Juan")
    private String nombre;

    @Schema(description = "Filtro por coincidencia en el correo electrónico", example = "juan.perez@correo.com")
    private String correo;

    @Schema(description = "Filtro por número o parte del teléfono", example = "477")
    private String telefono;

    @Schema(description = "Filtro por estado de actividad (true/false)", example = "true")
    private Boolean activo;

    @Schema(description = "Número de página a consultar (inicia en 0)", example = "0", defaultValue = "0")
    @Min(value = 0, message = "La página no puede ser menor a 0")
    private Integer pagina = 0;

    @Schema(description = "Cantidad de elementos por página (entre 1 y 100)", example = "10", defaultValue = "10")
    @Min(value = 1, message = "El tamaño de página debe ser al menos 1")
    @Max(value = 100, message = "El tamaño de página no puede exceder 100")
    private Integer tamanio = 10;

    @Schema(description = "Campo por el cual ordenar los resultados", example = "id", defaultValue = "id")
    private String ordenarPor = "id";

    @Schema(description = "Dirección de ordenamiento: ASC o DESC", example = "ASC", defaultValue = "ASC")
    private String direccion = "ASC";

    public UsuarioBusquedaRequest() {
    }

    public UsuarioBusquedaRequest(String nombre, String correo, String telefono, Boolean activo) {
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.activo = activo;
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

    public Integer getPagina() {
        return pagina != null ? pagina : 0;
    }

    public void setPagina(Integer pagina) {
        this.pagina = pagina;
    }

    public Integer getTamanio() {
        return (tamanio != null && tamanio > 0) ? tamanio : 10;
    }

    public void setTamanio(Integer tamanio) {
        this.tamanio = tamanio;
    }

    public String getOrdenarPor() {
        return (ordenarPor != null && !ordenarPor.isBlank()) ? ordenarPor : "id";
    }

    public void setOrdenarPor(String ordenarPor) {
        this.ordenarPor = ordenarPor;
    }

    public String getDireccion() {
        return (direccion != null && direccion.equalsIgnoreCase("DESC")) ? "DESC" : "ASC";
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
