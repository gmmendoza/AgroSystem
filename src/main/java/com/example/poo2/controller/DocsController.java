package com.example.poo2.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Controlador para la seccion de documentacion del sistema.
 * Sirve los archivos de documentacion (ERP, DP-Iteracion, Retrospectiva)
 * en formato HTML para visualizacion en el navegador.
 * 
 * @author Guadalupe Mendoza
 * @version 1.0
 */
@Controller
@RequestMapping("/docs")
public class DocsController {

    /**
     * Muestra la pagina principal de documentacion.
     * Carga el contenido de los archivos markdown y los pasa a la vista.
     * 
     * @param model Modelo para pasar datos a la vista
     * @return Nombre de la vista de documentacion
     */
    @GetMapping
    public String mostrarDocumentacion(Model model) {
        // Cargar contenido de los archivos de documentacion desde classpath
        model.addAttribute("erp", leerArchivo("docs/erp.md"));
        model.addAttribute("dpIteracion1", leerArchivo("docs/dp-iteracion-1.md"));
        model.addAttribute("retrospectiva", leerArchivo("docs/retrospectiva-iteracion-1.md"));
        model.addAttribute("roadmap", leerArchivo("docs/roadmap.md"));

        return "documentacion/index";
    }

    /**
     * Lee el contenido de un archivo de documentacion desde el classpath.
     * 
     * @param resourcePath Ruta del recurso en el classpath
     * @return Contenido del archivo como String, o mensaje de error si no existe
     */
    private String leerArchivo(String resourcePath) {
        try {
            Resource resource = new ClassPathResource(resourcePath);
            if (resource.exists()) {
                return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            } else {
                return "Archivo no encontrado: " + resourcePath;
            }
        } catch (IOException e) {
            return "Error al leer el archivo: " + e.getMessage();
        }
    }
}
