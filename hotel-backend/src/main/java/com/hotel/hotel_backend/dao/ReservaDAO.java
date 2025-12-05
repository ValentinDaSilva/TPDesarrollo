// [src/main/java/com/hotel/hotel_backend/dao/ReservaDAO.java]
package com.hotel.hotel_backend.dao;

import com.hotel.hotel_backend.domain.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaDAO extends JpaRepository<Reserva, Integer> {

    // Solapamiento
    @Query("""
            SELECT COUNT(r) > 0
            FROM Reserva r
            JOIN r.habitaciones h
            WHERE h.numero = :nro
              AND r.estado <> 'Cancelada'
              AND r.fechaInicio <= :fin
              AND r.fechaFin >= :inicio
            """)
    boolean existeSolapamiento(
            @Param("nro") Integer nro,
            @Param("inicio") LocalDate inicio,
            @Param("fin") LocalDate fin
    );

    // NUEVO: reservas dentro del rango
    @Query("""
            SELECT r
            FROM Reserva r
            WHERE r.fechaInicio >= :inicio
            AND   r.fechaFin   <= :fin
           """)
    List<Reserva> buscarEntreFechas(
            @Param("inicio") LocalDate inicio,
            @Param("fin") LocalDate fin
    );
}
