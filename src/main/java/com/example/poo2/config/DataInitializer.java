package com.example.poo2.config;

import com.example.poo2.model.EstadoLiquidacion;
import com.example.poo2.model.Rol;
import com.example.poo2.model.UnidadMedida;
import com.example.poo2.model.Usuario;
import com.example.poo2.repository.EstadoLiquidacionRepository;
import com.example.poo2.repository.RolRepository;
import com.example.poo2.repository.UnidadMedidaRepository;
import com.example.poo2.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EstadoLiquidacionRepository estadoLiquidacionRepository;

    @Autowired
    private UnidadMedidaRepository unidadMedidaRepository;

    @Override
    public void run(String... args) throws Exception {
        // Crear roles si no existen
        Rol rolAdmin = rolRepository.findByNombre("ADMIN")
                .orElseGet(() -> {
                    Rol rol = new Rol("ADMIN", "Administrador del sistema con acceso total");
                    return rolRepository.save(rol);
                });

        rolRepository.findByNombre("ENCARGADO")
                .orElseGet(() -> {
                    Rol rol = new Rol("ENCARGADO", "Encargado de campo con acceso a registro de tareas");
                    return rolRepository.save(rol);
                });

        rolRepository.findByNombre("OPERADOR")
                .orElseGet(() -> {
                    Rol rol = new Rol("OPERADOR", "Operador con acceso de solo lectura");
                    return rolRepository.save(rol);
                });

        // Crear usuario admin si no existe
        if (!usuarioRepository.existsByUsername("admin")) {
            Usuario admin = new Usuario(
                    "admin",
                    passwordEncoder.encode("admin123"),
                    "admin@agrosystem.com",
                    "Administrador del Sistema",
                    rolAdmin);
            usuarioRepository.save(admin);
            System.out.println("✅ Usuario admin creado exitosamente");
        }

        // Sembrar Unidades de Medida si no existen
        if (unidadMedidaRepository.count() == 0) {
            String[] unidades = { "Kilogramo", "Hectárea", "Día", "Hora", "Tonelada", "Unidad" };
            for (String nombre : unidades) {
                UnidadMedida um = new UnidadMedida(nombre);
                unidadMedidaRepository.save(um);
            }
            System.out.println("✅ Unidades de medida creadas");
        }

        // Sembrar Estados de Liquidación si no existen
        String[] estados = { "Pendiente", "Aprobada", "Pagada", "Cancelada" };
        for (String nombre : estados) {
            estadoLiquidacionRepository.findByNombre(nombre)
                    .orElseGet(() -> {
                        EstadoLiquidacion estado = new EstadoLiquidacion(nombre);
                        return estadoLiquidacionRepository.save(estado);
                    });
        }
        System.out.println("✅ Estados de liquidación verificados");
    }
}
