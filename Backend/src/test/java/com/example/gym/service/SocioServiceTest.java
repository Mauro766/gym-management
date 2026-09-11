package com.example.gym.service;

import com.example.gym.dto.SocioRequestDTO;
import com.example.gym.dto.SocioResponseDTO;
import com.example.gym.entity.EstadoSocio;
import com.example.gym.entity.Socio;
import com.example.gym.exception.DuplicateResourceException;
import com.example.gym.exception.ResourceNotFoundException;
import com.example.gym.mapper.SocioMapper;
import com.example.gym.repository.SocioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SocioServiceTest {

    @Mock
    private SocioRepository socioRepository;

    @Spy
    private SocioMapper socioMapper = new SocioMapper();

    @InjectMocks
    private SocioService socioService;

    private Socio socio;
    private SocioRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        socio = new Socio();
        socio.setId(1L);
        socio.setNombre("Carlos");
        socio.setApellido("Perez");
        socio.setDni("12345678");
        socio.setEmail("carlos@example.com");
        socio.setTelefono("1122334455");
        socio.setFechaNacimiento(LocalDate.of(1995, 5, 20));
        socio.setFechaIngreso(LocalDate.now());
        socio.setEstado(EstadoSocio.ACTIVO);

        requestDTO = SocioRequestDTO.builder()
                .nombre("Carlos")
                .apellido("Perez")
                .dni("12345678")
                .email("carlos@example.com")
                .telefono("1122334455")
                .fechaNacimiento(LocalDate.of(1995, 5, 20))
                .build();
    }

    @Test
    @DisplayName("Debe listar todos los socios")
    void testObtenerTodos() {
        when(socioRepository.findAll()).thenReturn(List.of(socio));

        List<SocioResponseDTO> resultado = socioService.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Carlos", resultado.get(0).getNombre());
        assertEquals("12345678", resultado.get(0).getDni());
        verify(socioRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe obtener un socio por ID existente")
    void testObtenerPorIdExitoso() {
        when(socioRepository.findById(1L)).thenReturn(Optional.of(socio));

        SocioResponseDTO resultado = socioService.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Carlos", resultado.getNombre());
    }

    @Test
    @DisplayName("Debe lanzar ResourceNotFoundException si el socio no existe por ID")
    void testObtenerPorIdNoEncontrado() {
        when(socioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> socioService.obtenerPorId(99L));
    }

    @Test
    @DisplayName("Debe crear un socio exitosamente")
    void testCrearExitoso() {
        when(socioRepository.existsByDni("12345678")).thenReturn(false);
        when(socioRepository.save(any(Socio.class))).thenAnswer(invocation -> {
            Socio s = invocation.getArgument(0);
            s.setId(1L);
            return s;
        });

        SocioResponseDTO resultado = socioService.crear(requestDTO);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(EstadoSocio.ACTIVO, resultado.getEstado());
        assertNotNull(resultado.getFechaIngreso());
        verify(socioRepository, times(1)).save(any(Socio.class));
    }

    @Test
    @DisplayName("Debe lanzar DuplicateResourceException al crear con DNI ya existente")
    void testCrearDniDuplicado() {
        when(socioRepository.existsByDni("12345678")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> socioService.crear(requestDTO));
        verify(socioRepository, never()).save(any(Socio.class));
    }

    @Test
    @DisplayName("Debe actualizar un socio existente")
    void testActualizarExitoso() {
        when(socioRepository.findById(1L)).thenReturn(Optional.of(socio));
        when(socioRepository.existsByDniAndIdNot("12345678", 1L)).thenReturn(false);
        when(socioRepository.save(any(Socio.class))).thenReturn(socio);

        requestDTO.setNombre("Carlos Alberto");
        SocioResponseDTO resultado = socioService.actualizar(1L, requestDTO);

        assertNotNull(resultado);
        assertEquals("Carlos Alberto", resultado.getNombre());
        verify(socioRepository, times(1)).save(socio);
    }

    @Test
    @DisplayName("Debe desactivar (borrado lógico) a un socio")
    void testDesactivar() {
        when(socioRepository.findById(1L)).thenReturn(Optional.of(socio));
        when(socioRepository.save(any(Socio.class))).thenReturn(socio);

        socioService.desactivar(1L);

        assertEquals(EstadoSocio.INACTIVO, socio.getEstado());
        verify(socioRepository, times(1)).save(socio);
    }

    @Test
    @DisplayName("Debe buscar socios por término parcial")
    void testBuscarPorTermino() {
        when(socioRepository.buscarPorTermino("car")).thenReturn(List.of(socio));

        List<SocioResponseDTO> resultado = socioService.buscar("car");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Carlos", resultado.get(0).getNombre());
    }
}
