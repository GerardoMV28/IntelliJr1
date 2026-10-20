package com.proyecto.usuarios.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.usuarios.dto.request.UsuarioBusquedaRequest;
import com.proyecto.usuarios.dto.request.UsuarioConsultaRequest;
import com.proyecto.usuarios.dto.request.UsuarioCreateRequest;
import com.proyecto.usuarios.dto.request.UsuarioEliminacionRequest;
import com.proyecto.usuarios.dto.request.UsuarioUpdateRequest;
import com.proyecto.usuarios.entity.Usuario;
import com.proyecto.usuarios.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setUp() {
        usuarioRepository.deleteAll();

        Usuario u1 = new Usuario("Juan Pérez", "juan.perez@correo.com", "4771234567", true);
        Usuario u2 = new Usuario("María López", "maria.lopez@correo.com", "4779876543", true);
        usuarioRepository.save(u1);
        usuarioRepository.save(u2);
    }

    @Test
    @DisplayName("2.1 - POST /api/v1/usuarios debe crear un usuario exitosamente (HTTP 201)")
    void testCrearUsuarioExitoso() throws Exception {
        UsuarioCreateRequest request = new UsuarioCreateRequest("Carlos Gómez", "carlos.gomez@correo.com", "4775551234");

        mockMvc.perform(post("/api/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.mensaje", is("Usuario registrado correctamente.")))
                .andExpect(jsonPath("$.datos.nombre", is("Carlos Gómez")))
                .andExpect(jsonPath("$.datos.correo", is("carlos.gomez@correo.com")))
                .andExpect(jsonPath("$.datos.telefono", is("4775551234")))
                .andExpect(jsonPath("$.datos.activo", is(true)))
                .andExpect(jsonPath("$.datos.id", notNullValue()))
                .andExpect(jsonPath("$.datos.fechaCreacion", notNullValue()));
    }

    @Test
    @DisplayName("2.1 - POST /api/v1/usuarios con correo duplicado debe retornar HTTP 409 Conflict")
    void testCrearUsuarioCorreoDuplicado() throws Exception {
        UsuarioCreateRequest request = new UsuarioCreateRequest("Otro Juan", "juan.perez@correo.com", "4771112233");

        mockMvc.perform(post("/api/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.codigo", is("CORREO_DUPLICADO")))
                .andExpect(jsonPath("$.mensaje", containsString("El correo ya se encuentra registrado")));
    }

    @Test
    @DisplayName("2.1 - POST /api/v1/usuarios con datos inválidos debe retornar HTTP 400 Bad Request")
    void testCrearUsuarioDatosInvalidos() throws Exception {
        UsuarioCreateRequest request = new UsuarioCreateRequest("   ", "correo-invalido", "123");

        mockMvc.perform(post("/api/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo", is("DATOS_INVALIDOS")))
                .andExpect(jsonPath("$.errores.nombre", notNullValue()))
                .andExpect(jsonPath("$.errores.correo", notNullValue()))
                .andExpect(jsonPath("$.errores.telefono", notNullValue()));
    }

    @Test
    @DisplayName("2.2 - GET /api/v1/usuarios debe retornar la lista de usuarios (HTTP 200)")
    void testConsultarUsuarios() throws Exception {
        mockMvc.perform(get("/api/v1/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje", is("Usuarios consultados exitosamente.")))
                .andExpect(jsonPath("$.datos", hasSize(2)));
    }

    @Test
    @DisplayName("2.3 - POST /api/v1/usuarios/consulta con llave compuesta debe retornar HTTP 200 OK")
    void testConsultarPorLlaveCompuestaExitosa() throws Exception {
        UsuarioConsultaRequest request = new UsuarioConsultaRequest("Juan Pérez", "juan.perez@correo.com");

        mockMvc.perform(post("/api/v1/usuarios/consulta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Juan Pérez")))
                .andExpect(jsonPath("$.correo", is("juan.perez@correo.com")))
                .andExpect(jsonPath("$.telefono", is("4771234567")));
    }

    @Test
    @DisplayName("2.3 - POST /api/v1/usuarios/consulta usuario no existente debe retornar HTTP 404 Not Found")
    void testConsultarPorLlaveCompuestaNoEncontrado() throws Exception {
        UsuarioConsultaRequest request = new UsuarioConsultaRequest("Inexistente", "noexiste@correo.com");

        mockMvc.perform(post("/api/v1/usuarios/consulta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.codigo", is("USUARIO_NO_ENCONTRADO")))
                .andExpect(jsonPath("$.mensaje", containsString("No existe un usuario")));
    }

    @Test
    @DisplayName("2.4 - PATCH /api/v1/usuarios/actualizacion debe modificar parcialmente al usuario (HTTP 200)")
    void testActualizarUsuarioExitoso() throws Exception {
        UsuarioUpdateRequest request = new UsuarioUpdateRequest("Juan Pérez", "juan.perez@correo.com", "4779998877", false);

        mockMvc.perform(patch("/api/v1/usuarios/actualizacion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje", is("Usuario actualizado correctamente.")))
                .andExpect(jsonPath("$.datos.telefono", is("4779998877")))
                .andExpect(jsonPath("$.datos.activo", is(false)));
    }

    @Test
    @DisplayName("2.5 - DELETE /api/v1/usuarios/eliminacion debe eliminar el usuario (HTTP 200)")
    void testEliminarUsuarioExitoso() throws Exception {
        UsuarioEliminacionRequest request = new UsuarioEliminacionRequest("Juan Pérez", "juan.perez@correo.com");

        mockMvc.perform(delete("/api/v1/usuarios/eliminacion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje", is("Usuario eliminado correctamente.")));
    }

    @Test
    @DisplayName("7.2 - POST /api/v1/usuarios/busqueda dinámica con Specification debe filtrar correctamente")
    void testBusquedaDinamica() throws Exception {
        UsuarioBusquedaRequest request = new UsuarioBusquedaRequest("juan", null, null, true);

        mockMvc.perform(post("/api/v1/usuarios/busqueda")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje", is("Búsqueda realizada exitosamente.")))
                .andExpect(jsonPath("$.datos.content", hasSize(1)))
                .andExpect(jsonPath("$.datos.content[0].nombre", is("Juan Pérez")));
    }
}
