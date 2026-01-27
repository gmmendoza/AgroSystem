package com.example.poo2.service;

import com.example.poo2.model.PrecioTarea;
import com.example.poo2.model.TareaRealizada;
import com.example.poo2.model.TipoTarea;
import com.example.poo2.repository.PrecioTareaRepository;
import com.example.poo2.repository.TareaRealizadaRepository;
import com.example.poo2.repository.TipoTareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TareaService {

    @Autowired
    private TipoTareaRepository tipoTareaRepository;

    @Autowired
    private PrecioTareaRepository precioTareaRepository;

    @Autowired
    private TareaRealizadaRepository tareaRealizadaRepository;

    // TipoTarea methods
    public List<TipoTarea> findAllTipos() {
        return tipoTareaRepository.findAll();
    }

    public TipoTarea saveTipo(TipoTarea tipoTarea) {
        return tipoTareaRepository.save(tipoTarea);
    }

    // PrecioTarea methods
    public PrecioTarea savePrecio(PrecioTarea precioTarea) {
        return precioTareaRepository.save(precioTarea);
    }

    public List<PrecioTarea> findPreciosByTipo(TipoTarea tipoTarea) {
        return precioTareaRepository.findByTipoTarea(tipoTarea);
    }

    public Optional<Double> findPrecioVigente(TipoTarea tipoTarea, LocalDate fecha) {
        return precioTareaRepository.findPrecioVigente(tipoTarea, fecha)
                .map(PrecioTarea::getValor);
    }

    // TareaRealizada methods
    public TareaRealizada registrarTarea(TareaRealizada tarea) {
        // Buscar precio vigente si no se ha establecido o para validar
        Optional<Double> precio = findPrecioVigente(tarea.getTipoTarea(), tarea.getFecha());
        if (precio.isPresent()) {
            tarea.setPrecioAplicado(precio.get());
        } else {
            throw new RuntimeException("No hay precio vigente para la tarea " + tarea.getTipoTarea().getDescripcion() + " en la fecha " + tarea.getFecha());
        }
        return tareaRealizadaRepository.save(tarea);
    }

    public List<TareaRealizada> findAllRealizadas() {
        return tareaRealizadaRepository.findAll();
    }
    
    public List<TareaRealizada> findRealizadasByEmpleado(Long empleadoId) {
        return tareaRealizadaRepository.findByEmpleadoId(empleadoId);
    }
}
