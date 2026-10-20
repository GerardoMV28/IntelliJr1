package com.proyecto.usuarios.controller;

import com.proyecto.usuarios.dto.request.UsuarioBusquedaRequest;
import com.proyecto.usuarios.dto.request.UsuarioConsultaRequest;
import com.proyecto.usuarios.dto.request.UsuarioCreateRequest;
import com.proyecto.usuarios.dto.request.UsuarioEliminacionRequest;
import com.proyecto.usuarios.dto.request.UsuarioUpdateRequest;
import com.proyecto.usuarios.dto.response.ApiErrorResponse;
import com.proyecto.usuarios.dto.response.ApiResponse;
import com.proyecto.usuarios.dto.response.UsuarioResponse;
import com.proyecto.usuarios.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador REST para la gestión de usuarios.
 * Cumple estrictamente con las siguientes restricciones de la especificación:
 * 1. Cero uso de @PathVariable.
 * 2. Cero uso de @RequestParam.
 * 3. Entrada de datos exclusivamente mediante @RequestBody y Request DTOs con @Valid.
 * 4. Salida exclusiva mediante Response DTOs (nunca entidades JPA directas).
 * 5. Ausencia total de lógica de negocio o bloques try-catch (delegado a Service y GlobalExceptionHandler).
 */
@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuarios", description = "Servicios REST para la administración y búsqueda de usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * 2.1 Crear usuario
     * Endpoint: POST /api/v1/usuarios
     */
    @Operation(
            summary = "Registrar un nuevo usuario",
            description = "Crea un nuevo usuario en la base de datos validando los campos y la unicidad del correo."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "Usuario registrado correctamente",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos en el formulario",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "409",
                    description = "Correo ya registrado previamente",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<UsuarioResponse>> crearUsuario(@Valid @RequestBody UsuarioCreateRequest request) {
        UsuarioResponse nuevoUsuario = usuarioService.crearUsuario(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.de("Usuario registrado correctamente.", nuevoUsuario));
    }

    /**
     * 2.2 Consultar usuarios registrados (listado general)
     * Endpoint: GET /api/v1/usuarios
     */
    @Operation(
            summary = "Consultar todos los usuarios",
            description = "Retorna la lista completa de usuarios registrados. No utiliza @RequestParam."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Lista de usuarios obtenida exitosamente",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))
            )
    })
    @GetMapping
    public ResponseEntity<ApiResponse<List<UsuarioResponse>>> consultarUsuarios() {
        List<UsuarioResponse> usuarios = usuarioService.obtenerTodos();
        return ResponseEntity.ok(ApiResponse.de("Usuarios consultados exitosamente.", usuarios));
    }

    /**
     * 2.3 Consultar usuario mediante una llave compuesta (nombre + correo)
     * Endpoint: POST /api/v1/usuarios/consulta
     */
    @Operation(
            summary = "Consultar usuario por llave compuesta (nombre y correo)",
            description = "Localiza a un usuario cuyos datos coincidan exactamente con el nombre y correo recibidos en el Request DTO."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Usuario localizado exitosamente",
                    content = @Content(schema = @Schema(implementation = UsuarioResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Nombre o correo ausentes o con formato inválido",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Usuario no encontrado",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    @PostMapping("/consulta")
    public ResponseEntity<UsuarioResponse> consultarPorLlaveCompuesta(@Valid @RequestBody UsuarioConsultaRequest request) {
        UsuarioResponse usuario = usuarioService.consultarPorLlaveCompuesta(request);
        return ResponseEntity.ok(usuario);
    }

    /**
     * 2.4 Actualizar usuario parcialmente
     * Endpoint: PATCH /api/v1/usuarios/actualizacion
     */
    @Operation(
            summary = "Actualizar parcialmente un usuario",
            description = "Modifica la información de un usuario identificado por su nombre y correo actual."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Usuario actualizado exitosamente",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos en la petición",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Usuario a actualizar no encontrado",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "409",
                    description = "El nuevo correo especificado ya pertenece a otro usuario",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    @PatchMapping("/actualizacion")
    public ResponseEntity<ApiResponse<UsuarioResponse>> actualizarUsuario(@Valid @RequestBody UsuarioUpdateRequest request) {
        UsuarioResponse actualizado = usuarioService.actualizarUsuario(request);
        return ResponseEntity.ok(ApiResponse.de("Usuario actualizado correctamente.", actualizado));
    }

    /**
     * 2.5 Eliminar usuario mediante llave compuesta
     * Endpoint: DELETE /api/v1/usuarios/eliminacion
     */
    @Operation(
            summary = "Eliminar un usuario",
            description = "Elimina de la base de datos al usuario identificado por nombre y correo."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Usuario eliminado exitosamente con confirmación estructurada",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Criterios de identificación inválidos",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Usuario a eliminar no encontrado",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    @DeleteMapping("/eliminacion")
    public ResponseEntity<ApiResponse<Void>> eliminarUsuario(@Valid @RequestBody UsuarioEliminacionRequest request) {
        usuarioService.eliminarUsuario(request);
        return ResponseEntity.ok(ApiResponse.soloMensaje("Usuario eliminado correctamente."));
    }

    /**
     * 7.2 Búsqueda dinámica mediante Spring Data JPA Specifications
     * Endpoint: POST /api/v1/usuarios/busqueda
     */
    @Operation(
            summary = "Búsqueda dinámica de usuarios",
            description = "Permite filtrar dinámicamente por nombre (parcial), correo, teléfono y estado activo combinables en el cuerpo de la petición."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Búsqueda completada exitosamente",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))
            )
    })
    @PostMapping("/busqueda")
    public ResponseEntity<ApiResponse<Page<UsuarioResponse>>> buscarUsuarios(
            @Valid @RequestBody(required = false) UsuarioBusquedaRequest request) {
        UsuarioBusquedaRequest filtro = (request != null) ? request : new UsuarioBusquedaRequest();
        Page<UsuarioResponse> resultado = usuarioService.buscarUsuariosDinamico(filtro);
        return ResponseEntity.ok(ApiResponse.de("Búsqueda realizada exitosamente.", resultado));
    }
}
