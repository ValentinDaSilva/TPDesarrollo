// [src/main/java/com/hotel/hotel_backend/dao/FacturaDAO.java]
package com.hotel.hotel_backend.dao;

import com.hotel.hotel_backend.domain.Factura;
import com.hotel.hotel_backend.domain.Pago;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FacturaDAO extends JpaRepository<Factura, Integer> {

    // ==================================================
    // FACTURAS PENDIENTES POR HABITACIÓN
    // ==================================================
    @Query("""
            SELECT f
            FROM Factura f
            JOIN f.estadia e
            JOIN e.reserva r
            JOIN r.habitaciones h
            WHERE f.estado = 'Pendiente'
              AND h.numero = :nro
            """)
    List<Factura> findPendientesByHabitacion(@Param("nro") int nroHabitacion);


    // ==================================================
    // PAGOS ENTRE FECHAS
    // ==================================================
    @Query("""
            SELECT p
            FROM Factura f
            JOIN f.pagos p
            WHERE p.fecha BETWEEN :desde AND :hasta
            """)
    List<Pago> findPagosEntreFechas(
            @Param("desde") LocalDate desde,
            @Param("hasta") LocalDate hasta
    );


    // ==================================================
    // CHEQUES ENTRE FECHAS
    // ==================================================
    @Query("""
            SELECT p
            FROM Factura f
            JOIN f.pagos p
            WHERE TYPE(p.medioDePago) = Cheque
              AND p.fecha BETWEEN :desde AND :hasta
            """)
    List<Pago> findChequesEntreFechas(
            @Param("desde") LocalDate desde,
            @Param("hasta") LocalDate hasta
    );
}
