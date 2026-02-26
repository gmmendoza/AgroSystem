package com.example.poo2.controller;

import com.example.poo2.model.Almacen;
import com.example.poo2.service.AlmacenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/almacenes")
public class AlmacenController {

    @Autowired
    private AlmacenService almacenService;

    @GetMapping
    public String list(@RequestParam(required = false) String filtro, Model model) {
        var todos = almacenService.findAll();
        var mostrar = todos;
        if ("activos".equals(filtro)) {
            mostrar = todos.stream().filter(Almacen::isActivo).toList();
        } else if ("inactivos".equals(filtro)) {
            mostrar = todos.stream().filter(a -> !a.isActivo()).toList();
        }
        model.addAttribute("almacenes", mostrar);
        model.addAttribute("filtro", filtro);
        model.addAttribute("totalActivos", todos.stream().filter(Almacen::isActivo).count());
        model.addAttribute("totalInactivos", todos.stream().filter(a -> !a.isActivo()).count());
        return "almacenes/list";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("almacen", new Almacen());
        model.addAttribute("editando", false);
        return "almacenes/form";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable("id") Long id, Model model) {
        almacenService.findById(id).ifPresent(a -> model.addAttribute("almacen", a));
        model.addAttribute("editando", true);
        return "almacenes/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Almacen almacen, RedirectAttributes redirectAttributes) {
        try {
            almacenService.save(almacen);
            String msg = (almacen.getId() != null) ? "Almacén actualizado." : "Almacén creado correctamente.";
            redirectAttributes.addFlashAttribute("success", msg);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/almacenes";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            almacenService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Almacén eliminado.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "No se puede eliminar el almacén.");
        }
        return "redirect:/almacenes";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable("id") Long id, Model model) {
        almacenService.findById(id).ifPresent(a -> model.addAttribute("almacen", a));
        return "almacenes/detalle";
    }
}
