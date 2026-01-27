package com.example.poo2.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tareas_realizadas")
public class TareaRealizada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private Double cantidad;

    @Column(nullable = false)
    private Double precioAplicado;

    @ManyToOne
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "tipo_tarea_id", nullable = false)
    private TipoTarea tipoTarea;

    public TareaRealizada() {
    }

    public TareaRealizada(LocalDate fecha, Double cantidad, Double precioAplicado, Empleado empleado, TipoTarea tipoTarea) {
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.precioAplicado = precioAplicado;
        this.empleado = empleado;
        this.tipoTarea = tipoTarea;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioAplicado() {
        return precioAplicado;
    }

    public void setPrecioAplicado(Double precioAplicado) {
        this.precioAplicado = precioAplicado;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public TipoTarea getTipoTarea() {
        return tipoTarea;
    }

    public void setTipoTarea(TipoTarea tipoTarea) {
        this.tipoTarea = tipoTarea;
    }
}
