package com.example.poo2.model;

import jakarta.persistence.*;

@Entity
@Table(name = "unidades_medida")
public class UnidadMedida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "parent_unit_id")
    private UnidadMedida unidadBase;

    public UnidadMedida() {
    }

    public UnidadMedida(String nombre) {
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

    public UnidadMedida getUnidadBase() {
        return unidadBase;
    }

    public void setUnidadBase(UnidadMedida unidadBase) {
        this.unidadBase = unidadBase;
    }
}
