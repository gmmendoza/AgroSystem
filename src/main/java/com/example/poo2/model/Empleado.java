package com.example.poo2.model;

import jakarta.persistence.*;

/**
 * Entidad que representa a un Empleado de la empresa.
 * Los empleados realizan las tareas agricolas que luego
 * son facturadas a los clientes.
 * 
 * @author Guadalupe Mendoza
 * @version 1.0
 */
@Entity
@Table(name = "empleados")
public class Empleado {

    /** Identificador unico del empleado */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre completo del empleado */
    @Column(nullable = false)
    private String nombre;

    /** Numero de legajo unico del empleado */
    @Column(unique = true, nullable = false)
    private String legajo;

    /** Puesto o cargo del empleado en la empresa */
    private String puesto;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // Constructores

    public Empleado() {
    }

    public Empleado(String nombre, String legajo, String puesto) {
        this.nombre = nombre;
        this.legajo = legajo;
        this.puesto = puesto;
    }

    // Getters y Setters

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

    public String getLegajo() {
        return legajo;
    }

    public void setLegajo(String legajo) {
        this.legajo = legajo;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
