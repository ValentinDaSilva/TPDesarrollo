package com.hotelPremier.classes.Dominio;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipohabitacion")
@Table(name="habitacion")
public class Habitacion  {

    @Id 
    private Integer numero; 

    @Column(name="estado") 
    private String estado;

    @Column(name="precio")  
    private float precio;

    @Column(name="cantidadpersonas")
    private Integer cantidadPersonas;    

    @Column(name = "tipohabitacion", insertable=false, updatable=false)
    private String tipohabitacion;

    // HABITACION → RESERVAS (NO ROMPE JSON POR BACKREF EN Reserva)
    @OneToMany(mappedBy = "habitacion")
    @JsonManagedReference(value = "habitacion-reservas")
    private List<Reserva> listaReservas;

    // HABITACION → ESTADIAS (NO ROMPE JSON POR BACKREF EN Estadia)
    @OneToMany(mappedBy = "habitacion")
    @JsonManagedReference(value = "habitacion-estadias")
    private List<Estadia> listaEstadias;


    public Habitacion() {
        // Constructor vacío requerido por JPA
    }

    public Habitacion(
            Integer numero,
            String estado,
            float precio,
            Integer cantidadPersonas,
            String tipohabitacion,
            List<Reserva> listaReservas,
            List<Estadia> listaEstadias
    ) {
        this.numero = numero;
        this.estado = estado;
        this.precio = precio;
        this.cantidadPersonas = cantidadPersonas;
        this.tipohabitacion = tipohabitacion;
        this.listaReservas = listaReservas;
        this.listaEstadias = listaEstadias;
    }

    // ========================
    // GETTERS & SETTERS
    // ========================

    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public float getPrecio() { return precio; }
    public void setPrecio(float precio) { this.precio = precio; }

    public Integer getCantidadPersonas() { return cantidadPersonas; }
    public void setCantidadPersonas(Integer cantidadPersonas) { this.cantidadPersonas = cantidadPersonas; }

    public String getTipohabitacion() { return this.tipohabitacion; }

    public List<Reserva> getListaReservas(){ return this.listaReservas; }
    public void setListaReservas(List<Reserva> listres){ this.listaReservas = listres; }

    public List<Estadia> getListaEstadia() { return this.listaEstadias; }
    public void setListaEstadia(List<Estadia> listaEstadias) { this.listaEstadias = listaEstadias; }

    // Métodos para calcular tipo según clase concreta
    public String getTipo(){
        if (this instanceof com.hotelPremier.classes.Dominio.habitacion.IndividualEstandar) return "IndividualEstandar";
        if (this instanceof com.hotelPremier.classes.Dominio.habitacion.DobleEstandar) return "DobleEstandar";
        if (this instanceof com.hotelPremier.classes.Dominio.habitacion.DobleSuperior) return "DobleSuperior";
        if (this instanceof com.hotelPremier.classes.Dominio.habitacion.SuperiorFamilyPlan) return "SuperiorFamilyPlan";
        if (this instanceof com.hotelPremier.classes.Dominio.habitacion.Suite) return "Suite";
        return null;
    }
}
