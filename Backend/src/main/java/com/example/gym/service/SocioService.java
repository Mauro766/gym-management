package com.example.gym.service;

import com.example.gym.dto.SocioRequestDTO;
import com.example.gym.dto.SocioResponseDTO;
import com.example.gym.entity.EstadoSocio;
import com.example.gym.entity.Socio;
import com.example.gym.exception.DuplicateResourceException;
import com.example.gym.exception.ResourceNotFoundException;
import com.example.gym.mapper.SocioMapper;
import com.example.gym.repository.SocioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class SocioService {

    private final SocioRepository socioRepository;
    private final SocioMapper socioMapper;

    public SocioService(SocioRepository socioRepository, SocioMapper socioMapper) {
        this.socioRepository = socioRepository;
        this.socioMapper = socioMapper;
    }

    @Transactional(readOnly = true)
    public List<SocioResponseDTO> obtenerTodos() {
        List<Socio> socios = socioRepository.findAll();
        return socioMapper.toResponseDTOList(socios);
    }

    @Transactional(readOnly = true)
    public SocioResponseDTO obtenerPorId(Long id) {
        Socio socio = socioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Socio no encontrado con ID: " + id));
        return socioMapper.toResponseDTO(socio);
    }

    @Transactional(readOnly = true)
    public List<SocioResponseDTO> buscar(String query) {
        if (query == null || query.trim().isEmpty()) {
            return obtenerTodos();
        }
        List<Socio> socios = socioRepository.buscarPorTermino(query.trim());
        return socioMapper.toResponseDTOList(socios);
    }

    @Transactional
    public SocioResponseDTO crear(SocioRequestDTO dto) {
        String dni = dto.getDni() != null ? dto.getDni().trim() : null;

        if (dni != null && socioRepository.existsByDni(dni)) {
            throw new DuplicateResourceException("Ya existe un socio registrado con el DNI: " + dni);
        }

        Socio socio = socioMapper.toEntity(dto);
        socio.setFechaIngreso(LocalDate.now());
        socio.setEstado(EstadoSocio.ACTIVO);

        Socio nuevoSocio = socioRepository.save(socio);
        return socioMapper.toResponseDTO(nuevoSocio);
    }

    @Transactional
    public SocioResponseDTO actualizar(Long id, SocioRequestDTO dto) {
        Socio socio = socioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Socio no encontrado con ID: " + id));

        String dni = dto.getDni() != null ? dto.getDni().trim() : null;
        if (dni != null && socioRepository.existsByDniAndIdNot(dni, id)) {
            throw new DuplicateResourceException("Ya existe otro socio registrado con el DNI: " + dni);
        }

        socioMapper.updateEntityFromDTO(socio, dto);
        Socio socioActualizado = socioRepository.save(socio);
        return socioMapper.toResponseDTO(socioActualizado);
    }

    @Transactional
    public void desactivar(Long id) {
        Socio socio = socioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Socio no encontrado con ID: " + id));

        socio.setEstado(EstadoSocio.INACTIVO);
        socioRepository.save(socio);
    }
}