package com.example.poo2.repository;

import com.example.poo2.model.TareaRealizada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TareaRealizadaRepository extends JpaRepository<TareaRealizada, Long> {
    List<TareaRealizada> findByEmpleadoId(Long empleadoId);

    @Query("SELECT t FROM TareaRealizada t WHERE " +
            "(:empleadoId IS NULL OR t.empleado.id = :empleadoId) AND " +
            "(:tipoTareaId IS NULL OR t.tipoTarea.id = :tipoTareaId) AND " +
            "(cast(:fechaInicio as date) IS NULL OR t.fecha >= :fechaInicio) AND " +
            "(cast(:fechaFin as date) IS NULL OR t.fecha <= :fechaFin) " +
            "ORDER BY t.fecha DESC")
    List<TareaRealizada> findByFilters(
            @Param("empleadoId") Long empleadoId,
            @Param("tipoTareaId") Long tipoTareaId,
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin);
}
