package com.example.poo2.repository;

import com.example.poo2.model.TareaRealizada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TareaRealizadaRepository extends JpaRepository<TareaRealizada, Long> {
    List<TareaRealizada> findByEmpleadoId(Long empleadoId);
}
