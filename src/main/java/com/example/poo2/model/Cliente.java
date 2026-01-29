package com.example.poo2.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Entidad que representa a un Cliente del sistema.
 * Los clientes son las personas o empresas a quienes se les facturan
 * los servicios agricolas realizados.
 * 
 * @author Guadalupe Mendoza
 * @version 1.0
 */
@Entity
@Table(name = "clientes")
public class Cliente {

    /** Identificador unico del cliente */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre o razon social del cliente */
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    @Column(nullable = false)
    private String nombre;

    /** Numero de CUIT (unico para cada cliente) */
    @NotBlank(message = "El CUIT es obligatorio")
    @Size(min = 11, max = 13, message = "El CUIT debe tener 11 dígitos")
    @Column(unique = true, nullable = false)
    private String cuit;

    /** Condicion fiscal del cliente para calculo de IVA */
    @NotNull(message = "La condición fiscal es obligatoria")
    @ManyToOne
    @JoinColumn(name = "condicion_fiscal_id", nullable = false)
    private CondicionFiscal condicionFiscal;

    @ManyToOne
    @JoinColumn(name = "agente_comercial_id")
    private Usuario agenteComercial;

    /** Direccion del cliente */
    private String direccion;

    /** Correo electronico del cliente */
    @Email(message = "El email debe ser válido")
    private String email;

    // Constructores

    public Cliente() {
    }

    public Cliente(String nombre, String cuit, CondicionFiscal condicionFiscal, String direccion, String email) {
        this.nombre = nombre;
        this.cuit = cuit;
        this.condicionFiscal = condicionFiscal;
        this.direccion = direccion;
        this.email = email;
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

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public CondicionFiscal getCondicionFiscal() {
        return condicionFiscal;
    }

    public void setCondicionFiscal(CondicionFiscal condicionFiscal) {
        this.condicionFiscal = condicionFiscal;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Usuario getAgenteComercial() {
        return agenteComercial;
    }

    public void setAgenteComercial(Usuario agenteComercial) {
        this.agenteComercial = agenteComercial;
    }
}
