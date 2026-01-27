package com.example.poo2.config;

import com.example.poo2.model.*;
import com.example.poo2.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EmpleadoRepository empleadoRepository;
    @Autowired
    private TipoTareaRepository tipoTareaRepository;
    @Autowired
    private TareaRealizadaRepository tareaRealizadaRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final Random random = new Random();

    @Override
    public void run(String... args) throws Exception {
        seedRolesAndUsers();
        seedClientes();
        seedEmpleados();
        seedTiposTarea();
        seedTareas();
    }

    private void seedRolesAndUsers() {
        if (rolRepository.count() == 0) {
            Rol adminRole = new Rol("ROLE_ADMIN", "Administrador del sistema");
            Rol userRole = new Rol("ROLE_USER", "Usuario estándar");
            rolRepository.saveAll(Arrays.asList(adminRole, userRole));

            Usuario admin = new Usuario("admin", passwordEncoder.encode("admin"), "admin@agro.com", "Administrador",
                    adminRole);
            Usuario user = new Usuario("user", passwordEncoder.encode("user"), "user@agro.com", "Usuario Test",
                    userRole);
            usuarioRepository.saveAll(Arrays.asList(admin, user));
            System.out.println("Usuarios y Roles creados.");
        }
    }

    private void seedClientes() {
        if (clienteRepository.count() == 0) {
            String[] nombres = { "Agro", "Campo", "Finca", "Estancia", "Granja", "Cooperativa" };
            String[] apellidos = { "Sur", "Norte", "Verde", "Azul", "Dorada", "Fértil", "Nueva", "Vieja" };

            for (int i = 0; i < 20; i++) {
                String nombre = nombres[random.nextInt(nombres.length)] + " "
                        + apellidos[random.nextInt(apellidos.length)] + " S.A.";
                String cuit = "30-" + (10000000 + random.nextInt(90000000)) + "-" + random.nextInt(10);
                CondicionFiscal condicion = CondicionFiscal.values()[random.nextInt(CondicionFiscal.values().length)];
                String email = "contacto" + i + "@" + nombre.toLowerCase().replaceAll(" ", "").replaceAll("\\.", "")
                        + ".com";

                Cliente cliente = new Cliente(nombre, cuit, condicion,
                        "Ruta " + random.nextInt(100) + " km " + random.nextInt(500), email);
                clienteRepository.save(cliente);
            }
            System.out.println("Clientes creados.");
        }
    }

    private void seedEmpleados() {
        if (empleadoRepository.count() == 0) {
            String[] nombres = { "Juan", "Pedro", "Maria", "Ana", "Luis", "Carlos", "Jose", "Laura", "Sofia",
                    "Miguel" };
            String[] apellidos = { "Perez", "Gomez", "Lopez", "Diaz", "Martinez", "Rodriguez", "Garcia", "Fernandez",
                    "Gonzalez", "Sanchez" };
            String[] puestos = { "Peón General", "Tractorista", "Cosechero", "Capataz", "Administrativo" };

            for (int i = 0; i < 20; i++) {
                String nombre = nombres[random.nextInt(nombres.length)] + " "
                        + apellidos[random.nextInt(apellidos.length)];
                String legajo = "L" + (1000 + i);
                String puesto = puestos[random.nextInt(puestos.length)];

                Empleado empleado = new Empleado(nombre, legajo, puesto);
                empleadoRepository.save(empleado);
            }
            System.out.println("Empleados creados.");
        }
    }

    private void seedTiposTarea() {
        if (tipoTareaRepository.count() == 0) {
            tipoTareaRepository.save(new TipoTarea("Cosecha Manual", UnidadMedida.KILO));
            tipoTareaRepository.save(new TipoTarea("Siembra", UnidadMedida.HECTAREA));
            tipoTareaRepository.save(new TipoTarea("Fumigación", UnidadMedida.HECTAREA));
            tipoTareaRepository.save(new TipoTarea("Poda", UnidadMedida.DIA));
            tipoTareaRepository.save(new TipoTarea("Mantenimiento Maquinaria", UnidadMedida.HORA));
            System.out.println("Tipos de Tarea creados.");
        }
    }

    private void seedTareas() {
        if (tareaRealizadaRepository.count() == 0) {
            List<Empleado> empleados = empleadoRepository.findAll();
            List<TipoTarea> tipos = tipoTareaRepository.findAll();

            if (empleados.isEmpty() || tipos.isEmpty())
                return;

            LocalDate today = LocalDate.now();

            // Generate tasks for the last 6 months
            for (int i = 0; i < 200; i++) {
                Empleado empleado = empleados.get(random.nextInt(empleados.size()));
                TipoTarea tipo = tipos.get(random.nextInt(tipos.size()));

                // Random date within last 180 days
                LocalDate fecha = today.minusDays(random.nextInt(180));

                double cantidad = 1 + random.nextInt(100);
                double precio = 100 + random.nextInt(9000); // Random price

                TareaRealizada tarea = new TareaRealizada(fecha, cantidad, precio, empleado, tipo);
                tareaRealizadaRepository.save(tarea);
            }
            System.out.println("Tareas creadas.");
        }
    }
}
