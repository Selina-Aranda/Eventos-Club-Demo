package com.example.demo.modelo;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Eventos_Reservados")
public class EventoReservado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_evento")
    private Evento evento;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Usuario cliente;

    private LocalDate fecha_reservada;
    private LocalTime hora_reservada;

    private String observaciones;
    private String telefono;
    private String metodo_pago;

    
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getMetodo_pago() {
        return metodo_pago;
    }
    public void setMetodo_pago(String metodo_pago) {
        this.metodo_pago = metodo_pago;
    }
    private String estado;

    


    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Evento getEvento() {
        return evento;
    }
    public void setEvento(Evento evento) {
        this.evento = evento;
    }
    public Usuario getCliente() {
        return cliente;
    }
    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }
    public LocalDate getFecha_reservada() {
        return fecha_reservada;
    }
    public void setFecha_reservada(LocalDate fecha_reservada) {
        this.fecha_reservada = fecha_reservada;
    }
    public LocalTime getHora_reservada() {
        return hora_reservada;
    }

    public void setHora_reservada(LocalTime hora_reservada) {
        this.hora_reservada = hora_reservada;
    }
    public String getObservaciones() {
        return observaciones;
    }
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

   
}