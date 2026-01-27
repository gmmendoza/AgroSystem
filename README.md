# AgroSystem - Sistema de Gestión Agrícola

<div align="center">

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.2-6DB33F?style=for-the-badge&logo=spring-boot)
![Java](https://img.shields.io/badge/Java-17-007396?style=for-the-badge&logo=java)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-4169E1?style=for-the-badge&logo=postgresql)
![Tailwind CSS](https://img.shields.io/badge/Tailwind%20CSS-3.4-38B2AC?style=for-the-badge&logo=tailwind-css)
![Docker](https://img.shields.io/badge/Docker-Ready-2496ED?style=for-the-badge&logo=docker)

Sistema integral de facturación y gestión de servicios agrícolas desarrollado con Spring Boot.

[Ver Demo](#instrucciones-de-ejecución) • [Documentación](docs/) • [Reportar Bug](https://github.com/gmmendoza/AgroSystem/issues)

</div>

---

## Características

### Autenticación y Seguridad
- Sistema de login con Spring Security
- Roles de usuario (ADMIN, ENCARGADO, OPERARIO)
- Gestión de perfil y cambio de contraseña

### Dashboard Inteligente
- Estadísticas en tiempo real
- Gráficos con Chart.js (actividades por mes)
- Top 5 empleados más productivos
- Resumen de actividades del día

### Gestión Completa
- **Clientes**: ABM con validación CUIT (algoritmo AFIP)
- **Empleados**: ABM con legajo y puesto
- **Tareas**: Tipos configurables con precios históricos
- **Actividades**: Registro con cálculo automático de precios

### Interfaz Moderna
- Diseño responsivo con Tailwind CSS
- Búsqueda, paginación y ordenamiento en listados
- Modales de confirmación animados
- Feedback visual en formularios
- Dark mode ready

---

## Stack Tecnológico

| Categoría | Tecnología |
|-----------|------------|
| **Backend** | Spring Boot 3.2.2, Spring Data JPA, Spring Security |
| **Base de Datos** | PostgreSQL 15 |
| **Frontend** | Thymeleaf, Tailwind CSS, JavaScript, Chart.js |
| **Contenedorización** | Docker & Docker Compose |

---

## Instrucciones de Ejecución

### Con Docker (Recomendado)

```bash
# Clonar el repositorio
git clone https://github.com/gmmendoza/AgroSystem.git
cd AgroSystem

# Construir y ejecutar
docker-compose up --build

# Acceder en http://localhost:8080
```

### Ejecución Local

```bash
# Requisitos: Java 17+, Maven 3.6+, PostgreSQL 15+

# Configurar PostgreSQL
# - Base de datos: poo2_db
# - Usuario: postgres
# - Contraseña: postgres

# Ejecutar
./mvnw spring-boot:run
```

**Credenciales por defecto:**
- Usuario: `admin`
- Contraseña: `admin`

---

## Estructura del Proyecto

```
AgroSystem/
├── 📂 docs/                    # Documentación
│   ├── erp.md                  # Requisitos del sistema
│   ├── roadmap.md              # Planificación de iteraciones
│   └── dp-iteracion-1.md       # Diseño detallado
├── 📂 src/main/java/.../
│   ├── config/                 # Security, Web Config
│   ├── controller/             # Controladores MVC
│   ├── model/                  # Entidades JPA
│   ├── repository/             # Repositorios de datos
│   └── service/                # Lógica de negocio
├── 📂 src/main/resources/
│   ├── templates/              # Vistas Thymeleaf
│   └── static/                 # CSS y JS
├── Dockerfile
├── docker-compose.yml
└── README.md
```

---

## Funcionalidades por Iteración

### Iteración 1 (Completada)

| Funcionalidad | Estado |
|---------------|--------|
| Sticky footer y modal de confirmación | ✅ |
| Búsqueda, paginación y ordenamiento | ✅ |
| Validación CUIT con algoritmo AFIP | ✅ |
| Dashboard con Chart.js y métricas | ✅ |
| Edición/eliminación de actividades | ✅ |
| Perfil de usuario y cambio de contraseña | ✅ |

### Iteración 2 (Próximamente)

- [ ] Facturación Individual
- [ ] Facturación Masiva por período
- [ ] Registro de Pagos
- [ ] Notas de Crédito
- [ ] Reportes de liquidación

---

## Desarrollado por

<div align="center">

### **Guadalupe Mendoza**

**Cátedra:** Programación Orientada a Objetos II

**Institución:** Facultad de Ciencias Exactas, Químicas y Naturales

**Universidad Nacional de Misiones** (UNaM)

**Año:** 2026

</div>

---

## Licencia

Este es un proyecto académico desarrollado como trabajo práctico para la cátedra de Programación Orientada a Objetos II.

---

<div align="center">

</div>
