package com.example.poo2.controller;

import com.example.poo2.service.ClienteService;
import com.example.poo2.service.EmpleadoService;
import com.example.poo2.service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private TareaService tareaService;

    @GetMapping("/")
    public String dashboard(Model model) {
        // Statistics
        long totalClientes = clienteService.findAll().size();
        long totalEmpleados = empleadoService.findAll().size();
        long totalTareas = tareaService.findAllRealizadas().size();
        long totalTipos = tareaService.findAllTipos().size();

        // Calculate total earnings from activities
        double totalGanancias = tareaService.findAllRealizadas().stream()
                .mapToDouble(t -> t.getCantidad() * t.getPrecioAplicado())
                .sum();

        model.addAttribute("totalClientes", totalClientes);
        model.addAttribute("totalEmpleados", totalEmpleados);
        model.addAttribute("totalTareas", totalTareas);
        model.addAttribute("totalTipos", totalTipos);
        model.addAttribute("totalGanancias", totalGanancias);

        // Recent activities (last 5)
        var todasTareas = tareaService.findAllRealizadas();
        var tareasRecientes = todasTareas.size() > 5
                ? todasTareas.subList(Math.max(todasTareas.size() - 5, 0), todasTareas.size())
                : todasTareas;
        model.addAttribute("tareasRecientes", tareasRecientes);

        return "home";
    }
}
