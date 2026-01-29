package com.example.poo2.repository;

import com.example.poo2.model.PrecioTarea;
import com.example.poo2.model.TipoTarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PrecioTareaRepository extends JpaRepository<PrecioTarea, Long> {

    List<PrecioTarea> findByTipoTarea(TipoTarea tipoTarea);

    Optional<PrecioTarea> findTopByTipoTareaAndFechaVigenciaLessThanEqualOrderByFechaVigenciaDesc(TipoTarea tipoTarea,
            LocalDate fecha);

    default Optional<PrecioTarea> findPrecioVigente(TipoTarea tipoTarea, LocalDate fecha) {
        return findTopByTipoTareaAndFechaVigenciaLessThanEqualOrderByFechaVigenciaDesc(tipoTarea, fecha);
    }
}
