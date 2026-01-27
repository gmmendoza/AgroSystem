package com.example.poo2.controller;

import com.example.poo2.model.Usuario;
import com.example.poo2.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/perfil")
public class PerfilController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String verPerfil(Authentication authentication, Model model) {
        String username = authentication.getName();
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        model.addAttribute("usuario", usuario);
        return "perfil";
    }

    @PostMapping("/cambiar-password")
    public String cambiarPassword(
            @RequestParam String passwordActual,
            @RequestParam String passwordNueva,
            @RequestParam String passwordConfirm,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        String username = authentication.getName();
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Validar contraseña actual
        if (!passwordEncoder.matches(passwordActual, usuario.getPassword())) {
            redirectAttributes.addFlashAttribute("error", "La contraseña actual es incorrecta.");
            return "redirect:/perfil";
        }

        // Validar que las nuevas coincidan
        if (!passwordNueva.equals(passwordConfirm)) {
            redirectAttributes.addFlashAttribute("error", "Las contraseñas nuevas no coinciden.");
            return "redirect:/perfil";
        }

        // Validar longitud mínima
        if (passwordNueva.length() < 6) {
            redirectAttributes.addFlashAttribute("error", "La nueva contraseña debe tener al menos 6 caracteres.");
            return "redirect:/perfil";
        }

        // Actualizar contraseña
        usuario.setPassword(passwordEncoder.encode(passwordNueva));
        usuarioRepository.save(usuario);

        redirectAttributes.addFlashAttribute("success", "¡Contraseña actualizada exitosamente!");
        return "redirect:/perfil";
    }

    @PostMapping("/actualizar")
    public String actualizarPerfil(
            @RequestParam String email,
            @RequestParam String nombreCompleto,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        String username = authentication.getName();
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setEmail(email);
        usuario.setNombreCompleto(nombreCompleto);
        usuarioRepository.save(usuario);

        redirectAttributes.addFlashAttribute("success", "¡Perfil actualizado exitosamente!");
        return "redirect:/perfil";
    }
}
