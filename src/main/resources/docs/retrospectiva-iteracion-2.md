# Retrospectiva Iteración 2

## Lo que hice bien

- **Módulo de Liquidaciones completo**: Se implementó el flujo completo de liquidaciones de empleados con cálculo automático de aportes patronales y cálculo proporcional por días trabajados.
- **Gráfico de Fluctuación de Precios**: Se implementó un gráfico multi-tipo interactivo con filtros por tipo de tarea, tooltips con precios exactos y selector "Todos / Ninguno".
- **Módulo de Cuentas Corrientes**: Se integraron las cuentas corrientes con movimientos de débito y crédito vinculados a las liquidaciones.
- **CSS consistente en todo el sistema**: Se unificó el estilo en todas las páginas (Almacenes, Cuentas Corrientes, breadcrumbs, sidebar).
- **Sidebar con contraste correcto**: Se mejoró la legibilidad del sidebar ajustando el color de texto para mayor contraste sobre el fondo verde oscuro.
- **Persistencia del estado de la UI**: La pestaña de Configuración en Actividades ahora muestra correctamente el gráfico al abrirse por primera vez usando MutationObserver.
- **Manejo correcto de fechas y precios vigentes**: El sistema calcula el precio aplicable según la fecha de cada actividad y lo muestra en la vista de configuración.

## Lo que me costó

- **Serialización de datos para gráficos**: El uso de `th:inline="javascript"` generó errores silenciosos con caracteres especiales (ñ, acentos) en los nombres de tipos de tarea. Se solucionó migrándo a `<script type="application/json">` para pasar datos del servidor al cliente.
- **Integración Alpine.js y Chart.js**: El tab de Configuración usa `x-cloak` de Alpine.js que oculta el canvas con `display: none`, impidiendo que Chart.js lo renderice. Se solucionó con un `MutationObserver` que detecta cuando el tab se vuelve visible.
- **Formulario de liquidaciones con CSRF**: El formulario de `/liquidaciones/nueva` usaba `action=` en lugar de `th:action=@{}`, impidiendo que Spring Security inyectara el token CSRF y rechazando el POST.
- **Filtros de búsqueda en clientes/empleados**: El selector de tabla en JavaScript usaba `querySelector` genérico que podía capturar el elemento equivocado. Se resolvió usando `id` explícitos en el contenedor.

## Para la próxima

1. **Tests de integración**: Implementar tests de integración con Spring Boot Test para los controllers principales.
2. **Generación de PDF para liquidaciones**: Agregar la opción de exportar las liquidaciones como PDF (por ejemplo, usando iText o JasperReports).
3. **Notificaciones y alertas**: Sistema de alertas para precios sin vigencia o empleados sin liquidación en el período.
4. **Roles y permisos**: Refinar el control de acceso por rol en todas las funcionalidades (aprobación de liquidaciones, etc.).
5. **Paginación en más secciones**: Extender la paginación client-side a las páginas de Almacenes y Cuentas Corrientes.

---

**Fecha:** Febrero 2026  
**Desarrollado por:** Guadalupe Mendoza  
**Materia:** Programación Orientada a Objetos II
