package com.example.poo2.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "precios_tarea")
public class PrecioTarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double valor;

    @Column(nullable = false)
    private LocalDate fechaVigencia;

    @ManyToOne
    @JoinColumn(name = "tipo_tarea_id", nullable = false)
    private TipoTarea tipoTarea;

    public PrecioTarea() {
    }

    public PrecioTarea(Double valor, LocalDate fechaVigencia, TipoTarea tipoTarea) {
        this.valor = valor;
        this.fechaVigencia = fechaVigencia;
        this.tipoTarea = tipoTarea;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public LocalDate getFechaVigencia() {
        return fechaVigencia;
    }

    public void setFechaVigencia(LocalDate fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }

    public TipoTarea getTipoTarea() {
        return tipoTarea;
    }

    public void setTipoTarea(TipoTarea tipoTarea) {
        this.tipoTarea = tipoTarea;
    }
}
