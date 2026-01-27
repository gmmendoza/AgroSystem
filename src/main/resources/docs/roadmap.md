# Roadmap del Proyecto - AgroSystem

## Iteracion 1 (Actual - Completada)

### Funcionalidades Implementadas
- Autenticacion de usuarios (login/logout/registro)
- Gestion de Clientes (ABM)
- Gestion de Empleados (ABM)
- Configuracion de Tipos de Tarea
- Gestion de Precios Historicos
- Registro de Actividades de Campo
- Dashboard con estadisticas
- Pagina de documentacion integrada

### Modelo de Datos
- 11 entidades implementadas
- Sistema de roles (ADMIN, ENCARGADO, OPERADOR)

---

## Iteracion 2 (Planificada)

### Funcionalidades a Implementar

#### Facturacion
- Generar facturas individuales
- Facturacion masiva por periodo
- Calculo automatico de IVA segun condicion fiscal
- Notas de credito para anulaciones

#### Pagos
- Registro de pagos recibidos
- Metodos de pago (Efectivo, Transferencia, Cheque)
- Estado de facturas (Pendiente, Pagada, Anulada)

#### Liquidacion de Empleados
- Generacion de liquidaciones por periodo
- Calculo de totales a pagar
- Estados de liquidacion (Pendiente, Aprobada, Pagada)

### Nuevas Entidades
- Factura
- DetalleFactura
- Pago
- NotaCredito

---

## Iteracion 3 (Futuro)

### Funcionalidades Planificadas

#### Reportes Avanzados
- Reporte de productividad por empleado
- Reporte de facturacion mensual
- Reporte de cuentas por cobrar
- Exportacion a PDF y Excel

#### Gestion de Almacenes
- Control de stock por almacen
- Movimientos entre almacenes
- Inventario valorizado

#### Integraciones
- Integracion con AFIP para facturacion electronica
- Notificaciones por email
- API REST para integraciones externas

---

## Mejoras Tecnicas Pendientes

### Testing
- [ ] Tests unitarios para servicios
- [ ] Tests de integracion para controladores
- [ ] Tests end-to-end con Selenium

### Seguridad
- [ ] Habilitar CSRF en produccion
- [ ] Implementar rate limiting
- [ ] Auditoria de acciones de usuario

### Infraestructura
- [ ] Configuracion de CI/CD
- [ ] Despliegue en cloud (AWS/Azure/GCP)
- [ ] Backup automatico de base de datos

---

**Ultima actualizacion:** Enero 2026  
**Desarrollado por:** Guadalupe Mendoza
