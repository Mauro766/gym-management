package com.example.gym.controller;

import com.example.gym.dto.SocioRequestDTO;
import com.example.gym.dto.SocioResponseDTO;
import com.example.gym.entity.EstadoSocio;
import com.example.gym.exception.DuplicateResourceException;
import com.example.gym.exception.GlobalExceptionHandler;
import com.example.gym.exception.ResourceNotFoundException;
import com.example.gym.service.SocioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class SocioControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SocioService socioService;

    @InjectMocks
    private SocioController socioController;

    private SocioResponseDTO responseDTO;
    private final String requestJson = "{\"nombre\":\"Mauro\",\"apellido\":\"Gomez\",\"dni\":\"40123456\",\"telefono\":\"1155443322\",\"email\":\"mauro@example.com\",\"fechaNacimiento\":\"1998-03-15\"}";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(socioController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        responseDTO = SocioResponseDTO.builder()
                .id(1L)
                .nombre("Mauro")
                .apellido("Gomez")
                .dni("40123456")
                .telefono("1155443322")
                .email("mauro@example.com")
                .fechaNacimiento(LocalDate.of(1998, 3, 15))
                .fechaIngreso(LocalDate.now())
                .estado(EstadoSocio.ACTIVO)
                .build();
    }

    @Test
    @DisplayName("GET /api/socios - Debe retornar lista de socios 200 OK")
    void testObtenerTodos() throws Exception {
        when(socioService.obtenerTodos()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/socios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nombre", is("Mauro")))
                .andExpect(jsonPath("$[0].dni", is("40123456")));
    }

    @Test
    @DisplayName("GET /api/socios/{id} - Debe retornar socio 200 OK")
    void testObtenerPorId() throws Exception {
        when(socioService.obtenerPorId(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/socios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombre", is("Mauro")));
    }

    @Test
    @DisplayName("GET /api/socios/{id} - Debe retornar 404 NOT FOUND si no existe")
    void testObtenerPorIdNotFound() throws Exception {
        when(socioService.obtenerPorId(99L)).thenThrow(new ResourceNotFoundException("Socio no encontrado con ID: 99"));

        mockMvc.perform(get("/api/socios/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.message", is("Socio no encontrado con ID: 99")));
    }

    @Test
    @DisplayName("GET /api/socios/buscar - Debe filtrar socios por query 200 OK")
    void testBuscarSocios() throws Exception {
        when(socioService.buscar("mauro")).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/socios/buscar").param("query", "mauro"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nombre", is("Mauro")));
    }

    @Test
    @DisplayName("POST /api/socios - Debe crear socio 201 CREATED")
    void testCrearSocio() throws Exception {
        when(socioService.crear(any(SocioRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/socios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombre", is("Mauro")));
    }

    @Test
    @DisplayName("POST /api/socios - Debe retornar 409 CONFLICT si DNI ya existe")
    void testCrearSocioDniDuplicado() throws Exception {
        when(socioService.crear(any(SocioRequestDTO.class)))
                .thenThrow(new DuplicateResourceException("Ya existe un socio registrado con el DNI: 40123456"));

        mockMvc.perform(post("/api/socios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status", is(409)))
                .andExpect(jsonPath("$.message", is("Ya existe un socio registrado con el DNI: 40123456")));
    }

    @Test
    @DisplayName("PUT /api/socios/{id} - Debe actualizar socio 200 OK")
    void testActualizarSocio() throws Exception {
        when(socioService.actualizar(eq(1L), any(SocioRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/api/socios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombre", is("Mauro")));
    }

    @Test
    @DisplayName("DELETE /api/socios/{id} - Debe desactivar socio 204 NO CONTENT")
    void testDesactivarSocio() throws Exception {
        doNothing().when(socioService).desactivar(1L);

        mockMvc.perform(delete("/api/socios/1"))
                .andExpect(status().isNoContent());
    }
}
