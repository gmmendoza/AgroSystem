package com.example.poo2.controller;

import com.example.poo2.model.PrecioTarea;
import com.example.poo2.model.TareaRealizada;
import com.example.poo2.model.TipoTarea;
import com.example.poo2.service.EmpleadoService;
import com.example.poo2.service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/tareas")
public class TareaController {

    @Autowired
    private TareaService tareaService;

    @Autowired
    private EmpleadoService empleadoService;

    // --- Tipos de Tarea ---

    @GetMapping("/tipos")
    public String listTipos(Model model) {
        model.addAttribute("tipos", tareaService.findAllTipos());
        model.addAttribute("tipoTarea", new TipoTarea());
        return "tareas/tipos";
    }

    @PostMapping("/tipos/guardar")
    public String saveTipo(@ModelAttribute TipoTarea tipoTarea) {
        tareaService.saveTipo(tipoTarea);
        return "redirect:/tareas/tipos";
    }

    // --- Precios ---

    @GetMapping("/precios")
    public String listPrecios(@RequestParam(required = false) Long tipoId, Model model) {
        model.addAttribute("tipos", tareaService.findAllTipos());
        if (tipoId != null) {
            // Logic to filter prices by type could go here
        }
        model.addAttribute("precioTarea", new PrecioTarea());
        return "tareas/precios";
    }

    @PostMapping("/precios/guardar")
    public String savePrecio(@ModelAttribute PrecioTarea precioTarea) {
        tareaService.savePrecio(precioTarea);
        return "redirect:/tareas/precios";
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
    public String saveRegistro(@ModelAttribute TareaRealizada tareaRealizada, RedirectAttributes redirectAttributes) {
        try {
            // Ensure date is set if not provided (though form should provide it)
            if (tareaRealizada.getFecha() == null) {
                tareaRealizada.setFecha(LocalDate.now());
            }
            tareaService.registrarTarea(tareaRealizada);
            redirectAttributes.addFlashAttribute("success", "Actividad registrada correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al registrar: " + e.getMessage());
        }
        return "redirect:/tareas/registro";
    }

    @GetMapping("/historial")
    public String listHistorial(Model model) {
        model.addAttribute("tareas", tareaService.findAllRealizadas());
        return "tareas/historial";
    }
}
