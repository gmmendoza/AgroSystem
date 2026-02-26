package com.example.poo2.controller;

import com.example.poo2.model.CuentaCorriente;
import com.example.poo2.model.MovimientoCuentaCorriente;
import com.example.poo2.service.ClienteService;
import com.example.poo2.service.CuentaCorrienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/cuentas-corrientes")
public class CuentaCorrienteController {

    @Autowired
    private CuentaCorrienteService cuentaCorrienteService;

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public String list(Model model) {
        List<CuentaCorriente> cuentas = cuentaCorrienteService.findAll();

        // Stats
        double totalAdeudado = cuentas.stream()
                .filter(c -> c.getSaldo() > 0)
                .mapToDouble(CuentaCorriente::getSaldo).sum();
        long enMora = cuentas.stream()
                .filter(c -> c.getLimiteCredito() > 0 && c.getSaldo() > c.getLimiteCredito())
                .count();
        long aSaldo = cuentas.stream()
                .filter(c -> c.getSaldo() < 0)
                .count();

        model.addAttribute("cuentas", cuentas);
        model.addAttribute("totalAdeudado", totalAdeudado);
        model.addAttribute("enMora", enMora);
        model.addAttribute("aSaldo", aSaldo);
        model.addAttribute("clientes", clienteService.findAll());
        return "cuentas-corrientes/list";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable("id") Long id, Model model,
            @RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {
        CuentaCorriente cuenta = cuentaCorrienteService.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta corriente no encontrada"));

        List<MovimientoCuentaCorriente> movimientos = cuentaCorrienteService.getMovimientos(id);

        model.addAttribute("cuenta", cuenta);
        model.addAttribute("movimientos", movimientos);

        // Data for saldo chart (last 20 movements reversed)
        var movimientosOrdenados = movimientos.stream().limit(20).toList();
        java.util.List<String> chartLabels = new java.util.ArrayList<>();
        java.util.List<Double> chartSaldos = new java.util.ArrayList<>();
        var reversed = new java.util.ArrayList<>(movimientosOrdenados);
        java.util.Collections.reverse(reversed);
        for (var m : reversed) {
            chartLabels.add(m.getFecha().toString());
            chartSaldos.add(m.getSaldoResultante());
        }
        model.addAttribute("chartLabels", chartLabels);
        model.addAttribute("chartSaldos", chartSaldos);
        model.addAttribute("isAjax", "XMLHttpRequest".equals(requestedWith));

        if ("XMLHttpRequest".equals(requestedWith)) {
            return "cuentas-corrientes/detalle :: content-fragment";
        }

        return "cuentas-corrientes/detalle";
    }

    @GetMapping("/cliente/{clienteId}")
    public String detallePorCliente(@PathVariable("clienteId") Long clienteId,
            Model model,
            @RequestHeader(value = "X-Requested-With", required = false) String requestedWith,
            RedirectAttributes redirectAttributes) {
        try {
            CuentaCorriente cuenta = cuentaCorrienteService.findOrCreateByClienteId(clienteId);
            if ("XMLHttpRequest".equals(requestedWith)) {
                return detalle(cuenta.getId(), model, requestedWith);
            }
            return "redirect:/cuentas-corrientes/" + cuenta.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/cuentas-corrientes";
        }
    }

    @PostMapping("/{id}/debito")
    public String registrarDebito(@PathVariable("id") Long id,
            @RequestParam("concepto") String concepto,
            @RequestParam("monto") Double monto,
            RedirectAttributes redirectAttributes) {
        try {
            cuentaCorrienteService.registrarDebito(id, concepto, monto);
            redirectAttributes.addFlashAttribute("success",
                    "Débito registrado: $" + String.format("%.2f", monto));
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/cuentas-corrientes/" + id;
    }

    @PostMapping("/{id}/credito")
    public String registrarCredito(@PathVariable("id") Long id,
            @RequestParam("concepto") String concepto,
            @RequestParam("monto") Double monto,
            RedirectAttributes redirectAttributes) {
        try {
            cuentaCorrienteService.registrarCredito(id, concepto, monto);
            redirectAttributes.addFlashAttribute("success",
                    "Crédito registrado: $" + String.format("%.2f", monto));
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/cuentas-corrientes/" + id;
    }

    @PostMapping("/{id}/limite")
    public String modificarLimite(@PathVariable("id") Long id,
            @RequestParam("limite") Double limite,
            RedirectAttributes redirectAttributes) {
        try {
            cuentaCorrienteService.setLimiteCredito(id, limite);
            redirectAttributes.addFlashAttribute("success", "Límite de crédito actualizado.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/cuentas-corrientes/" + id;
    }

    @PostMapping("/crear/{clienteId}")
    public String crearCuenta(@PathVariable("clienteId") Long clienteId, RedirectAttributes redirectAttributes) {
        try {
            CuentaCorriente cuenta = cuentaCorrienteService.findOrCreateByClienteId(clienteId);
            redirectAttributes.addFlashAttribute("success", "Cuenta corriente creada.");
            return "redirect:/cuentas-corrientes/" + cuenta.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/cuentas-corrientes";
        }
    }
}
