package com.gym.service;

import com.gym.model.EstadoPago;
import com.gym.model.Pago;
import com.gym.model.Socio;
import com.gym.repository.SocioRepository;
import java.util.List;
import java.util.Optional;

public class SocioService {
    private final SocioRepository repository;
    public SocioService(SocioRepository repository) { this.repository = repository; }
    public List<Socio> obtenerSocios() { return repository.findAllSocios(); }
    public List<Pago> obtenerPagos() { return repository.findAllPagos(); }
    public Optional<Socio> obtenerSocio(int id) { return obtenerSocios().stream().filter(s -> s.getId() == id).findFirst(); }
    public Optional<Socio> obtenerSocioDePago(Pago pago) { return obtenerSocio(pago.getSocioId()); }
    public long contarActivos() { return obtenerSocios().stream().filter(Socio::isActivo).count(); }
    public long contarPagos(EstadoPago state) { return obtenerPagos().stream().filter(p -> p.getEstado() == state).count(); }
    public void guardarSocio(Socio socio) { repository.saveSocio(socio); }
    public void marcarPagoComoPagado(Pago pago) { pago.marcarComoPagado(); }
}