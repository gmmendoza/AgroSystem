package com.example.poo2.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "movimientos_cuenta_corriente")
public class MovimientoCuentaCorriente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cuenta_corriente_id", nullable = false)
    private CuentaCorriente cuentaCorriente;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private String tipo; // "DEBITO" o "CREDITO"

    @Column(nullable = false)
    private String concepto;

    @Column(nullable = false)
    private Double monto;

    @Column(nullable = false)
    private Double saldoResultante;

    public MovimientoCuentaCorriente() {
    }

    public MovimientoCuentaCorriente(CuentaCorriente cuenta, String tipo, String concepto, Double monto,
            Double saldoResultante) {
        this.cuentaCorriente = cuenta;
        this.fecha = LocalDate.now();
        this.tipo = tipo;
        this.concepto = concepto;
        this.monto = monto;
        this.saldoResultante = saldoResultante;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CuentaCorriente getCuentaCorriente() {
        return cuentaCorriente;
    }

    public void setCuentaCorriente(CuentaCorriente cuentaCorriente) {
        this.cuentaCorriente = cuentaCorriente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Double getSaldoResultante() {
        return saldoResultante;
    }

    public void setSaldoResultante(Double saldoResultante) {
        this.saldoResultante = saldoResultante;
    }
}
