package com.example.poo2.repository;

import com.example.poo2.model.EstadoLiquidacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EstadoLiquidacionRepository extends JpaRepository<EstadoLiquidacion, Long> {
    Optional<EstadoLiquidacion> findByNombre(String nombre);
}
