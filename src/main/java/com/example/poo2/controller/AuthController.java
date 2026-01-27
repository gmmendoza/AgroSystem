package com.example.poo2.controller;

import com.example.poo2.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador para la autenticacion de usuarios.
 * Maneja las operaciones de login, logout y registro de usuarios.
 * 
 * @author Guadalupe Mendoza
 * @version 1.0
 */
@Controller
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Muestra la pagina de inicio de sesion.
     * 
     * @param error  Parametro opcional que indica si hubo un error de login
     * @param logout Parametro opcional que indica si el usuario cerro sesion
     * @param model  Modelo para pasar datos a la vista
     * @return Nombre de la vista de login
     */
    @GetMapping("/login")
    public String login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {

        if (error != null) {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
        }

        if (logout != null) {
            model.addAttribute("message", "Has cerrado sesion exitosamente");
        }

        return "auth/login";
    }

    /**
     * Muestra la pagina de registro de nuevos usuarios.
     * 
     * @return Nombre de la vista de registro
     */
    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "auth/registro";
    }

    /**
     * Procesa el formulario de registro de nuevos usuarios.
     * Valida los datos y crea el usuario si todo es correcto.
     * 
     * @param username           Nombre de usuario elegido
     * @param password           Contraseña del usuario
     * @param confirmPassword    Confirmacion de la contraseña
     * @param email              Correo electronico del usuario
     * @param nombreCompleto     Nombre completo del usuario
     * @param redirectAttributes Atributos para mensajes flash
     * @return Redireccion a login si exito, o vuelve a registro si error
     */
    @PostMapping("/registro")
    public String procesarRegistro(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            @RequestParam String email,
            @RequestParam String nombreCompleto,
            RedirectAttributes redirectAttributes) {

        // Validar que las contraseñas coincidan
        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Las contraseñas no coinciden");
            return "redirect:/registro";
        }

        // Validar longitud minima de contraseña
        if (password.length() < 6) {
            redirectAttributes.addFlashAttribute("error", "La contraseña debe tener al menos 6 caracteres");
            return "redirect:/registro";
        }

        try {
            // Intentar registrar el usuario
            usuarioService.registrarUsuario(username, password, email, nombreCompleto);
            redirectAttributes.addFlashAttribute("success", "Cuenta creada exitosamente. Ahora puedes iniciar sesion.");
            return "redirect:/login";
        } catch (RuntimeException e) {
            // Capturar errores de validacion (username/email duplicados)
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/registro";
        }
    }

    /**
     * Muestra la pagina de acceso denegado.
     * Se muestra cuando un usuario intenta acceder a un recurso sin permisos.
     * 
     * @return Nombre de la vista de acceso denegado
     */
    @GetMapping("/acceso-denegado")
    public String accesoDenegado() {
        return "auth/acceso-denegado";
    }
}
