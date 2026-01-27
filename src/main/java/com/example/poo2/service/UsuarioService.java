package com.example.poo2.service;

import com.example.poo2.model.Rol;
import com.example.poo2.model.Usuario;
import com.example.poo2.repository.RolRepository;
import com.example.poo2.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio para la gestion de usuarios del sistema.
 * Maneja el registro, validacion y operaciones CRUD de usuarios.
 * 
 * @author Guadalupe Mendoza
 * @version 1.0
 */
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Registra un nuevo usuario en el sistema.
     * La contraseña se encripta automaticamente con BCrypt.
     * Por defecto, los usuarios nuevos reciben el rol OPERADOR.
     * 
     * @param username       Nombre de usuario unico
     * @param password       Contraseña en texto plano (sera encriptada)
     * @param email          Correo electronico del usuario
     * @param nombreCompleto Nombre completo del usuario
     * @return El usuario creado y guardado en la base de datos
     * @throws RuntimeException si el username o email ya existen
     */
    @Transactional
    public Usuario registrarUsuario(String username, String password, String email, String nombreCompleto) {
        // Validar que el username no exista
        if (usuarioRepository.existsByUsername(username)) {
            throw new RuntimeException("El nombre de usuario ya esta en uso");
        }

        // Validar que el email no exista
        if (usuarioRepository.existsByEmail(email)) {
            throw new RuntimeException("El correo electronico ya esta registrado");
        }

        // Obtener rol por defecto (OPERADOR para usuarios auto-registrados)
        Rol rolOperador = rolRepository.findByNombre("OPERADOR")
                .orElseThrow(() -> new RuntimeException("Rol OPERADOR no encontrado en el sistema"));

        // Crear el nuevo usuario con contraseña encriptada
        Usuario nuevoUsuario = new Usuario(
                username,
                passwordEncoder.encode(password),
                email,
                nombreCompleto,
                rolOperador);

        // Guardar y retornar el usuario creado
        return usuarioRepository.save(nuevoUsuario);
    }

    /**
     * Verifica si un nombre de usuario ya existe en el sistema.
     * 
     * @param username El nombre de usuario a verificar
     * @return true si el username ya existe, false en caso contrario
     */
    public boolean existeUsername(String username) {
        return usuarioRepository.existsByUsername(username);
    }

    /**
     * Verifica si un correo electronico ya existe en el sistema.
     * 
     * @param email El correo electronico a verificar
     * @return true si el email ya existe, false en caso contrario
     */
    public boolean existeEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }
}
