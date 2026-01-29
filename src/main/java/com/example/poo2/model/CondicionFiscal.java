package com.example.poo2.model;

import jakarta.persistence.*;

/**
 * Entidad que representa las condiciones fiscales de un cliente.
 * Se utiliza para determinar el calculo de impuestos (IVA) en la facturacion.
 * 
 * @author Guadalupe Mendoza
 * @version 1.0
 */
@Entity
@Table(name = "condiciones_fiscales")
public class CondicionFiscal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private CondicionFiscal parent;

    public CondicionFiscal() {
    }

    public CondicionFiscal(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public CondicionFiscal getParent() {
        return parent;
    }

    public void setParent(CondicionFiscal parent) {
        this.parent = parent;
    }
}
