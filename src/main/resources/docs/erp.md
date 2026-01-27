# Especificación de Requisitos de Software (SRS)
## Sistema de Facturación de Servicios para Empresa Agrícola - AgroSystem

---

### 1. Introducción

El propósito de este documento es definir los requisitos funcionales y no funcionales para el desarrollo de un sistema de facturación de servicios y gestión de tareas para un emprendimiento agrícola. El sistema permitirá gestionar cuentas de clientes, registrar tareas realizadas por empleados, y generar facturación masiva e individual, así como reportes de pagos a empleados.

### 2. Descripción General

El sistema está dirigido a una empresa de servicios agrícolas que necesita llevar un control detallado de las tareas realizadas por sus empleados (cosecha, limpieza, etc.) y facturar estos servicios a sus clientes. Además, el sistema debe calcular los montos a pagar a los empleados basándose en las tareas realizadas y los precios vigentes.

### 3. Requisitos Funcionales

#### 3.1. Autenticación y Autorización
- **RF-01**: El sistema debe permitir el inicio de sesión con usuario y contraseña.
- **RF-02**: Las contraseñas deben estar encriptadas en la base de datos.
- **RF-03**: El sistema debe soportar múltiples roles (ADMIN, ENCARGADO, OPERADOR).

#### 3.2. Gestión de Clientes y Cuentas
- **RF-04**: El sistema debe permitir el alta, baja y modificación de Clientes.
- **RF-05**: Se debe registrar la condición fiscal de cada cliente (Responsable Inscripto, Monotributista, Exento, etc.) para el cálculo correcto del IVA.
- **RF-06**: El sistema debe gestionar las cuentas corrientes de los clientes.

#### 3.3. Gestión de Empleados y Tareas
- **RF-07**: El sistema debe permitir el alta, baja y modificación de Empleados.
- **RF-08**: El sistema debe permitir definir Tipos de Tareas (ej. Cosecha, Limpieza, Mantenimiento).
- **RF-09**: Cada Tipo de Tarea debe tener una unidad de medida asociada (Kilo, Hectárea, Día, Hora).
- **RF-10**: El sistema debe permitir registrar Precios Unitarios para cada Tipo de Tarea, con vigencia por fecha (Histórico de precios).
- **RF-11**: El sistema debe permitir la carga de Tareas realizadas por un empleado en una fecha específica.
    - La carga puede ser retroactiva.
    - El sistema debe asignar automáticamente el precio vigente para la fecha de la tarea.

#### 3.4. Facturación (Iteración 2)
- **RF-12**: El sistema debe permitir generar Facturas Individuales para un cliente.
- **RF-13**: El sistema debe permitir la Facturación Masiva por período.
- **RF-14**: El sistema debe calcular automáticamente los impuestos (IVA) según la condición fiscal del cliente.
- **RF-15**: El sistema debe permitir la Anulación de Facturas (Notas de Crédito).
- **RF-16**: El sistema debe permitir registrar Pagos recibidos de los clientes.

#### 3.5. Reportes
- **RF-17**: Reporte de Tareas por Empleado: Detalle de tareas realizadas en un período.
- **RF-18**: Reporte de Liquidación a Empleados: Monto total a pagar a cada empleado.

### 4. Requisitos No Funcionales

- **RNF-01**: El sistema debe estar desarrollado en Java 17 utilizando Spring Boot 3.x.
- **RNF-02**: La base de datos debe ser PostgreSQL 15.
- **RNF-03**: Se utilizará Hibernate como ORM.
- **RNF-04**: El frontend debe ser desarrollado con Thymeleaf y Tailwind CSS.
- **RNF-05**: La interfaz debe ser moderna, intuitiva y responsiva.
- **RNF-06**: Seguridad implementada con Spring Security y BCrypt.
- **RNF-07**: Containerización con Docker y Docker Compose.

### 5. Reglas de Negocio

- **RN-01**: Los precios de las tareas varían en el tiempo. Al registrar una tarea o calcular su costo, siempre se debe tomar el precio vigente en la fecha de ejecución de la tarea.
- **RN-02**: La facturación debe respetar las normativas fiscales argentinas vigentes.
- **RN-03**: Un cliente puede tener una única cuenta corriente asociada.

### 6. Tecnologías Utilizadas

| Componente | Tecnología |
|------------|------------|
| Backend | Spring Boot 3.2.2, Spring Data JPA, Spring Security |
| Frontend | Thymeleaf, Tailwind CSS, JavaScript |
| Base de datos | PostgreSQL 15 |
| ORM | Hibernate |
| Contenedorización | Docker, Docker Compose |
| Seguridad | Spring Security, BCrypt |

---

**Desarrollado por:** Guadalupe Mendoza  
**Universidad:** [Tu Universidad]  
**Materia:** Programación Orientada a Objetos II  
**Año:** 2026
