package com.example.poo2.model;

import jakarta.persistence.*;

@Entity
@Table(name = "estados_liquidacion")
public class EstadoLiquidacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "estado_padre_id")
    private EstadoLiquidacion estadoPadre;

    public EstadoLiquidacion() {
    }

    public EstadoLiquidacion(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public EstadoLiquidacion getEstadoPadre() {
        return estadoPadre;
    }

    public void setEstadoPadre(EstadoLiquidacion estadoPadre) {
        this.estadoPadre = estadoPadre;
    }
}
