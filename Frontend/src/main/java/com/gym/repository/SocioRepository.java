package com.gym.repository;

import com.gym.model.EstadoPago;
import com.gym.model.Pago;
import com.gym.model.Socio;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SocioRepository {
    private final List<Socio> socios = new ArrayList<>();
    private final List<Pago> pagos = new ArrayList<>();

    public SocioRepository() {
        String[][] data = {
                {"Ana", "García", "30111222", "11 5555-0101", "ana@fitclub.com"}, {"Bruno", "López", "32222333", "11 5555-0102", "bruno@fitclub.com"},
                {"Carla", "Méndez", "33444555", "11 5555-0103", "carla@fitclub.com"}, {"Diego", "Sosa", "34555666", "11 5555-0104", "diego@fitclub.com"},
                {"Elena", "Rossi", "35666777", "11 5555-0105", "elena@fitclub.com"}, {"Facundo", "Vega", "36777888", "11 5555-0106", "facundo@fitclub.com"},
                {"Gabriela", "Paz", "37888999", "11 5555-0107", "gabriela@fitclub.com"}, {"Hernán", "Ruiz", "38999000", "11 5555-0108", "hernan@fitclub.com"},
                {"Irene", "Silva", "40111000", "11 5555-0109", "irene@fitclub.com"}, {"Julián", "Costa", "41222000", "11 5555-0110", "julian@fitclub.com"}
        };
        for (int i = 0; i < data.length; i++) {
            socios.add(new Socio(i + 1, data[i][0], data[i][1], data[i][2], data[i][3], data[i][4], LocalDate.now().minusDays(20L * (i + 1)), i != 7));
            EstadoPago state = i % 3 == 0 ? EstadoPago.PENDIENTE : EstadoPago.PAGADO;
            pagos.add(new Pago(i + 1, i + 1, "Septiembre 2026", 25000 + i * 500, LocalDate.of(2026, 9, 10 + (i % 10)),
                    state == EstadoPago.PAGADO ? LocalDate.of(2026, 9, 5) : null, state));
        }
    }
    public List<Socio> findAllSocios() { return socios; }
    public List<Pago> findAllPagos() { return pagos; }
    public void saveSocio(Socio socio) { socios.add(socio); }
}