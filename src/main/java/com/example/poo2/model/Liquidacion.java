package com.example.poo2.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "liquidaciones")
public class Liquidacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleado empleado;

    @Column(nullable = false)
    private LocalDate periodoInicio;

    @Column(nullable = false)
    private LocalDate periodoFin;

    @Column(nullable = false)
    private LocalDate fechaGeneracion;

    @Column(nullable = false)
    private Double totalBruto = 0.0; // Suma de todas las tareas

    private Double deducciones = 0.0;

    @Column(nullable = false)
    private Double totalNeto = 0.0;

    @ManyToOne
    @JoinColumn(name = "estado_id", nullable = false)
    private EstadoLiquidacion estado;

    private LocalDate fechaPago;

    private String observaciones;

    public Liquidacion() {
    }

    public Liquidacion(Empleado empleado, LocalDate periodoInicio, LocalDate periodoFin) {
        this.empleado = empleado;
        this.periodoInicio = periodoInicio;
        this.periodoFin = periodoFin;
        this.periodoFin = periodoFin;
        this.fechaGeneracion = LocalDate.now();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public LocalDate getPeriodoInicio() {
        return periodoInicio;
    }

    public void setPeriodoInicio(LocalDate periodoInicio) {
        this.periodoInicio = periodoInicio;
    }

    public LocalDate getPeriodoFin() {
        return periodoFin;
    }

    public void setPeriodoFin(LocalDate periodoFin) {
        this.periodoFin = periodoFin;
    }

    public LocalDate getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(LocalDate fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public Double getTotalBruto() {
        return totalBruto;
    }

    public void setTotalBruto(Double totalBruto) {
        this.totalBruto = totalBruto;
    }

    public Double getDeducciones() {
        return deducciones;
    }

    public void setDeducciones(Double deducciones) {
        this.deducciones = deducciones;
    }

    public Double getTotalNeto() {
        return totalNeto;
    }

    public void setTotalNeto(Double totalNeto) {
        this.totalNeto = totalNeto;
    }

    public EstadoLiquidacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoLiquidacion estado) {
        this.estado = estado;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    // Método de negocio
    public void calcularNeto() {
        this.totalNeto = this.totalBruto - (this.deducciones != null ? this.deducciones : 0.0);
    }
}
