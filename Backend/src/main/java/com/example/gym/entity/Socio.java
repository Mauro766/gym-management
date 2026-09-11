package com.example.gym.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "socios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Socio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    @NotBlank
    private String apellido;

    @Column(unique = true, nullable = false)
    private String dni;

    private String telefono;

    @Email
    private String email;

    private LocalDate fechaNacimiento;

    private LocalDate fechaIngreso;

    @Enumerated(EnumType.STRING)
    private EstadoSocio estado;
}