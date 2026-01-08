package com.hotelPremier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hotelPremier.classes.Dominio.medioDePago.Cheque;
import com.hotelPremier.classes.Dominio.medioDePago.MedioDePago;

import java.util.Date;
import java.util.List;

@Repository
public interface MedioDePagoRepository extends JpaRepository<MedioDePago, Integer> {
    
    /**
     * Busca todos los cheques en un rango de fechas de cobro (plazo).
     */
    @Query("""
        SELECT c 
        FROM Cheque c 
        WHERE c.plazo BETWEEN :desdeFecha AND :hastaFecha 
        ORDER BY c.plazo ASC
    """)
    List<Cheque> findChequesPorRangoFechas(
        @Param("desdeFecha") Date desdeFecha,
        @Param("hastaFecha") Date hastaFecha
    );
}

