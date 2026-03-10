package com.example.demo.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "eventos")
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private String descripcion;
    private String tipo;
    private String ruta_imagen;
    private double precio;

    // getters y setters...
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getRuta_imagen() { return ruta_imagen; }
    public void setRuta_imagen(String ruta_imagen) { this.ruta_imagen = ruta_imagen; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
}