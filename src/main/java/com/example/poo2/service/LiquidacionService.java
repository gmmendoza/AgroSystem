package com.example.poo2.service;

import com.example.poo2.model.*;
import com.example.poo2.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LiquidacionService {

    @Autowired
    private LiquidacionRepository liquidacionRepository;

    @Autowired
    private EstadoLiquidacionRepository estadoLiquidacionRepository;

    @Autowired
    private TareaRealizadaRepository tareaRealizadaRepository;

    public List<Liquidacion> findAll() {
        return liquidacionRepository.findAll();
    }

    public Optional<Liquidacion> findById(Long id) {
        return liquidacionRepository.findById(id);
    }

    public List<Liquidacion> findByEmpleado(Long empleadoId) {
        return liquidacionRepository.findByEmpleadoId(empleadoId);
    }

    public List<Liquidacion> findByEstado(String nombreEstado) {
        return estadoLiquidacionRepository.findByNombre(nombreEstado)
                .map(liquidacionRepository::findByEstado)
                .orElse(List.of());
    }

    /**
     * Genera una liquidación calculando el total bruto de las tareas del período.
     */
    public Liquidacion generarLiquidacion(Long empleadoId, LocalDate inicio, LocalDate fin) {
        // Obtener las tareas del período
        List<TareaRealizada> tareas = tareaRealizadaRepository.findByFilters(empleadoId, null, inicio, fin);

        if (tareas.isEmpty()) {
            throw new RuntimeException("No hay actividades registradas para el empleado en el período seleccionado.");
        }

        // Calcular total bruto
        double totalBruto = tareas.stream()
                .mapToDouble(t -> t.getCantidad() * t.getPrecioAplicado())
                .sum();

        // Estado inicial: Pendiente
        EstadoLiquidacion estadoPendiente = estadoLiquidacionRepository.findByNombre("Pendiente")
                .orElseThrow(
                        () -> new RuntimeException("Estado 'Pendiente' no encontrado. Verifique la base de datos."));

        Empleado empleado = new Empleado();
        empleado.setId(empleadoId);

        Liquidacion liq = new Liquidacion(empleado, inicio, fin);
        liq.setTotalBruto(totalBruto);
        liq.setDeducciones(0.0);
        liq.setTotalNeto(totalBruto);
        liq.setEstado(estadoPendiente);

        return liquidacionRepository.save(liq);
    }

    /**
     * Retorna las tareas realizadas que conforman el detalle de la liquidación.
     */
    public List<TareaRealizada> getDetalle(Long liquidacionId) {
        return liquidacionRepository.findById(liquidacionId).map(liq -> tareaRealizadaRepository.findByFilters(
                liq.getEmpleado().getId(), null,
                liq.getPeriodoInicio(), liq.getPeriodoFin())).orElse(List.of());
    }

    /**
     * Obtener preview de tareas y total para un período antes de generar.
     */
    public List<TareaRealizada> previewLiquidacion(Long empleadoId, LocalDate inicio, LocalDate fin) {
        return tareaRealizadaRepository.findByFilters(empleadoId, null, inicio, fin);
    }

    public Liquidacion aprobar(Long id) {
        Liquidacion liq = liquidacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Liquidación no encontrada"));

        EstadoLiquidacion estadoAprobada = estadoLiquidacionRepository.findByNombre("Aprobada")
                .orElseThrow(() -> new RuntimeException("Estado 'Aprobada' no configurado"));

        liq.setEstado(estadoAprobada);
        return liquidacionRepository.save(liq);
    }

    public Liquidacion pagar(Long id, LocalDate fechaPago) {
        Liquidacion liq = liquidacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Liquidación no encontrada"));

        EstadoLiquidacion estadoPagada = estadoLiquidacionRepository.findByNombre("Pagada")
                .orElseThrow(() -> new RuntimeException("Estado 'Pagada' no configurado"));

        liq.setEstado(estadoPagada);
        liq.setFechaPago(fechaPago != null ? fechaPago : LocalDate.now());
        return liquidacionRepository.save(liq);
    }

    public Liquidacion cancelar(Long id) {
        Liquidacion liq = liquidacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Liquidación no encontrada"));

        EstadoLiquidacion estadoCancelada = estadoLiquidacionRepository.findByNombre("Cancelada")
                .orElseThrow(() -> new RuntimeException("Estado 'Cancelada' no configurado"));

        liq.setEstado(estadoCancelada);
        return liquidacionRepository.save(liq);
    }

    public Liquidacion actualizarDeducciones(Long id, Double deducciones, String observaciones) {
        Liquidacion liq = liquidacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Liquidación no encontrada"));
        liq.setDeducciones(deducciones != null ? deducciones : 0.0);
        liq.calcularNeto();
        if (observaciones != null)
            liq.setObservaciones(observaciones);
        return liquidacionRepository.save(liq);
    }
}
