package com.example.poo2.controller;

import com.example.poo2.model.TareaRealizada;
import com.example.poo2.model.TipoTarea;
import com.example.poo2.service.EmpleadoService;
import com.example.poo2.service.TareaService;
import com.example.poo2.repository.UnidadMedidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/actividades")
public class ActividadesController {

        @Autowired
        private TareaService tareaService;

        @Autowired
        private EmpleadoService empleadoService;

        @Autowired
        private UnidadMedidaRepository unidadMedidaRepository;

        @GetMapping
        public String index(
                        @org.springframework.web.bind.annotation.RequestParam(required = false) Long empleadoId,
                        @org.springframework.web.bind.annotation.RequestParam(required = false) Long tipoTareaId,
                        @org.springframework.web.bind.annotation.RequestParam(required = false) String fechaInicio,
                        @org.springframework.web.bind.annotation.RequestParam(required = false) String fechaFin,
                        @org.springframework.web.bind.annotation.RequestParam(defaultValue = "0") int page,
                        @org.springframework.web.bind.annotation.RequestParam(defaultValue = "10") int size,
                        Model model) {

                java.time.LocalDate inicio = (fechaInicio != null && !fechaInicio.isEmpty())
                                ? java.time.LocalDate.parse(fechaInicio)
                                : null;
                java.time.LocalDate fin = (fechaFin != null && !fechaFin.isEmpty())
                                ? java.time.LocalDate.parse(fechaFin)
                                : null;

                org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page,
                                size);

                org.springframework.data.domain.Page<TareaRealizada> tareasPage = tareaService.filtrarTareasPaginado(
                                empleadoId,
                                tipoTareaId, inicio, fin, pageable);

                // Data for Tabs and Modals
                model.addAttribute("tareasPage", tareasPage);
                model.addAttribute("tareas", tareasPage.getContent());
                model.addAttribute("empleados", empleadoService.findAll());

                List<TipoTarea> tipos = tareaService.findAllTipos();
                model.addAttribute("tipos", tipos);

                Map<Long, Boolean> tareasConPrecio = new HashMap<>();
                // Check active price for TODAY
                java.time.LocalDate today = java.time.LocalDate.now();
                tipos.forEach(t -> {
                        tareasConPrecio.put(t.getId(), tareaService.findPrecioVigente(t, today).isPresent());
                });
                model.addAttribute("tareasConPrecio", tareasConPrecio);
                model.addAttribute("unidadesMedida", unidadMedidaRepository.findAll());

                // Pass filter params back
                model.addAttribute("empleadoId", empleadoId);
                model.addAttribute("tipoTareaId", tipoTareaId);
                model.addAttribute("fechaInicio", fechaInicio);
                model.addAttribute("fechaFin", fechaFin);

                // Form Objects
                model.addAttribute("nuevaTarea", new TareaRealizada());
                model.addAttribute("nuevoTipo", new TipoTarea());

                return "actividades/index";
        }
}
