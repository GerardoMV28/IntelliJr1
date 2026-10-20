package com.proyecto.usuarios.service;

import com.proyecto.usuarios.dto.request.UsuarioBusquedaRequest;
import com.proyecto.usuarios.dto.request.UsuarioConsultaRequest;
import com.proyecto.usuarios.dto.request.UsuarioCreateRequest;
import com.proyecto.usuarios.dto.request.UsuarioEliminacionRequest;
import com.proyecto.usuarios.dto.request.UsuarioUpdateRequest;
import com.proyecto.usuarios.dto.response.UsuarioResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Interfaz de la capa de servicio para la administración de usuarios.
 * Aplica el principio de desacoplamiento y polimorfismo mediante interfaces.
 */
public interface UsuarioService {

    /**
     * Registra un nuevo usuario en la base de datos validando la unicidad del correo.
     */
    UsuarioResponse crearUsuario(UsuarioCreateRequest request);

    /**
     * Consulta el listado completo de usuarios registrados.
     */
    List<UsuarioResponse> obtenerTodos();

    /**
     * Consulta el listado de usuarios con soporte de paginación.
     */
    Page<UsuarioResponse> obtenerTodosPaginado(Pageable pageable);

    /**
     * Consulta un usuario específico mediante la llave compuesta (nombre + correo).
     */
    UsuarioResponse consultarPorLlaveCompuesta(UsuarioConsultaRequest request);

    /**
     * Modifica parcialmente la información de un usuario identificado por su llave compuesta.
     */
    UsuarioResponse actualizarUsuario(UsuarioUpdateRequest request);

    /**
     * Elimina a un usuario localizado mediante su llave compuesta.
     */
    void eliminarUsuario(UsuarioEliminacionRequest request);

    /**
     * Realiza búsquedas dinámicas combinando diferentes criterios mediante JPA Specifications.
     */
    Page<UsuarioResponse> buscarUsuariosDinamico(UsuarioBusquedaRequest request);
}
