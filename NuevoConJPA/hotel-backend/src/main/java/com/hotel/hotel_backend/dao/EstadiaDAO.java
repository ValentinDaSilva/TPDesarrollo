// src/main/java/com/hotel/hotel_backend/dao/EstadiaDAO.java
package com.hotel.hotel_backend.dao;

import com.hotel.hotel_backend.domain.Estadia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@Repository
public interface EstadiaDAO extends JpaRepository<Estadia, Integer> {

    @Query("""
            SELECT e FROM Estadia e
            JOIN e.reserva r
            JOIN r.habitaciones h
            WHERE e.estado = 'EnCurso'
              AND h.numero = :numeroHabitacion
           """)
    Optional<Estadia> findActivaByHabitacion(@Param("numeroHabitacion") Integer numeroHabitacion);

    // ✅ Ahora el parámetro del método está anotado con @Param
    @Query("""
            SELECT CASE WHEN COUNT(e) > 0 THEN TRUE ELSE FALSE END
            FROM Estadia e
            WHERE e.reserva.id = :idReserva
            """)
    boolean existsByReservaId(@Param("idReserva") Integer idReserva);
}
