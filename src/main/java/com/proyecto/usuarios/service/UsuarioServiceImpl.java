package com.proyecto.usuarios.service;

import com.proyecto.usuarios.dto.request.UsuarioBusquedaRequest;
import com.proyecto.usuarios.dto.request.UsuarioConsultaRequest;
import com.proyecto.usuarios.dto.request.UsuarioCreateRequest;
import com.proyecto.usuarios.dto.request.UsuarioEliminacionRequest;
import com.proyecto.usuarios.dto.request.UsuarioUpdateRequest;
import com.proyecto.usuarios.dto.response.UsuarioResponse;
import com.proyecto.usuarios.entity.Usuario;
import com.proyecto.usuarios.exception.CorreoDuplicadoException;
import com.proyecto.usuarios.exception.UsuarioNoEncontradoException;
import com.proyecto.usuarios.repository.UsuarioRepository;
import com.proyecto.usuarios.specification.UsuarioSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación de la lógica de negocio para la administración de usuarios.
 * Mantiene la separación de responsabilidades y centraliza las reglas de negocio.
 */
@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UsuarioResponse crearUsuario(UsuarioCreateRequest request) {
        String correoLimpio = request.getCorreo().trim();

        // 1. Validar que el correo no se encuentre registrado previamente
        if (usuarioRepository.existsByCorreo(correoLimpio)) {
            throw new CorreoDuplicadoException();
        }

        // 2. Mapear DTO a Entidad. El servidor controla 'id', 'activo' y 'fechaCreacion'
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(request.getNombre().trim());
        nuevoUsuario.setCorreo(correoLimpio);
        nuevoUsuario.setTelefono(request.getTelefono().trim());
        nuevoUsuario.setActivo(true); // Estado inicial activo controlado por el servidor

        Usuario guardado = usuarioRepository.save(nuevoUsuario);

        // 3. Retornar información mediante Response DTO
        return UsuarioResponse.desdeEntidad(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponse> obtenerTodos() {
        return usuarioRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream()
                .map(UsuarioResponse::desdeEntidad)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioResponse> obtenerTodosPaginado(Pageable pageable) {
        return usuarioRepository.findAll(pageable)
                .map(UsuarioResponse::desdeEntidad);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponse consultarPorLlaveCompuesta(UsuarioConsultaRequest request) {
        Usuario usuario = localizarPorLlaveCompuesta(request.getNombre(), request.getCorreo());
        return UsuarioResponse.desdeEntidad(usuario);
    }

    @Override
    public UsuarioResponse actualizarUsuario(UsuarioUpdateRequest request) {
        // 1. Localizar usuario mediante la llave compuesta (nombre + correo)
        Usuario usuario = localizarPorLlaveCompuesta(request.getNombre(), request.getCorreo());

        // 2. Si se desea actualizar el correo, validar unicidad
        if (request.getNuevoCorreo() != null && !request.getNuevoCorreo().isBlank()) {
            String nuevoCorreoLimpio = request.getNuevoCorreo().trim();
            if (!nuevoCorreoLimpio.equalsIgnoreCase(usuario.getCorreo())) {
                if (usuarioRepository.existsByCorreoAndIdNot(nuevoCorreoLimpio, usuario.getId())) {
                    throw new CorreoDuplicadoException();
                }
                usuario.setCorreo(nuevoCorreoLimpio);
            }
        }

        // 3. Modificar únicamente los campos proporcionados (evitando sobrescribir con null de forma accidental)
        if (request.getNuevoNombre() != null && !request.getNuevoNombre().isBlank()) {
            usuario.setNombre(request.getNuevoNombre().trim());
        }

        if (request.getTelefono() != null && !request.getTelefono().isBlank()) {
            usuario.setTelefono(request.getTelefono().trim());
        }

        if (request.getActivo() != null) {
            usuario.setActivo(request.getActivo());
        }

        Usuario actualizado = usuarioRepository.save(usuario);
        return UsuarioResponse.desdeEntidad(actualizado);
    }

    @Override
    public void eliminarUsuario(UsuarioEliminacionRequest request) {
        // 1. Localizar usuario mediante la llave compuesta
        Usuario usuario = localizarPorLlaveCompuesta(request.getNombre(), request.getCorreo());

        // 2. Eliminación física en la base de datos
        usuarioRepository.delete(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioResponse> buscarUsuariosDinamico(UsuarioBusquedaRequest request) {
        // Construir paginación y ordenamiento a partir de los datos recibidos en el Request DTO
        Sort sort = Sort.by(
                "DESC".equalsIgnoreCase(request.getDireccion()) ? Sort.Direction.DESC : Sort.Direction.ASC,
                request.getOrdenarPor()
        );

        Pageable pageable = PageRequest.of(request.getPagina(), request.getTamanio(), sort);

        // Ejecutar consulta dinámica utilizando JPA Specification
        return usuarioRepository.findAll(UsuarioSpecification.conFiltros(request), pageable)
                .map(UsuarioResponse::desdeEntidad);
    }

    /**
     * Método auxiliar privado para buscar un usuario por su llave compuesta (nombre + correo).
     * Lanza UsuarioNoEncontradoException si no existe coincidencia exacta.
     */
    private Usuario localizarPorLlaveCompuesta(String nombre, String correo) {
        if (nombre == null || correo == null) {
            throw new UsuarioNoEncontradoException();
        }

        return usuarioRepository.findByNombreAndCorreo(nombre.trim(), correo.trim())
                .orElseThrow(UsuarioNoEncontradoException::new);
    }
}
