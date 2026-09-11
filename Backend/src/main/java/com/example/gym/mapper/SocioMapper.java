package com.example.gym.mapper;

import com.example.gym.dto.SocioRequestDTO;
import com.example.gym.dto.SocioResponseDTO;
import com.example.gym.entity.Socio;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SocioMapper {

    public Socio toEntity(SocioRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Socio socio = new Socio();
        socio.setNombre(dto.getNombre() != null ? dto.getNombre().trim() : null);
        socio.setApellido(dto.getApellido() != null ? dto.getApellido().trim() : null);
        socio.setDni(dto.getDni() != null ? dto.getDni().trim() : null);
        socio.setTelefono(dto.getTelefono() != null ? dto.getTelefono().trim() : null);
        socio.setEmail(dto.getEmail() != null ? dto.getEmail().trim() : null);
        socio.setFechaNacimiento(dto.getFechaNacimiento());

        return socio;
    }

    public SocioResponseDTO toResponseDTO(Socio socio) {
        if (socio == null) {
            return null;
        }

        return SocioResponseDTO.builder()
                .id(socio.getId())
                .nombre(socio.getNombre())
                .apellido(socio.getApellido())
                .dni(socio.getDni())
                .telefono(socio.getTelefono())
                .email(socio.getEmail())
                .fechaNacimiento(socio.getFechaNacimiento())
                .fechaIngreso(socio.getFechaIngreso())
                .estado(socio.getEstado())
                .build();
    }

    public void updateEntityFromDTO(Socio socio, SocioRequestDTO dto) {
        if (socio == null || dto == null) {
            return;
        }

        socio.setNombre(dto.getNombre() != null ? dto.getNombre().trim() : socio.getNombre());
        socio.setApellido(dto.getApellido() != null ? dto.getApellido().trim() : socio.getApellido());
        socio.setDni(dto.getDni() != null ? dto.getDni().trim() : socio.getDni());
        socio.setTelefono(dto.getTelefono() != null ? dto.getTelefono().trim() : null);
        socio.setEmail(dto.getEmail() != null ? dto.getEmail().trim() : null);
        socio.setFechaNacimiento(dto.getFechaNacimiento());
    }

    public List<SocioResponseDTO> toResponseDTOList(List<Socio> socios) {
        if (socios == null) {
            return Collections.emptyList();
        }

        return socios.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}
