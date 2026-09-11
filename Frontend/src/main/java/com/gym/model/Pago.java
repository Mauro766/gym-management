package com.gym.model;

import java.time.LocalDate;

public class Pago {
    private final int id;
    private final int socioId;
    private final String periodo;
    private final double monto;
    private final LocalDate fechaVencimiento;
    private LocalDate fechaPago;
    private EstadoPago estado;

    public Pago(int id, int socioId, String periodo, double monto, LocalDate fechaVencimiento, LocalDate fechaPago, EstadoPago estado) {
        this.id = id; this.socioId = socioId; this.periodo = periodo; this.monto = monto; this.fechaVencimiento = fechaVencimiento;
        this.fechaPago = fechaPago; this.estado = estado;
    }
    public int getId() { return id; }
    public int getSocioId() { return socioId; }
    public String getPeriodo() { return periodo; }
    public double getMonto() { return monto; }
    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public LocalDate getFechaPago() { return fechaPago; }
    public EstadoPago getEstado() { return estado; }
    public void marcarComoPagado() { estado = EstadoPago.PAGADO; fechaPago = LocalDate.now(); }
}