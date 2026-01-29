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
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CondicionFiscalRepository condicionFiscalRepository;
    @Autowired
    private UnidadMedidaRepository unidadMedidaRepository;
    @Autowired
    private EstadoLiquidacionRepository estadoLiquidacionRepository;
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

    private final Random random = new Random();

    @Override
    public void run(String... args) throws Exception {
        seedCondicionesFiscales();
        seedUnidadesMedida();
        seedEstadosLiquidacion();
        seedRolesAndUsers();
        seedClientes();
        seedEmpleados();
        seedTiposTarea();
        seedTareas();
    }

    private void seedCondicionesFiscales() {
        if (condicionFiscalRepository.count() == 0) {
            condicionFiscalRepository.saveAll(Arrays.asList(
                    new CondicionFiscal("RESPONSABLE_INSCRIPTO", "Responsable Inscripto"),
                    new CondicionFiscal("MONOTRIBUTISTA", "Monotributista"),
                    new CondicionFiscal("EXENTO", "Exento"),
                    new CondicionFiscal("CONSUMIDOR_FINAL", "Consumidor Final")));
        }
    }

    private void seedUnidadesMedida() {
        if (unidadMedidaRepository.count() == 0) {
            unidadMedidaRepository.saveAll(Arrays.asList(
                    new UnidadMedida("KILO"),
                    new UnidadMedida("HECTAREA"),
                    new UnidadMedida("DIA"),
                    new UnidadMedida("HORA")));
        }
    }

    private void seedEstadosLiquidacion() {
        if (estadoLiquidacionRepository.count() == 0) {
            estadoLiquidacionRepository.saveAll(Arrays.asList(
                    new EstadoLiquidacion("PENDIENTE"),
                    new EstadoLiquidacion("APROBADA"),
                    new EstadoLiquidacion("PAGADA"),
                    new EstadoLiquidacion("ANULADA")));
        }
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
            List<CondicionFiscal> condiciones = condicionFiscalRepository.findAll();

            for (int i = 0; i < 20; i++) {
                String nombre = nombres[random.nextInt(nombres.length)] + " "
                        + apellidos[random.nextInt(apellidos.length)] + " S.A.";
                String cuit = "30-" + (10000000 + random.nextInt(90000000)) + "-" + random.nextInt(10);
                CondicionFiscal condicion = condiciones.get(random.nextInt(condiciones.size()));
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
            UnidadMedida kilo = unidadMedidaRepository.findByNombre("KILO").orElse(null);
            UnidadMedida hectarea = unidadMedidaRepository.findByNombre("HECTAREA").orElse(null);
            UnidadMedida dia = unidadMedidaRepository.findByNombre("DIA").orElse(null);
            UnidadMedida hora = unidadMedidaRepository.findByNombre("HORA").orElse(null);

            tipoTareaRepository.save(new TipoTarea("Cosecha Manual", kilo));
            tipoTareaRepository.save(new TipoTarea("Siembra", hectarea));
            tipoTareaRepository.save(new TipoTarea("Fumigación", hectarea));
            tipoTareaRepository.save(new TipoTarea("Poda", dia));
            tipoTareaRepository.save(new TipoTarea("Mantenimiento Maquinaria", hora));
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
