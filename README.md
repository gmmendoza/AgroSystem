# AgroSystem - Sistema de Gestión Agrícola

Sistema de facturación y gestión de servicios agrícolas desarrollado con Spring Boot.

## Características

- ✅ Gestión de Clientes (ABM completo)
- ✅ Gestión de Empleados (ABM completo)
- ✅ Configuración de Tipos de Tareas y Precios Históricos
- ✅ Registro de Actividades con cálculo automático de precios
- ✅ Dashboard con estadísticas en tiempo real
- ✅ Historial de actividades realizadas
- ✅ Interfaz moderna y responsiva

## Stack Tecnológico

- **Backend**: Spring Boot 3.2.2, Spring Data JPA, Spring Security
- **Base de Datos**: PostgreSQL 15
- **Frontend**: Thymeleaf, Tailwind CSS, JavaScript
- **Contenedorización**: Docker & Docker Compose

## Requisitos Previos

### Opción 1: Ejecución con Docker (Recomendado)
- Docker Desktop instalado
- Docker Compose

### Opción 2: Ejecución Local
- Java 17 o superior
- Maven 3.6+
- PostgreSQL 15+

## Instrucciones de Ejecución

### 🐳 Con Docker (Recomendado)

1. **Clonar el repositorio**
   ```bash
   cd Poo2
   ```

2. **Construir y ejecutar con Docker Compose**
   ```bash
   docker-compose up --build
   ```

3. **Acceder a la aplicación**
   - Abrir navegador en: `http://localhost:8080`
   - La base de datos PostgreSQL estará disponible en: `localhost:5432`

4. **Detener la aplicación**
   ```bash
   docker-compose down
   ```

5. **Detener y eliminar volúmenes (reinicio completo)**
   ```bash
   docker-compose down -v
   ```

### 💻 Ejecución Local

1. **Configurar PostgreSQL**
   - Crear base de datos: `poo2_db`
   - Usuario: `postgres`
   - Contraseña: `postgres`

2. **Configurar `application.properties`**
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/poo2_db
   spring.datasource.username=postgres
   spring.datasource.password=tu_password
   ```

3. **Ejecutar la aplicación**
   ```bash
   ./mvnw spring-boot:run
   ```
   
   O en Windows:
   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

4. **Acceder a la aplicación**
   - Abrir navegador en: `http://localhost:8080`

## Estructura del Proyecto

```
Poo2/
├── docs/                       # Documentación del proyecto
│   ├── erp.md                 # Especificación de requisitos
│   ├── roadmap.md             # Planificación de iteraciones
│   └── dp-iteracion-1.md      # Diseño iteración 1
├── src/
│   ├── main/
│   │   ├── java/com/example/poo2/
│   │   │   ├── config/        # Configuración (Security)
│   │   │   ├── controller/    # Controladores web
│   │   │   ├── model/         # Entidades JPA
│   │   │   ├── repository/    # Repositorios
│   │   │   ├── service/       # Lógica de negocio
│   │   │   └── Poo2Application.java
│   │   └── resources/
│   │       ├── static/        # CSS y JS personalizados
│   │       ├── templates/     # Vistas Thymeleaf
│   │       └── application.properties
│   └── test/
├── Dockerfile                  # Configuración Docker
├── docker-compose.yml          # Orquestación de servicios
├── init.sql                    # Script de inicialización DB
├── pom.xml                     # Dependencias Maven
└── README.md
```

## Modelo de Datos

- **Cliente**: Datos del cliente y condición fiscal
- **Empleado**: Información de empleados
- **TipoTarea**: Definición de tipos de tareas (Cosecha, Limpieza, etc.)
- **PrecioTarea**: Precios históricos por tipo de tarea
- **TareaRealizada**: Registro de actividades con precio aplicado

## Funcionalidades Principales

### Dashboard
- Estadísticas en tiempo real
- Total de clientes, empleados y actividades
- Suma total a pagar por tareas realizadas
- Actividades recientes
- Accesos rápidos a funciones principales

### Gestión de Clientes
- Crear, editar y eliminar clientes
- Registro de condición fiscal (IVA)
- Campos: Nombre, CUIT, Condición Fiscal, Email, Dirección

### Gestión de Empleados
- Crear, editar y eliminar empleados
- Campos: Nombre, Legajo, Puesto

### Configuración de Tareas
- Definir tipos de tareas con unidad de medida
- Gestionar precios históricos con vigencia por fecha
- El sistema selecciona automáticamente el precio correcto según la fecha

### Registro de Actividades
- Selección de empleado y tipo de tarea
- Ingreso de fecha y cantidad
- **Cálculo automático** del precio basado en precios históricos
- Historial completo de todas las actividades

## Próximas Funcionalidades (Iteración 2)

- [ ] Facturación Individual
- [ ] Facturación Masiva por período
- [ ] Registro de Pagos
- [ ] Anulación de Facturas (Notas de Crédito)
- [ ] Reportes avanzados de liquidación

## Desarrollado por

- [Tu Nombre]
- Universidad: [Tu Universidad]
- Materia: Programación Orientada a Objetos II

## Licencia

Proyecto académico - 2026
