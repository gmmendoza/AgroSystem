package com.example.poo2.controller;

import com.example.poo2.model.Empleado;
import com.example.poo2.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("empleados", empleadoService.findAll());
        return "empleados/list";
    }

    @GetMapping("/nuevo")
    public String form(Model model) {
        model.addAttribute("empleado", new Empleado());
        return "empleados/form";
    }

    @PostMapping("/guardar")
    public String save(@ModelAttribute Empleado empleado) {
        empleadoService.save(empleado);
        return "redirect:/empleados";
    }

    @GetMapping("/editar/{id}")
    public String edit(@PathVariable Long id, Model model) {
        empleadoService.findById(id).ifPresent(empleado -> model.addAttribute("empleado", empleado));
        return "empleados/form";
    }

    @GetMapping("/eliminar/{id}")
    public String delete(@PathVariable Long id) {
        empleadoService.deleteById(id);
        return "redirect:/empleados";
    }
}
