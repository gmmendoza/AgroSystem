package com.example.poo2.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "cuentas_corrientes")
public class CuentaCorriente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "cliente_id", nullable = false, unique = true)
    private Cliente cliente;

    @Column(nullable = false)
    private Double saldo = 0.0; // Saldo actual (positivo = debe, negativo = a favor)

    @Column(nullable = false)
    private Double limiteCredito = 0.0;

    private LocalDate fechaUltimoMovimiento;

    private boolean activa = true;

    public CuentaCorriente() {
    }

    public CuentaCorriente(Cliente cliente, Double limiteCredito) {
        this.cliente = cliente;
        this.saldo = 0.0;
        this.limiteCredito = limiteCredito;
        this.fechaUltimoMovimiento = LocalDate.now();
        this.activa = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Double getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(Double limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    public LocalDate getFechaUltimoMovimiento() {
        return fechaUltimoMovimiento;
    }

    public void setFechaUltimoMovimiento(LocalDate fechaUltimoMovimiento) {
        this.fechaUltimoMovimiento = fechaUltimoMovimiento;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    // Método de negocio para verificar disponibilidad de crédito
    public boolean tieneCredito(Double monto) {
        return (saldo + monto) <= limiteCredito;
    }
}
