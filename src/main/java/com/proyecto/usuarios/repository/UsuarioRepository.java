package com.proyecto.usuarios.repository;

import com.proyecto.usuarios.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio Spring Data JPA para la entidad Usuario.
 * Incluye soporte para operaciones CRUD y búsquedas dinámicas mediante JpaSpecificationExecutor.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>, JpaSpecificationExecutor<Usuario> {

    /**
     * Localiza un usuario utilizando la llave compuesta formada por nombre y correo electrónico.
     * Equivale conceptualmente a: SELECT * FROM usuarios WHERE nombre = :nombre AND correo = :correo
     */
    Optional<Usuario> findByNombreAndCorreo(String nombre, String correo);

    /**
     * Verifica si ya existe un usuario registrado con el correo especificado.
     */
    boolean existsByCorreo(String correo);

    /**
     * Verifica si existe otro usuario con el correo especificado, excluyendo el ID actual (para actualizaciones).
     */
    boolean existsByCorreoAndIdNot(String correo, Long id);
}
