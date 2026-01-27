package com.example.poo2.controller;

import com.example.poo2.model.Cliente;
import com.example.poo2.model.CondicionFiscal;
import com.example.poo2.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("clientes", clienteService.findAll());
        return "clientes/list";
    }

    @GetMapping("/nuevo")
    public String form(Model model) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("condicionesFiscales", CondicionFiscal.values());
        return "clientes/form";
    }

    @PostMapping("/guardar")
    public String save(@ModelAttribute Cliente cliente) {
        clienteService.save(cliente);
        return "redirect:/clientes";
    }

    @GetMapping("/editar/{id}")
    public String edit(@PathVariable Long id, Model model) {
        clienteService.findById(id).ifPresent(cliente -> model.addAttribute("cliente", cliente));
        model.addAttribute("condicionesFiscales", CondicionFiscal.values());
        return "clientes/form";
    }

    @GetMapping("/eliminar/{id}")
    public String delete(@PathVariable Long id) {
        clienteService.deleteById(id);
        return "redirect:/clientes";
    }
}
