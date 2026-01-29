# Documento de Diseño y Planificación - Iteración 1

## Trabajo en equipo

- **Guadalupe Mendoza:** Desarrollo Full Stack (Backend y Frontend), diseño de la arquitectura, implementación de funcionalidades y documentación.

---

## Diseño OO

**Diagrama de clases UML:**  
![Diagrama de clases](diagramas-de-clases/dc-iteracion-1.png)

---

## Wireframe y Caso de Uso

### Wireframes

- **Página de Login**  
  ![Login](wireframes/login.png)

- **Inicio Principal**  
  ![Dashboard](wireframes/dashboard.png)

- **Gestión de Clientes**  
  ![Clientes](wireframes/clientes.png)

- **Gestión de Empleados**  
  ![Empleados](wireframes/empleados.png)

- **Registro de Actividades**  
  ![Registro Actividad](wireframes/registro-actividad.png)

- **Configuración de Tareas**  
  ![Configuración](wireframes/configuracion.png)

---

### Casos de Uso

#### Caso de Uso 1: Iniciar Sesión
**Actores:** Usuario (Administrador, Encargado, Operador)  
**Propósito:** Permite a los usuarios autenticarse en el sistema para acceder a las funcionalidades según su rol.  
**Precondición:** El usuario tiene una cuenta activa en el sistema.  
**Postcondición:** El usuario accede al inicio y puede navegar según sus permisos.

#### Caso de Uso 2: Gestionar Clientes (ABM)
**Actores:** Administrador  
**Propósito:** Permite crear, editar y eliminar clientes del sistema.  
**Precondición:** El usuario ha iniciado sesión con rol de Administrador.  
**Postcondición:** Los datos del cliente quedan registrados con su condición fiscal para facturación.

#### Caso de Uso 3: Gestionar Empleados (ABM)
**Actores:** Administrador  
**Propósito:** Permite registrar empleados que realizarán tareas agrícolas.  
**Precondición:** El usuario ha iniciado sesión con rol de Administrador.  
**Postcondición:** El empleado queda disponible para asignarle tareas realizadas.

#### Caso de Uso 4: Configurar Tipos de Tarea
**Actores:** Administrador  
**Propósito:** Definir los tipos de tareas agrícolas con su unidad de medida.  
**Precondición:** El usuario tiene acceso a la sección de configuración.  
**Postcondición:** Los tipos de tarea quedan disponibles para el registro de actividades.

#### Caso de Uso 5: Gestionar Precios Históricos
**Actores:** Administrador  
**Propósito:** Registrar precios unitarios por tipo de tarea con fecha de vigencia.  
**Precondición:** Existe al menos un tipo de tarea definido.  
**Postcondición:** El sistema usará el precio vigente según la fecha de cada actividad.

#### Caso de Uso 6: Registrar Actividad de Campo
**Actores:** Encargado, Administrador  
**Propósito:** Registrar tareas realizadas por empleados con cálculo automático del costo.  
**Precondición:** Existen empleados y tipos de tarea con precios vigentes.  
**Postcondición:** La actividad queda registrada con el precio aplicado según la fecha.

#### Caso de Uso 7: Ver Historial de Actividades
**Actores:** Administrador, Encargado, Operador  
**Propósito:** Consultar todas las actividades registradas en el sistema.  
**Precondición:** El usuario ha iniciado sesión.  
**Postcondición:** Se muestra el listado de actividades con empleado, tarea, cantidad y monto.

#### Caso de Uso 8: Ver Inicio con Estadísticas
**Actores:** Administrador, Encargado  
**Propósito:** Visualizar estadísticas en tiempo real del negocio.  
**Precondición:** El usuario ha iniciado sesión.  
**Postcondición:** Se muestran totales de clientes, empleados, actividades y montos a pagar.

#### Caso de Uso 9: Cerrar Sesión
**Actores:** Todos los usuarios autenticados  
**Propósito:** Finalizar la sesión de forma segura.  
**Precondición:** El usuario está autenticado.  
**Postcondición:** La sesión se invalida y el usuario es redirigido al login.

#### Caso de Uso 10: Gestionar Almacenes
**Actores:** Administrador  
**Propósito:** Registrar almacenes/depósitos con ubicación y capacidad.  
**Precondición:** El usuario tiene rol de Administrador.  
**Postcondición:** Los almacenes quedan disponibles para asignación de productos (Iteración 2).

---

## Backlog de Iteración

### Historias de usuario implementadas en la iteración 1:

#### **HU1: Autenticación de Usuarios**
**Descripción de la historia de usuario**  
Como usuario del sistema,  
Quiero iniciar sesión con mi usuario y contraseña  
Para acceder a las funcionalidades según mi rol.

**Criterios de aceptación**  
- El sistema valida credenciales contra la base de datos.
- Las contraseñas están encriptadas con BCrypt.
- Al fallar el login, se muestra mensaje de error.
- Existen 3 roles: ADMIN, ENCARGADO, OPERADOR.

#### **HU2: Gestión de Clientes**
**Descripción de la historia de usuario**  
Como administrador,  
Quiero registrar clientes con su condición fiscal  
Para poder facturarles los servicios posteriormente.

**Criterios de aceptación**  
- Formulario con campos: Nombre, CUIT (único), Condición Fiscal, Dirección, Email.
- Validación de CUIT único en el sistema.
- Listado con opciones de editar y eliminar.

#### **HU3: Gestión de Empleados**
**Descripción de la historia de usuario**  
Como administrador,  
Quiero registrar empleados con su legajo  
Para asignarles las tareas que realizan.

**Criterios de aceptación**  
- Formulario con campos: Nombre, Legajo (único), Puesto.
- Listado con opciones de editar y eliminar.

#### **HU4: Configuración de Tipos de Tarea**
**Descripción de la historia de usuario**  
Como administrador,  
Quiero definir tipos de tareas agrícolas  
Para poder registrar las actividades de los empleados.

**Criterios de aceptación**  
- Formulario con Descripción y Unidad de Medida (Kilo, Hectárea, Día, Hora).
- Listado de tipos definidos.

#### **HU5: Gestión de Precios Históricos**
**Descripción de la historia de usuario**  
Como administrador,  
Quiero registrar precios unitarios con fecha de vigencia  
Para que el sistema calcule correctamente según la fecha de cada tarea.

**Criterios de aceptación**  
- Cada tipo de tarea puede tener múltiples precios.
- El sistema usa el precio vigente para la fecha de la actividad.

#### **HU6: Registro de Actividades de Campo**
**Descripción de la historia de usuario**  
Como encargado,  
Quiero registrar las tareas realizadas por los empleados  
Para llevar control de la producción y calcular pagos.

**Criterios de aceptación**  
- Selección de empleado y tipo de tarea.
- Ingreso de fecha (permite retroactiva) y cantidad.
- El sistema calcula automáticamente el subtotal con el precio vigente.

#### **HU7: Inicio con Estadísticas**
**Descripción de la historia de usuario**  
Como usuario,  
Quiero ver un resumen de la operación en el inicio  
Para tener una visión general del negocio.

**Criterios de aceptación**  
- Muestra total de clientes, empleados y actividades.
- Muestra suma total de montos a pagar.
- Muestra las últimas actividades registradas.

---

## Tareas

### Backend
- [x] Configurar Spring Boot con JPA, Security, Thymeleaf
- [x] Crear entidades: Cliente, Empleado, TipoTarea, PrecioTarea, TareaRealizada
- [x] Crear entidades de autenticación: Usuario, Rol
- [x] Crear entidades complementarias: Almacen, CuentaCorriente, Liquidacion
- [x] Implementar Spring Security con login form y BCrypt
- [x] Crear repositories para todas las entidades
- [x] Implementar service para lógica de precios vigentes
- [x] Crear controllers para ABMs y autenticación
- [x] Crear DataInitializer para usuario admin inicial

### Frontend
- [x] Crear layout base con Thymeleaf y Tailwind CSS
- [x] Crear vista de login premium con animaciones
- [x] Crear navbar con usuario logueado y botón logout
- [x] Crear vistas de listado y formulario de Clientes
- [x] Crear vistas de listado y formulario de Empleados
- [x] Crear vista de configuración de Tareas y Precios
- [x] Crear vista de Registro de Actividades
- [x] Crear Inicio con estadísticas y accesos rápidos
