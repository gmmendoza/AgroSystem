package com.example.poo2.controller;

import com.example.poo2.model.TareaRealizada;
import com.example.poo2.service.ClienteService;
import com.example.poo2.service.EmpleadoService;
import com.example.poo2.service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

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
                var todasTareas = tareaService.findAllRealizadas();
                long totalTareas = todasTareas.size();
                long totalTipos = tareaService.findAllTipos().size();

                // Calculate total earnings from activities
                double totalGanancias = todasTareas.stream()
                                .mapToDouble(t -> t.getCantidad() * t.getPrecioAplicado())
                                .sum();

                model.addAttribute("totalClientes", totalClientes);
                model.addAttribute("totalEmpleados", totalEmpleados);
                model.addAttribute("totalTareas", totalTareas);
                model.addAttribute("totalTipos", totalTipos);
                model.addAttribute("totalGanancias", totalGanancias);

                // Recent activities (last 5)
                var tareasRecientes = todasTareas.stream()
                                .sorted((a, b) -> b.getFecha().compareTo(a.getFecha()))
                                .limit(5)
                                .collect(Collectors.toList());
                model.addAttribute("tareasRecientes", tareasRecientes);

                // === NUEVOS DATOS PARA DASHBOARD MEJORADO ===

                // 1. Actividades del día (hoy)
                LocalDate hoy = LocalDate.now();
                var tareasHoy = todasTareas.stream()
                                .filter(t -> t.getFecha().equals(hoy))
                                .collect(Collectors.toList());
                double totalHoy = tareasHoy.stream()
                                .mapToDouble(t -> t.getCantidad() * t.getPrecioAplicado())
                                .sum();
                model.addAttribute("tareasHoy", tareasHoy);
                model.addAttribute("totalHoy", totalHoy);

                // 2. Top 5 empleados más productivos (por monto total)
                Map<String, Double> empleadoTotales = new LinkedHashMap<>();
                todasTareas.stream()
                                .filter(t -> t.getEmpleado() != null && t.getEmpleado().getNombre() != null)
                                .collect(Collectors.groupingBy(
                                                t -> t.getEmpleado().getNombre(),
                                                Collectors.summingDouble(t -> t.getCantidad() * t.getPrecioAplicado())))
                                .entrySet().stream()
                                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                                .limit(5)
                                .forEach(e -> empleadoTotales.put(e.getKey(), e.getValue()));
                model.addAttribute("topEmpleados", empleadoTotales);

                // 3. Datos para gráfico de actividades por mes (últimos 6 meses)
                List<String> mesesLabels = new ArrayList<>();
                List<Double> mesesMontos = new ArrayList<>();
                List<Long> mesesCantidades = new ArrayList<>();

                YearMonth mesActual = YearMonth.now();
                for (int i = 5; i >= 0; i--) {
                        YearMonth mes = mesActual.minusMonths(i);
                        String label = mes.getMonth().toString().substring(0, 3) + " " + mes.getYear();
                        mesesLabels.add(label);

                        double montoMes = todasTareas.stream()
                                        .filter(t -> YearMonth.from(t.getFecha()).equals(mes))
                                        .mapToDouble(t -> t.getCantidad() * t.getPrecioAplicado())
                                        .sum();
                        mesesMontos.add(montoMes);

                        long cantidadMes = todasTareas.stream()
                                        .filter(t -> YearMonth.from(t.getFecha()).equals(mes))
                                        .count();
                        mesesCantidades.add(cantidadMes);
                }
                model.addAttribute("mesesLabels", mesesLabels);
                model.addAttribute("mesesMontos", mesesMontos);
                model.addAttribute("mesesCantidades", mesesCantidades);

                // 4. Distribution by Task Type (Pie Chart)
                Map<String, Long> tareasPorTipo = todasTareas.stream()
                                .filter(t -> t.getTipoTarea() != null && t.getTipoTarea().getDescripcion() != null)
                                .collect(Collectors.groupingBy(
                                                t -> t.getTipoTarea().getDescripcion(),
                                                Collectors.counting()));
                model.addAttribute("pieLabels", tareasPorTipo.keySet());
                model.addAttribute("pieData", tareasPorTipo.values());

                // 5. Comparison vs Previous Month
                YearMonth mesPasado = mesActual.minusMonths(1);
                double montoMesActual = mesesMontos.get(5); // Last element
                double montoMesPasado = todasTareas.stream()
                                .filter(t -> YearMonth.from(t.getFecha()).equals(mesPasado))
                                .mapToDouble(t -> t.getCantidad() * t.getPrecioAplicado())
                                .sum();

                double porcentajeCambio = 0;
                if (montoMesPasado > 0) {
                        porcentajeCambio = ((montoMesActual - montoMesPasado) / montoMesPasado) * 100;
                } else if (montoMesActual > 0) {
                        porcentajeCambio = 100;
                }
                model.addAttribute("porcentajeCambio", porcentajeCambio);
                model.addAttribute("tendenciaPositiva", porcentajeCambio >= 0);

                return "home";
        }
}
