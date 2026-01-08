package com.hotelPremier.classes.Dominio;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hotelPremier.classes.Dominio.estadia.estado.EstadoEstadia;
import com.hotelPremier.classes.Dominio.estadia.estado.EstadiaEnCurso;
import com.hotelPremier.classes.Dominio.estadia.estado.EstadiaFinalizada;
import com.hotelPremier.classes.Dominio.estadia.observer.EstadiaObserver;

import jakarta.persistence.*;

@Entity
@Table(name = "estadia")
public class Estadia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estadia")
    private Integer id_estadia;

    // ===============================
    // FECHAS (LocalDate, SIN @Temporal)
    // ===============================
    @Column(name = "checkin")
    private LocalDate checkin;

    @Column(name = "checkout")
    private LocalDate checkout;

    // ===============================
    // HABITACIÓN (BackReference)
    // ===============================
    @ManyToOne
    @JoinColumn(name = "nro_habitacion")
    @JsonBackReference(value = "habitacion-estadias")
    private Habitacion habitacion;

    // ===============================
    // HUÉSPEDES
    // ===============================
    @ManyToMany(mappedBy = "listaEstadias", fetch = FetchType.EAGER)
    @JsonManagedReference(value = "estadia-huespedes")
    private List<Huesped> listahuesped;

    // ===============================
    // FACTURAS
    // ===============================
    @OneToMany(mappedBy = "estadia")
    @JsonManagedReference(value = "estadia-facturas")
    private List<Factura> listafactura;

    // ===============================
    // RESERVA (BackReference)
    // ===============================
    @OneToOne
    @JoinColumn(name = "id_reserva")
    @JsonBackReference(value = "reserva-estadia")
    private Reserva reserva;

    // ===============================
    // STATE
    // ===============================
    @Column(name = "estado")
    private String estado;

    @Transient
    private EstadoEstadia estadoEstadia;

    // ===============================
    // OBSERVERS (NO persistente)
    // ===============================
    @Transient
    private List<EstadiaObserver> observers = new ArrayList<>();

    // ===============================
    // CONSTRUCTOR
    // ===============================
    public Estadia() {
        this.estado = "ENCURSO";
        this.estadoEstadia = new EstadiaEnCurso();
    }

    // ===============================
    // GETTERS / SETTERS
    // ===============================
    public Integer getId_estadia() { return id_estadia; }
    public void setId_estadia(Integer id_estadia) { this.id_estadia = id_estadia; }

    public LocalDate getCheckin() { return checkin; }
    public void setCheckin(LocalDate checkin) { this.checkin = checkin; }

    public LocalDate getCheckout() { return checkout; }
    public void setCheckout(LocalDate checkout) { this.checkout = checkout; }

    public Habitacion getHabitacion() { return habitacion; }
    public void setHabitacion(Habitacion habitacion) { this.habitacion = habitacion; }

    public List<Huesped> getListahuesped() { return listahuesped; }
    public void setListahuesped(List<Huesped> listahuesped) { this.listahuesped = listahuesped; }

    public List<Factura> getListafactura() { return listafactura; }
    public void setListafactura(List<Factura> listafactura) { this.listafactura = listafactura; }

    public Reserva getReserva() { return reserva; }
    public void setReserva(Reserva reserva) { this.reserva = reserva; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) {
        this.estado = estado;
        syncEstado();
    }

    // ===============================
    // SINCRONIZACIÓN STATE
    // ===============================
    @PostLoad
    @PostPersist
    private void syncEstado() {
        if (estado == null || estado.isEmpty()) {
            estado = "ENCURSO";
        }
        switch (estado.toUpperCase()) {
            case "FINALIZADA" -> estadoEstadia = new EstadiaFinalizada();
            default -> estadoEstadia = new EstadiaEnCurso();
        }
    }

    public void setEstadoEstadia(EstadoEstadia nuevoEstado) {
        String estadoAnterior = this.estado;
        this.estadoEstadia = nuevoEstado;
        this.estado = nuevoEstado.getNombre();

        if (!estadoAnterior.equals(this.estado)) {
            notificarObservers();
        }
    }

    // ===============================
    // OBSERVER
    // ===============================
    public void registrarObserver(EstadiaObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void eliminarObserver(EstadiaObserver observer) {
        observers.remove(observer);
    }

    private void notificarObservers() {
        for (EstadiaObserver observer : observers) {
            observer.actualizar(this);
        }
    }

    // ===============================
    // MÉTODOS DE NEGOCIO (DELEGADOS)
    // ===============================
    public void iniciarEstadia() {
        if (!"ENCURSO".equals(this.estado)) {
            if (estadoEstadia == null) {
                syncEstado();
            }
            setEstadoEstadia(new EstadiaEnCurso());
        }
    }

    public void agregarHuesped(Huesped huesped) {
        if (estadoEstadia == null) {
            syncEstado();
        }
        estadoEstadia.agregarHuesped(this, huesped);
    }

    public void agregarServicioExtra(com.hotelPremier.classes.Dominio.servicioExtra.ServicioExtra servicio) {
        if (estadoEstadia == null) {
            syncEstado();
        }
        estadoEstadia.agregarServicioExtra(this, servicio);
    }

    public void generarFactura(Factura factura) {
        if (estadoEstadia == null) {
            syncEstado();
        }
        estadoEstadia.generarFactura(this, factura);
    }

    /**
     * 🔧 IMPORTANTE: también migrado a LocalDate
     */
    public void modificarFechas(LocalDate nuevoCheckin, LocalDate nuevoCheckout) {
        if (estadoEstadia == null) {
            syncEstado();
        }
        estadoEstadia.modificarFechas(this, nuevoCheckin, nuevoCheckout);
    }

    public void finalizar() {
        if (estadoEstadia == null) {
            syncEstado();
        }
        estadoEstadia.finalizar(this);
    }
}
