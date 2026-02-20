package com.example.poo2.repository;

import com.example.poo2.model.MovimientoCuentaCorriente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimientoCuentaCorrienteRepository extends JpaRepository<MovimientoCuentaCorriente, Long> {
    List<MovimientoCuentaCorriente> findByCuentaCorrienteIdOrderByFechaDesc(Long cuentaCorrienteId);
}
