package com.example.gym.controller;

import com.example.gym.dto.SocioRequestDTO;
import com.example.gym.dto.SocioResponseDTO;
import com.example.gym.service.SocioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/socios")
public class SocioController {

    private final SocioService socioService;

    public SocioController(SocioService socioService) {
        this.socioService = socioService;
    }

    @GetMapping
    public ResponseEntity<List<SocioResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(socioService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SocioResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(socioService.obtenerPorId(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<SocioResponseDTO>> buscar(@RequestParam(required = false, defaultValue = "") String query) {
        return ResponseEntity.ok(socioService.buscar(query));
    }

    @PostMapping
    public ResponseEntity<SocioResponseDTO> crear(@Valid @RequestBody SocioRequestDTO requestDTO) {
        SocioResponseDTO nuevoSocio = socioService.crear(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoSocio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SocioResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody SocioRequestDTO requestDTO) {
        return ResponseEntity.ok(socioService.actualizar(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        socioService.desactivar(id);
        return ResponseEntity.noContent().build();
    }
}