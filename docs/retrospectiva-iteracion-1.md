# Retrospectiva Iteración 1

## Lo que hicimos bien

- **Arquitectura sólida desde el inicio**: Se definió una arquitectura MVC clara con Spring Boot, separando correctamente las capas de modelo, repositorio, servicio y controlador.
- **Seguridad implementada**: Se integró Spring Security con autenticación real desde el principio, evitando tener que refactorizar después.
- **Modelo de dominio completo**: Se expandió el modelo de 5 a 11 clases, anticipando las necesidades de la Iteración 2 (facturación, liquidación).
- **Documentación siguiendo estándares**: Se adoptó un formato de documentación profesional con wireframes, diagramas UML y casos de uso detallados.
- **UI moderna y responsiva**: Se utilizó Tailwind CSS con diseño glassmorphism y animaciones para una experiencia de usuario premium.

## Lo que nos costó

- **Configuración inicial de Docker**: La configuración del contenedor con PostgreSQL y la aplicación Spring Boot requirió varios ajustes en las variables de entorno.
- **Integración de Spring Security**: Configurar correctamente el login form con Thymeleaf y los redirects tomó más tiempo del esperado.
- **Precios históricos**: La lógica para obtener el precio vigente según la fecha de la tarea fue compleja de implementar correctamente.

## Para la próxima

1. **Implementar facturación completa**: En la Iteración 2 se implementarán las entidades Factura, DetalleFactura, Pago y NotaCredito con sus respectivas vistas.
2. **Agregar reportes**: Crear reportes de liquidación a empleados y resúmenes por período.
3. **Tests unitarios**: Agregar pruebas para la lógica de negocio crítica (precios vigentes, cálculos de totales).
4. **Mejorar validaciones**: Agregar validaciones más estrictas en formularios con mensajes de error específicos.
5. **Roles y permisos**: Restringir funcionalidades según el rol del usuario (ADMIN vs ENCARGADO vs OPERADOR).

---

**Fecha:** Enero 2026  
**Desarrollado por:** Guadalupe Mendoza  
**Materia:** Programación Orientada a Objetos II
