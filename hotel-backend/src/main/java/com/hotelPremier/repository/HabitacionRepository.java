package com.hotelPremier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hotelPremier.classes.Dominio.Habitacion;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Integer> {

    // ===============================
    // BÚSQUEDAS BÁSICAS
    // ===============================

    List<Habitacion> findAll();

    Habitacion findByNumero(Integer numero);

    // ===============================
    // FILTRO POR TIPO
    // ===============================

    @Query("""
        SELECT h
        FROM Habitacion h
        WHERE h.tipohabitacion = :tipoHabitacion
    """)
    List<Habitacion> buscarPorTipoHabitacion(
            @Param("tipoHabitacion") String tipoHabitacion
    );

    // ===============================
    // HABITACIONES CON RESERVAS / ESTADÍAS EN RANGO
    // ===============================

    @Query("""
        SELECT DISTINCT h
        FROM Habitacion h
            LEFT JOIN h.listaReservas r
                ON r.fecha_desde <= :fechaHasta
               AND r.fecha_hasta >= :fechaDesde
            LEFT JOIN h.listaEstadias e
                ON e.checkin <= :fechaHasta
               AND e.checkout >= :fechaDesde
        WHERE h.tipohabitacion = :tipoHabitacion
    """)
    List<Habitacion> buscarListaHabitaciones(
            @Param("tipoHabitacion") String tipoHabitacion,
            @Param("fechaDesde") LocalDate fechaDesde,
            @Param("fechaHasta") LocalDate fechaHasta
    );
}
