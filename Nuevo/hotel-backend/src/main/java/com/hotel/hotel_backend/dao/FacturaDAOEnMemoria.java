package com.hotel.hotel_backend.dao;

import com.hotel.hotel_backend.domain.Factura;
import com.hotel.hotel_backend.domain.NotaDeCredito;
import com.hotel.hotel_backend.domain.Pago;
import com.hotel.hotel_backend.domain.Estadia;
import com.hotel.hotel_backend.domain.Reserva;
import com.hotel.hotel_backend.domain.MedioDePago.Cheque;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FacturaDAOEnMemoria implements FacturaDAO {

    private static FacturaDAOEnMemoria instance;
    public static synchronized FacturaDAOEnMemoria getInstance() {
        if (instance == null) instance = new FacturaDAOEnMemoria();
        return instance;
    }

    private final List<Factura> facturas = new ArrayList<>();
    private final List<NotaDeCredito> notas = new ArrayList<>();
    private int secuenciaNota = 1;

    private int secuenciaId = 1;

    private FacturaDAOEnMemoria() {}

    @Override
    public void save(Factura f) {
        f.setId(secuenciaId++);
        facturas.add(f);
    }

    @Override
    public Optional<Factura> findById(int id) {
        return facturas.stream()
                .filter(x -> x.getId() == id)
                .findFirst();
    }

    @Override
    public List<Factura> findAll() {
        return new ArrayList<>(facturas);
    }

    @Override
    public void update(Factura f) {
        for (int i = 0; i < facturas.size(); i++) {
            if (facturas.get(i).getId() == f.getId()) {
                facturas.set(i, f);
                return;
            }
        }
    }

    @Override
    public List<Factura> findPendientesByHabitacion(int nroHabitacion) {

        return facturas.stream()
                .filter(f -> f.getEstado().equalsIgnoreCase("Pendiente"))
                .filter(f -> estadiaTieneHabitacion(f.getEstadia().getId(), nroHabitacion))
                .collect(Collectors.toList());
    }


    private boolean estadiaTieneHabitacion(int idEstadia, int nroHabitacion) {

        Estadia e = EstadiaDAOEnMemoria.getInstance()
                .findById(idEstadia)
                .orElse(null);

        if (e == null) return false;

        Reserva r = ReservaDAOEnMemoria.getInstance()
                .findById(e.getIdReserva())
                .orElse(null);

        if (r == null) return false;

        return r.getHabitaciones().contains(nroHabitacion);
    }

    public List<Pago> findChequesEntreFechas(LocalDate desde, LocalDate hasta) {

        List<Pago> resultado = new ArrayList<>();

        for (Factura f : facturas) {
            for (Pago p : f.getPagos()) {

                if (p.getMedioDePago() instanceof Cheque) {
                    LocalDate fechaPago = LocalDate.parse(p.getFecha());

                    if ((fechaPago.isEqual(desde) || fechaPago.isAfter(desde)) &&
                        (fechaPago.isEqual(hasta) || fechaPago.isBefore(hasta))) {

                        resultado.add(p);
                    }
                }
            }
        }

        return resultado;
    }

    public List<Pago> findPagosEntreFechas(LocalDate desde, LocalDate hasta) {

        List<Pago> resultado = new ArrayList<>();

        for (Factura f : facturas) {
            for (Pago p : f.getPagos()) {

                LocalDate fechaPago = LocalDate.parse(p.getFecha());

                if ((fechaPago.isEqual(desde) || fechaPago.isAfter(desde)) &&
                    (fechaPago.isEqual(hasta) || fechaPago.isBefore(hasta))) {

                    resultado.add(p);
                }
            }
        }

        return resultado;
    }

    public void saveNota(NotaDeCredito nota) {

       // generar ID
        nota.setIdNota(secuenciaNota++);

        // cancelar facturas asociadas
        if (nota.getFacturas() != null) {
            for (Factura fac : nota.getFacturas()) {

                // buscar factura original en memoria
                findById(fac.getId()).ifPresent(f -> {
                    f.setEstado("CANCELADA");   // <---- IMPORTANTE
                    update(f);                 // reemplazar factura vieja
                });
            }
        }

        // guardar nota en la lista
        notas.add(nota);
    }

}
