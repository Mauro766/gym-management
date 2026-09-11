package com.gym.model;

import java.time.LocalDate;

public class Socio {
    private final int id;
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String email;
    private LocalDate fechaAlta;
    private boolean activo;

    public Socio(int id, String nombre, String apellido, String dni, String telefono, String email, LocalDate fechaAlta, boolean activo) {
        this.id = id; this.nombre = nombre; this.apellido = apellido; this.dni = dni; this.telefono = telefono;
        this.email = email; this.fechaAlta = fechaAlta; this.activo = activo;
    }
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getDni() { return dni; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }
    public LocalDate getFechaAlta() { return fechaAlta; }
    public boolean isActivo() { return activo; }
    public String getNombreCompleto() { return nombre + " " + apellido; }
    public void setNombre(String value) { nombre = value; }
    public void setApellido(String value) { apellido = value; }
    public void setDni(String value) { dni = value; }
    public void setTelefono(String value) { telefono = value; }
    public void setEmail(String value) { email = value; }
    public void setFechaAlta(LocalDate value) { fechaAlta = value; }
    public void setActivo(boolean value) { activo = value; }
}