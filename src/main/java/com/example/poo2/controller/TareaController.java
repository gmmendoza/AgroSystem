package com.example.poo2.controller;

import com.example.poo2.model.Empleado;
import com.example.poo2.model.PrecioTarea;
import com.example.poo2.model.TareaRealizada;
import com.example.poo2.model.TipoTarea;
import com.example.poo2.model.UnidadMedida;
import com.example.poo2.service.EmpleadoService;
import com.example.poo2.service.TareaService;
import com.example.poo2.repository.UnidadMedidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/tareas")
public class TareaController {

    @Autowired
    private TareaService tareaService;

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private UnidadMedidaRepository unidadMedidaRepository;

    // --- Tipos de Tarea ---

    @GetMapping("/tipos")
    public String listTipos(Model model) {
        List<TipoTarea> tipos = tareaService.findAllTipos();
        // Enrich each type with its current price
        Map<Long, Double> preciosVigentes = new HashMap<>();
        LocalDate hoy = LocalDate.now();
        for (TipoTarea tipo : tipos) {
            tareaService.findPrecioVigente(tipo, hoy)
                    .ifPresent(precio -> preciosVigentes.put(tipo.getId(), precio));
        }
        model.addAttribute("tipos", tipos);
        model.addAttribute("preciosVigentes", preciosVigentes);
        model.addAttribute("tipoTarea", new TipoTarea());
        model.addAttribute("unidadesMedida", unidadMedidaRepository.findAll());
        model.addAttribute("editando", false);
        return "tareas/tipos";
    }

    @GetMapping("/tipos/editar/{id}")
    public String editarTipo(@PathVariable("id") Long id, Model model) {
        tareaService.findTipoById(id).ifPresent(tipo -> model.addAttribute("tipoTarea", tipo));
        model.addAttribute("tipos", tareaService.findAllTipos());
        Map<Long, Double> preciosVigentes = new HashMap<>();
        LocalDate hoy = LocalDate.now();
        for (TipoTarea tipo : tareaService.findAllTipos()) {
            tareaService.findPrecioVigente(tipo, hoy)
                    .ifPresent(precio -> preciosVigentes.put(tipo.getId(), precio));
        }
        model.addAttribute("preciosVigentes", preciosVigentes);
        model.addAttribute("unidadesMedida", unidadMedidaRepository.findAll());
        model.addAttribute("editando", true);
        return "tareas/tipos";
    }

    @PostMapping("/tipos/guardar")
    public String saveTipo(@ModelAttribute TipoTarea tipoTarea,
            @RequestParam(name = "unidadMedidaId", required = false) Long unidadMedidaId,
            RedirectAttributes redirectAttributes) {
        if (unidadMedidaId != null) {
            UnidadMedida um = new UnidadMedida();
            um.setId(unidadMedidaId);
            tipoTarea.setUnidadMedida(um);
        }
        tareaService.saveTipo(tipoTarea);
        String msg = (tipoTarea.getId() != null) ? "Tipo de tarea actualizado correctamente."
                : "Tipo de tarea creado correctamente.";
        redirectAttributes.addFlashAttribute("success", msg);
        return "redirect:/tareas/tipos";
    }

    @GetMapping("/tipos/eliminar/{id}")
    public String eliminarTipo(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            tareaService.deleteTipo(id);
            redirectAttributes.addFlashAttribute("success", "Tipo de tarea eliminado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "No se puede eliminar: el tipo tiene actividades registradas.");
        }
        return "redirect:/tareas/tipos";
    }

    // --- Precios ---

    @GetMapping("/precios")
    public String listPrecios(@RequestParam(name = "tipoId", required = false) Long tipoId, Model model) {
        List<TipoTarea> tipos = tareaService.findAllTipos();
        model.addAttribute("tipos", tipos);
        model.addAttribute("tipoId", tipoId);

        // Prepare all prices per type as JSON-ready structure for the chart
        // tiposConPrecios: list of { id, descripcion, precios: [{fecha, valor}] }
        java.util.List<Map<String, Object>> tiposConPrecios = new java.util.ArrayList<>();
        for (TipoTarea tipo : tipos) {
            Map<String, Object> entry = new HashMap<>();
            entry.put("id", tipo.getId());
            entry.put("descripcion", tipo.getDescripcion());
            List<Map<String, Object>> precios = tareaService.findPreciosByTipo(tipo).stream()
                    .sorted(java.util.Comparator.comparing(p -> p.getFechaVigencia()))
                    .map(p -> {
                        Map<String, Object> m = new HashMap<>();
                        m.put("fecha", p.getFechaVigencia().toString());
                        m.put("valor", p.getValor());
                        return m;
                    })
                    .collect(Collectors.toList());
            entry.put("precios", precios);
            tiposConPrecios.add(entry);
        }
        model.addAttribute("tiposConPrecios", tiposConPrecios);

        // Precios vigentes hoy (for sidebar summary + JS inline map)
        Map<Long, Double> preciosVigentes = new HashMap<>();
        LocalDate hoy = LocalDate.now();
        for (TipoTarea tipo : tipos) {
            tareaService.findPrecioVigente(tipo, hoy)
                    .ifPresent(precio -> preciosVigentes.put(tipo.getId(), precio));
        }
        model.addAttribute("preciosVigentes", preciosVigentes);
        // Convert to String keys for Thymeleaf inline JS compatibility
        Map<String, Object> preciosVigentesDataMap = new HashMap<>();
        preciosVigentes.forEach((k, v) -> preciosVigentesDataMap.put(String.valueOf(k), v));
        model.addAttribute("preciosVigentesDataMap", preciosVigentesDataMap);

        PrecioTarea precioTarea = new PrecioTarea();
        if (tipoId != null) {
            TipoTarea tipo = new TipoTarea();
            tipo.setId(tipoId);
            precioTarea.setTipoTarea(tipo);
        }
        model.addAttribute("precioTarea", precioTarea);
        return "tareas/precios";
    }

    @PostMapping("/precios/guardar")
    public String savePrecio(@RequestParam("tipoTareaId") Long tipoTareaId,
            @RequestParam("valor") Double valor,
            @RequestParam("fechaVigencia") @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) LocalDate fechaVigencia,
            RedirectAttributes redirectAttributes) {
        try {
            PrecioTarea precioTarea = new PrecioTarea();
            TipoTarea tipo = new TipoTarea();
            tipo.setId(tipoTareaId);
            precioTarea.setTipoTarea(tipo);
            precioTarea.setValor(valor);
            precioTarea.setFechaVigencia(fechaVigencia);
            tareaService.savePrecio(precioTarea);
            redirectAttributes.addFlashAttribute("success", "Precio guardado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar precio: " + e.getMessage());
        }
        return "redirect:/tareas/precios?tipoId=" + tipoTareaId;
    }

    // --- Registro de Actividad ---

    @GetMapping("/registro")
    public String formRegistro(Model model) {
        model.addAttribute("tareaRealizada", new TareaRealizada());
        model.addAttribute("empleados", empleadoService.findAll());
        model.addAttribute("tipos", tareaService.findAllTipos());
        return "tareas/registro";
    }

    @PostMapping("/registro/guardar")
    public String saveRegistro(@ModelAttribute TareaRealizada tareaRealizada,
            @RequestParam(name = "empleadoIds", required = false) List<Long> empleadoIds,
            RedirectAttributes redirectAttributes) {
        try {
            if (tareaRealizada.getFecha() == null) {
                tareaRealizada.setFecha(LocalDate.now());
            }

            if (empleadoIds != null && !empleadoIds.isEmpty()) {
                for (Long empId : empleadoIds) {
                    TareaRealizada t = new TareaRealizada();
                    t.setFecha(tareaRealizada.getFecha());
                    t.setTipoTarea(tareaRealizada.getTipoTarea());
                    t.setCantidad(tareaRealizada.getCantidad());

                    Empleado e = new Empleado();
                    e.setId(empId);
                    t.setEmpleado(e);

                    tareaService.registrarTarea(t);
                }
                redirectAttributes.addFlashAttribute("success",
                        "Actividad registrada para " + empleadoIds.size() + " empleados.");
            } else {
                tareaService.registrarTarea(tareaRealizada);
                redirectAttributes.addFlashAttribute("success", "Actividad registrada correctamente.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al registrar: " + e.getMessage());
        }
        return "redirect:/actividades";
    }

    @GetMapping("/historial")
    public String listHistorial(
            @RequestParam(name = "empleadoId", required = false) Long empleadoId,
            @RequestParam(name = "tipoTareaId", required = false) Long tipoTareaId,
            @RequestParam(name = "fechaInicio", required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam(name = "fechaFin", required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            Model model) {

        model.addAttribute("tareas", tareaService.filtrarTareas(empleadoId, tipoTareaId, fechaInicio, fechaFin));
        model.addAttribute("empleados", empleadoService.findAll());
        model.addAttribute("tiposTarea", tareaService.findAllTipos());

        model.addAttribute("empleadoId", empleadoId);
        model.addAttribute("tipoTareaId", tipoTareaId);
        model.addAttribute("fechaInicio", fechaInicio);
        model.addAttribute("fechaFin", fechaFin);

        return "tareas/historial";
    }

    // --- Editar Actividad ---

    @GetMapping("/editar/{id}")
    public String editarActividad(@PathVariable("id") Long id, Model model) {
        tareaService.findRealizadaById(id).ifPresent(tarea -> {
            model.addAttribute("tareaRealizada", tarea);
        });
        model.addAttribute("empleados", empleadoService.findAll());
        model.addAttribute("tipos", tareaService.findAllTipos());
        model.addAttribute("editando", true);
        return "tareas/registro";
    }

    @PostMapping("/actualizar")
    public String actualizarActividad(@ModelAttribute TareaRealizada tareaRealizada,
            RedirectAttributes redirectAttributes) {
        try {
            tareaService.actualizarTarea(tareaRealizada);
            redirectAttributes.addFlashAttribute("success", "Actividad actualizada correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar: " + e.getMessage());
        }
        return "redirect:/actividades";
    }

    // --- Eliminar Actividad ---

    @GetMapping("/eliminar/{id}")
    public String eliminarActividad(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            tareaService.eliminarTarea(id);
            redirectAttributes.addFlashAttribute("success", "Actividad eliminada correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar: " + e.getMessage());
        }
        return "redirect:/actividades";
    }

    // --- API for Frontend ---

    @GetMapping("/api/precios/{id}")
    @ResponseBody
    public List<Map<String, Object>> getPreciosPorTipo(@PathVariable("id") Long id) {
        TipoTarea tipo = new TipoTarea();
        tipo.setId(id);
        return tareaService.findPreciosByTipo(tipo).stream()
                .map(p -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("fecha", p.getFechaVigencia());
                    map.put("valor", p.getValor());
                    return map;
                })
                .sorted((a, b) -> ((LocalDate) a.get("fecha"))
                        .compareTo((LocalDate) b.get("fecha")))
                .collect(Collectors.toList());
    }

    @GetMapping("/api/check-precio")
    @ResponseBody
    public Map<String, Object> checkPrecio(@RequestParam("tipoId") Long tipoId, @RequestParam("fecha") String fecha) {
        Map<String, Object> response = new HashMap<>();
        try {
            TipoTarea tipo = new TipoTarea();
            tipo.setId(tipoId);
            LocalDate fechaDate = LocalDate.parse(fecha);

            java.util.Optional<Double> precio = tareaService.findPrecioVigente(tipo, fechaDate);

            if (precio.isPresent()) {
                response.put("valid", true);
                response.put("precio", precio.get());
            } else {
                response.put("valid", false);
                response.put("message", "No hay precio vigente para esta fecha.");
            }
        } catch (Exception e) {
            response.put("valid", false);
            response.put("message", "Error al validar precio: " + e.getMessage());
        }
        return response;
    }
}
