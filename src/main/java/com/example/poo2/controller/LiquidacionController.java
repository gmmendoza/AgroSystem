package com.example.poo2.controller;

import com.example.poo2.model.Liquidacion;
import com.example.poo2.model.TareaRealizada;
import com.example.poo2.service.EmpleadoService;
import com.example.poo2.service.LiquidacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/liquidaciones")
public class LiquidacionController {

    @Autowired
    private LiquidacionService liquidacionService;

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping
    public String list(@RequestParam(required = false) String estado,
            @RequestParam(required = false) Long empleadoId,
            Model model) {
        List<Liquidacion> liquidaciones;

        if (estado != null && !estado.isBlank()) {
            liquidaciones = liquidacionService.findByEstado(estado);
        } else if (empleadoId != null) {
            liquidaciones = liquidacionService.findByEmpleado(empleadoId);
        } else {
            liquidaciones = liquidacionService.findAll();
        }

        // Sort by fechaGeneracion desc
        liquidaciones.sort((a, b) -> b.getFechaGeneracion().compareTo(a.getFechaGeneracion()));

        model.addAttribute("liquidaciones", liquidaciones);
        model.addAttribute("empleados", empleadoService.findAll());
        model.addAttribute("estadoFiltro", estado);
        model.addAttribute("empleadoFiltro", empleadoId);

        // Stats
        long pendientes = liquidaciones.stream().filter(l -> "Pendiente".equals(l.getEstado().getNombre())).count();
        long aprobadas = liquidaciones.stream().filter(l -> "Aprobada".equals(l.getEstado().getNombre())).count();
        long pagadas = liquidaciones.stream().filter(l -> "Pagada".equals(l.getEstado().getNombre())).count();
        double totalPagado = liquidaciones.stream()
                .filter(l -> "Pagada".equals(l.getEstado().getNombre()))
                .mapToDouble(Liquidacion::getTotalNeto).sum();

        model.addAttribute("statPendientes", pendientes);
        model.addAttribute("statAprobadas", aprobadas);
        model.addAttribute("statPagadas", pagadas);
        model.addAttribute("statTotalPagado", totalPagado);

        return "liquidaciones/list";
    }

    @GetMapping("/nueva")
    public String formularioNueva(Model model) {
        model.addAttribute("empleados", empleadoService.findAll());
        model.addAttribute("hoy", LocalDate.now());
        model.addAttribute("inicioMes", LocalDate.now().withDayOfMonth(1));
        return "liquidaciones/form";
    }

    @PostMapping("/generar")
    public String generar(
            @RequestParam Long empleadoId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin,
            RedirectAttributes redirectAttributes) {
        try {
            Liquidacion liq = liquidacionService.generarLiquidacion(empleadoId, inicio, fin);
            redirectAttributes.addFlashAttribute("success",
                    "Liquidación generada correctamente. Total bruto: $" + String.format("%.2f", liq.getTotalBruto()));
            return "redirect:/liquidaciones/" + liq.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/liquidaciones/nueva";
        }
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        Liquidacion liq = liquidacionService.findById(id)
                .orElseThrow(() -> new RuntimeException("Liquidación no encontrada"));

        List<TareaRealizada> detalle = liquidacionService.getDetalle(id);

        model.addAttribute("liquidacion", liq);
        model.addAttribute("detalle", detalle);

        // Group by tipo tarea for summary
        Map<String, Double> resumenPorTipo = detalle.stream()
                .collect(Collectors.groupingBy(
                        t -> t.getTipoTarea().getDescripcion(),
                        Collectors.summingDouble(t -> t.getCantidad() * t.getPrecioAplicado())));

        model.addAttribute("resumenPorTipo", resumenPorTipo);
        return "liquidaciones/detalle";
    }

    @PostMapping("/{id}/aprobar")
    public String aprobar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            liquidacionService.aprobar(id);
            redirectAttributes.addFlashAttribute("success", "Liquidación aprobada correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al aprobar: " + e.getMessage());
        }
        return "redirect:/liquidaciones/" + id;
    }

    @PostMapping("/{id}/pagar")
    public String pagar(@PathVariable Long id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaPago,
            RedirectAttributes redirectAttributes) {
        try {
            liquidacionService.pagar(id, fechaPago);
            redirectAttributes.addFlashAttribute("success", "Liquidación marcada como PAGADA.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/liquidaciones/" + id;
    }

    @PostMapping("/{id}/cancelar")
    public String cancelar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            liquidacionService.cancelar(id);
            redirectAttributes.addFlashAttribute("info", "Liquidación cancelada.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/liquidaciones/" + id;
    }

    @PostMapping("/{id}/deducciones")
    public String actualizarDeducciones(@PathVariable Long id,
            @RequestParam Double deducciones,
            @RequestParam(required = false) String observaciones,
            RedirectAttributes redirectAttributes) {
        try {
            liquidacionService.actualizarDeducciones(id, deducciones, observaciones);
            redirectAttributes.addFlashAttribute("success", "Deducciones actualizadas.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/liquidaciones/" + id;
    }

    // API para preview antes de generar
    @GetMapping("/api/preview")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> preview(
            @RequestParam Long empleadoId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        try {
            List<TareaRealizada> tareas = liquidacionService.previewLiquidacion(empleadoId, inicio, fin);
            double total = tareas.stream()
                    .mapToDouble(t -> t.getCantidad() * t.getPrecioAplicado())
                    .sum();

            List<Map<String, Object>> items = tareas.stream().map(t -> {
                Map<String, Object> item = new HashMap<>();
                item.put("fecha", t.getFecha().toString());
                item.put("tipo", t.getTipoTarea().getDescripcion());
                item.put("cantidad", t.getCantidad());
                item.put("precio", t.getPrecioAplicado());
                item.put("subtotal", t.getCantidad() * t.getPrecioAplicado());
                return item;
            }).collect(Collectors.toList());

            Map<String, Object> result = new HashMap<>();
            result.put("tareas", items);
            result.put("total", total);
            result.put("cantidad", tareas.size());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}
