package com.example.poo2.repository;

import com.example.poo2.model.Liquidacion;
import com.example.poo2.model.Empleado;
import com.example.poo2.model.EstadoLiquidacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LiquidacionRepository extends JpaRepository<Liquidacion, Long> {
    List<Liquidacion> findByEmpleado(Empleado empleado);

    List<Liquidacion> findByEmpleadoId(Long empleadoId);

    List<Liquidacion> findByEstado(EstadoLiquidacion estado);

    List<Liquidacion> findByPeriodoInicioBetween(LocalDate inicio, LocalDate fin);
}
