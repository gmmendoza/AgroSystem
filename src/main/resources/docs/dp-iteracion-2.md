# Documento de Diseño y Planificación - Iteración 2

## Trabajo en equipo

- **Guadalupe Mendoza:** Desarrollo Full Stack (Backend y Frontend), diseño de la arquitectura, implementación de funcionalidades y documentación.

---

## Diseño OO

**Diagrama de clases UML:**  
![Diagrama de clases](diagramas-de-clases/dc-iteracion-2.png)

---

## Novedades de la Iteración 2

Esta iteración expandió el sistema con tres módulos principales:

1. **Liquidaciones de Empleados** — generación de recibos con cálculo de aportes patronales.
2. **Cuentas Corrientes** — seguimiento de saldo de cada empleado con movimientos de débito/crédito.
3. **Mejoras UI/UX** — sidebar con contraste, gráfico multi-tipo de precios, filtros funcionales.

---

## Casos de Uso

#### Caso de Uso 11: Generar Liquidación de Empleado
**Actores:** Administrador  
**Propósito:** Calcular y registrar la liquidación mensual de un empleado incluyendo sus tareas realizadas, aportes patronales y retenciones.  
**Precondición:** El empleado tiene actividades registradas en el período.  
**Postcondición:** La liquidación queda registrada y se genera un movimiento en la cuenta corriente del empleado.

#### Caso de Uso 12: Ver Detalle de Liquidación
**Actores:** Administrador, Encargado  
**Propósito:** Consultar el desglose completo de una liquidación: actividades, subtotales, aportes y neto a pagar.  
**Precondición:** Existe al menos una liquidación registrada.  
**Postcondición:** Se muestra el recibo completo con todos los conceptos.

#### Caso de Uso 13: Gestionar Cuenta Corriente
**Actores:** Administrador  
**Propósito:** Consultar el historial de movimientos de la cuenta corriente de un empleado, con saldo actual.  
**Precondición:** El empleado existe en el sistema.  
**Postcondición:** Se muestra el listado de movimientos (débitos, créditos) y el saldo actual.

#### Caso de Uso 14: Ver Fluctuación de Precios por Tipo
**Actores:** Administrador, Encargado  
**Propósito:** Visualizar la evolución histórica de precios de uno o varios tipos de tarea en un gráfico multi-línea interactivo.  
**Precondición:** Existen tipos de tarea con al menos un precio registrado.  
**Postcondición:** El gráfico muestra las líneas seleccionadas con tooltips de precios exactos al pasar el cursor.

#### Caso de Uso 15: Gestionar Tipos de Tarea (editar/eliminar)
**Actores:** Administrador  
**Propósito:** Editar la descripción o unidad de medida de un tipo de tarea, o eliminarlo del sistema.  
**Precondición:** El tipo de tarea existe y no tiene restricciones de integridad.  
**Postcondición:** El tipo queda actualizado o eliminado.

#### Caso de Uso 16: Gestionar Almacenes
**Actores:** Administrador  
**Propósito:** Registrar, editar y eliminar almacenes con su nombre, ubicación y capacidad.  
**Precondición:** El usuario tiene rol Administrador.  
**Postcondición:** Los almacenes quedan disponibles para asignación futura de inventario.

---

## Backlog de Iteración

### Historias de usuario implementadas en la Iteración 2:

#### **HU8: Liquidación de Empleados**
**Descripción de la historia de usuario**  
Como administrador,  
Quiero generar liquidaciones mensuales para cada empleado  
Para calcular automáticamente los montos a pagar con aportes y retenciones.

**Criterios de aceptación**  
- Selección de empleado y período (mes/año).
- Cálculo automático de subtotal de actividades.
- Inclusión de aportes patronales configurables.
- Cálculo proporcional si el empleado no trabajó el mes completo.
- Visualización del detalle completo de la liquidación.

#### **HU9: Cuentas Corrientes**
**Descripción de la historia de usuario**  
Como administrador,  
Quiero ver el saldo y movimientos de la cuenta corriente de cada empleado  
Para llevar un control financiero preciso.

**Criterios de aceptación**  
- Cada liquidación aprobada genera un movimiento en la cuenta corriente.
- Se puede ver el historial completo de movimientos.
- Se muestra el saldo actual (positivo = a favor del empleado).

#### **HU10: Dashboard con estadísticas mejoradas**
**Descripción de la historia de usuario**  
Como usuario,  
Quiero ver un resumen de las métricas del negocio en el inicio  
Para tener una visión global rápida.

**Criterios de aceptación**  
- Tarjetas de resumen: Clientes, Empleados, Actividades Registradas, Total a Pagar.
- Top 5 empleados por monto.
- Acciones rápidas: Nuevo Cliente, Nuevo Empleado, Registrar Actividad.
- Actividades recientes con monto y fecha.

#### **HU11: Gráfico de Fluctuación de Precios**
**Descripción de la historia de usuario**  
Como administrador,  
Quiero ver la evolución histórica de precios por tipo de tarea en un gráfico  
Para analizar tendencias y tomar decisiones de precios.

**Criterios de aceptación**  
- Gráfico multi-línea con una línea por tipo de tarea seleccionado.
- Filtros de checkboxes con botones "Todos" y "Ninguno".
- Tooltip con precio exacto al pasar el cursor por cada punto.
- Precio vigente del día mostrado junto a cada tipo.

---

## Tareas

### Backend
- [x] Crear entidad `Liquidacion` con sus relaciones
- [x] Crear entidad `ConceptoLiquidacion` para conceptos parametrizados
- [x] Crear entidad `MovimientoCuentaCorriente` vinculada a `CuentaCorriente`
- [x] Implementar `LiquidacionService` con cálculo de aportes y proporcional
- [x] Crear `LiquidacionController` con CRUD y detalle
- [x] Crear `CuentaCorrienteController` con listado y movimientos
- [x] Exponer endpoint `/tareas/api/precios/{id}` para chart de precios
- [x] Pasar datos `tiposConPrecios` y `preciosVigentes` a la vista de actividades
- [x] Crear `TipoTareaController` con editar y eliminar
- [x] Agregar soporte de CSRF en formularios de liquidación

### Frontend
- [x] Crear template `liquidaciones/list.html` con paginación y acciones
- [x] Crear template `liquidaciones/form.html` para nueva liquidación
- [x] Crear template `liquidaciones/detalle.html` con vista de recibo
- [x] Crear template `cuentas-corrientes/list.html`
- [x] Crear template `cuentas-corrientes/detalle.html` con movimientos
- [x] Reescribir tab "Configuración" en `actividades/index.html` con gráfico multi-tipo
- [x] Fix sidebar: contraste de texto (`text-gray-200`) sobre fondo verde oscuro
- [x] Fix breadcrumbs: reemplazar emojis 🏠 por SVG Heroicons en todas las páginas
- [x] Fix filtros de búsqueda en `clientes/list.html` y `empleados/list.html`
- [x] Agregar botones Editar y Eliminar en la lista de Tipos de Tarea (inline)
- [x] Mostrar precio vigente como badge en la lista de tipos de tarea
