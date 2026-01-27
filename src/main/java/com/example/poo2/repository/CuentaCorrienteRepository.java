package com.example.poo2.repository;

import com.example.poo2.model.CuentaCorriente;
import com.example.poo2.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CuentaCorrienteRepository extends JpaRepository<CuentaCorriente, Long> {
    Optional<CuentaCorriente> findByCliente(Cliente cliente);

    Optional<CuentaCorriente> findByClienteId(Long clienteId);
}
