package com.example.poo2.model;

/**
 * Enumeracion que representa las condiciones fiscales de un cliente.
 * Se utiliza para determinar el calculo de impuestos (IVA) en la facturacion.
 * 
 * @author Guadalupe Mendoza
 * @version 1.0
 */
public enum CondicionFiscal {
    /** Responsable Inscripto - IVA 21% */
    RESPONSABLE_INSCRIPTO,

    /** Monotributista - Sin discriminacion de IVA */
    MONOTRIBUTISTA,

    /** Exento de IVA */
    EXENTO,

    /** Consumidor Final - IVA incluido */
    CONSUMIDOR_FINAL
}
