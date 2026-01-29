# Especificación de Requisitos de Software (SRS)
## Sistema de Gestión Agrícola - AgroSystem

---

### 1. Introducción

El propósito de este documento es definir los requisitos funcionales y no funcionales para el desarrollo de un sistema de gestión integral para un emprendimiento agrícola. El sistema permitirá administrar eficientemente los recursos humanos, controlar las actividades de campo, gestionar almacenes y llevar un seguimiento detallado de las cuentas corrientes de los clientes y las liquidaciones de los empleados.

### 2. Descripción General

El sistema está dirigido a una empresa de servicios agrícolas que necesita centralizar su gestión operativa. Se enfoca en el control detallado de las tareas realizadas por los empleados (cosecha, siembra, mantenimiento), la gestión de inventario en almacenes y el análisis de productividad. A diferencia de un sistema contable tradicional, AgroSystem prioriza la gestión interna, el cálculo de costos operativos y el seguimiento de saldos, sin intervención directa en procesos de facturación fiscal externa (AFIP/ARCA).

### 3. Requisitos Funcionales

#### 3.1. Autenticación y Autorización
- **RF-01**: El sistema debe permitir el inicio de sesión con usuario y contraseña.
- **RF-02**: Las contraseñas deben estar encriptadas en la base de datos.
- **RF-03**: El sistema debe soportar múltiples roles (ADMIN, ENCARGADO, OPERADOR).

#### 3.2. Gestión de Clientes y Cuentas Corrientes
- **RF-04**: El sistema debe permitir el alta, baja y modificación de Clientes.
- **RF-05**: El sistema debe gestionar las **Cuentas Corrientes** de los clientes.
    - Registro de saldo actual.
    - Historial de movimientos (debe y haber).
    - Definición de límites de crédito internos.
- **RF-06**: Visualización clara del estado de deuda o crédito de cada cliente.

#### 3.3. Gestión de Recursos Humanos y Tareas
- **RF-07**: El sistema debe permitir el alta, baja y modificación de Empleados.
- **RF-08**: Definición de Tipos de Tarea (Cosecha, Poda, Siembra) con su Unidad de Medida (Kilo, Hectárea, Día).
- **RF-09**: Gestión de Listas de Precios Históricos por tarea, con vigencia por fecha.
- **RF-10**: Registro de **Actividades de Campo** (Tareas realizadas).
    - Asignación de uno o múltiples empleados a una tarea.
    - Cálculo automático del valor de la tarea según el precio vigente.
    - Carga retroactiva permitida para ajustes administrativos.

#### 3.4. Liquidación de Haberes (Iteración 2)
- **RF-11**: Generación de **Liquidaciones** por período.
- **RF-12**: Cálculo automático del total a pagar por empleado basado en:
    - Sumatoria de tareas realizadas en el período.
    - Precios vigentes al momento de la ejecución.
- **RF-13**: Gestión de estados de liquidación (Pendiente, Aprobada, Pagada).
- **RF-14**: Visualización del detalle compositivo de cada liquidación.

#### 3.5. Gestión de Almacenes e Inventario (Iteración 2)
- **RF-15**: Administración de **Almacenes** y Depósitos.
- **RF-16**: Registro de ubicación física y capacidad de almacenamiento.
- **RF-17**: Control de estados de almacén (Activo, En Mantenimiento, Inactivo).

#### 3.6. Reportes y Estadísticas
- **RF-18**: Reporte de **Productividad por Empleado**: Eficiencia y totales realizados.
- **RF-19**: Reporte de Actividades por Período: Visión global de operaciones.
- **RF-20**: Reporte de Estado de Cuentas Corrientes.
- **RF-21**: Exportación de reportes a formato PDF para archivo digital.

### 4. Requisitos No Funcionales

- **RNF-01**: El sistema debe estar desarrollado en Java 17 utilizando Spring Boot 3.x.
- **RNF-02**: La base de datos debe ser PostgreSQL 15.
- **RNF-03**: Se utilizará Hibernate como ORM.
- **RNF-04**: El frontend debe ser desarrollado con Thymeleaf, Tailwind CSS y Alpine.js.
- **RNF-05**: La interfaz debe ser moderna, intuitiva y responsiva.
- **RNF-06**: Seguridad implementada con Spring Security y BCrypt.
- **RNF-07**: Containerización completa con Docker y Docker Compose.

### 5. Reglas de Negocio

- **RN-01 (Precios Vigentes)**: Al registrar una tarea, el sistema debe congelar el valor monetario basado en el precio vigente de ese día. Cambios futuros en la lista de precios no deben afectar tareas ya registradas.
- **RN-02 (Gestión Interna)**: El sistema opera como una herramienta de gestión interna y control de costos. Los cálculos de impuestos externos o facturación fiscal se manejan en sistemas contables separados.
- **RN-03 (Unicidad de Cuenta)**: Un cliente posee una única cuenta corriente principal para la gestión de sus saldos.

### 6. Tecnologías Utilizadas

| Componente | Tecnología |
|------------|------------|
| Backend | Spring Boot 3.2.x, Spring Data JPA |
| Frontend | Thymeleaf, Tailwind CSS, Alpine.js, Chart.js |
| Base de datos | PostgreSQL 15 |
| ORM | Hibernate |
| Herramientas | Docker, Docker Compose, Maven |
| Seguridad | Spring Security, BCrypt |

---

**Desarrollado por:** Guadalupe Mendoza  
**Universidad:** Universidad Nacional de Misiones - Facultad de Ciencias, Exactas, Químicas y Naturales
**Materia:** Programación Orientada a Objetos II  
**Año:** 2026
