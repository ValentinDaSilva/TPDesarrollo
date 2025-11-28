// [src/main/java/com/hotel/hotel_backend/dao/HuespedDAO.java]
package com.hotel.hotel_backend.dao;

import com.hotel.hotel_backend.domain.Huesped;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface HuespedDAO extends JpaRepository<Huesped, String> {

    // Buscar por documento (ID principal)
    Optional<Huesped> findByNumeroDocumento(String numeroDocumento);

    // Para validar existencia desde el service
    boolean existsByNumeroDocumento(String numeroDocumento);

    // Por CUIT (opcional)
    Optional<Huesped> findByCuit(String cuit);

    // Búsqueda con filtros
    @Query("""
            SELECT h FROM Huesped h
            WHERE (:apellido IS NULL OR :apellido = '' OR h.apellido LIKE :apellido% )
            AND   (:nombre IS NULL OR :nombre = '' OR h.nombre LIKE :nombre% )
            AND   (:tipoDocumento IS NULL OR :tipoDocumento = '' OR h.tipoDocumento = :tipoDocumento )
            AND   (:numeroDocumento IS NULL OR :numeroDocumento = '' OR h.numeroDocumento LIKE :numeroDocumento% )
           """)
    List<Huesped> buscarConFiltros(
            @Param("apellido") String apellido,
            @Param("nombre") String nombre,
            @Param("tipoDocumento") String tipoDocumento,
            @Param("numeroDocumento") String numeroDocumento
    );

    List<Huesped> findAllByCuit(String cuit);

}
