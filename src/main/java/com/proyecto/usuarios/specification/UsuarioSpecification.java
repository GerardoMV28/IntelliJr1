package com.proyecto.usuarios.specification;

import com.proyecto.usuarios.dto.request.UsuarioBusquedaRequest;
import com.proyecto.usuarios.entity.Usuario;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de construir dinámicamente las condiciones de búsqueda para Spring Data JPA.
 * Evita concatenar consultas SQL manualmente y previene inyecciones SQL.
 */
public class UsuarioSpecification {

    private UsuarioSpecification() {
        // Constructor privado para evitar instanciación
    }

    /**
     * Construye una Specification dinámica combinando únicamente los criterios que el cliente proporcionó.
     * Excluye automáticamente los campos nulos o con cadenas en blanco.
     */
    public static Specification<Usuario> conFiltros(UsuarioBusquedaRequest request) {
        return (root, query, cb) -> {
            if (request == null) {
                return cb.conjunction();
            }

            List<Predicate> predicados = new ArrayList<>();

            // 1. Filtro por nombre (coincidencia parcial insensible a mayúsculas/minúsculas)
            if (request.getNombre() != null && !request.getNombre().isBlank()) {
                String patronNombre = "%" + request.getNombre().trim().toLowerCase() + "%";
                predicados.add(cb.like(cb.lower(root.get("nombre")), patronNombre));
            }

            // 2. Filtro por correo (coincidencia parcial insensible a mayúsculas/minúsculas)
            if (request.getCorreo() != null && !request.getCorreo().isBlank()) {
                String patronCorreo = "%" + request.getCorreo().trim().toLowerCase() + "%";
                predicados.add(cb.like(cb.lower(root.get("correo")), patronCorreo));
            }

            // 3. Filtro por teléfono (coincidencia parcial)
            if (request.getTelefono() != null && !request.getTelefono().isBlank()) {
                String patronTelefono = "%" + request.getTelefono().trim() + "%";
                predicados.add(cb.like(root.get("telefono"), patronTelefono));
            }

            // 4. Filtro por estado activo (coincidencia booleana exacta)
            if (request.getActivo() != null) {
                predicados.add(cb.equal(root.get("activo"), request.getActivo()));
            }

            // Combina todas las condiciones con AND (o retorna todos si no se especificaron filtros)
            return cb.and(predicados.toArray(new Predicate[0]));
        };
    }
}
