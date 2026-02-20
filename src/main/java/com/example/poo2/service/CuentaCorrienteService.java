package com.example.poo2.service;

import com.example.poo2.model.Cliente;
import com.example.poo2.model.CuentaCorriente;
import com.example.poo2.model.MovimientoCuentaCorriente;
import com.example.poo2.repository.ClienteRepository;
import com.example.poo2.repository.CuentaCorrienteRepository;
import com.example.poo2.repository.MovimientoCuentaCorrienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CuentaCorrienteService {

    @Autowired
    private CuentaCorrienteRepository cuentaCorrienteRepository;

    @Autowired
    private MovimientoCuentaCorrienteRepository movimientoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public List<CuentaCorriente> findAll() {
        return cuentaCorrienteRepository.findAll();
    }

    public Optional<CuentaCorriente> findById(Long id) {
        return cuentaCorrienteRepository.findById(id);
    }

    public Optional<CuentaCorriente> findByClienteId(Long clienteId) {
        return cuentaCorrienteRepository.findByClienteId(clienteId);
    }

    /**
     * Obtiene la cuenta corriente de un cliente, o la crea si no existe.
     */
    public CuentaCorriente findOrCreateByClienteId(Long clienteId) {
        return cuentaCorrienteRepository.findByClienteId(clienteId)
                .orElseGet(() -> {
                    Cliente cliente = clienteRepository.findById(clienteId)
                            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
                    CuentaCorriente nueva = new CuentaCorriente(cliente, 0.0);
                    return cuentaCorrienteRepository.save(nueva);
                });
    }

    /**
     * Registra un DÉBITO (el cliente debe más / se le carga un monto).
     * El saldo aumenta (saldo positivo = el cliente debe).
     */
    public MovimientoCuentaCorriente registrarDebito(Long cuentaId, String concepto, Double monto) {
        CuentaCorriente cuenta = cuentaCorrienteRepository.findById(cuentaId)
                .orElseThrow(() -> new RuntimeException("Cuenta corriente no encontrada"));

        double nuevoSaldo = cuenta.getSaldo() + monto;
        cuenta.setSaldo(nuevoSaldo);
        cuenta.setFechaUltimoMovimiento(LocalDate.now());
        cuentaCorrienteRepository.save(cuenta);

        return movimientoRepository.save(
                new MovimientoCuentaCorriente(cuenta, "DEBITO", concepto, monto, nuevoSaldo));
    }

    /**
     * Registra un CRÉDITO (el cliente paga / se le abona un monto).
     * El saldo disminuye.
     */
    public MovimientoCuentaCorriente registrarCredito(Long cuentaId, String concepto, Double monto) {
        CuentaCorriente cuenta = cuentaCorrienteRepository.findById(cuentaId)
                .orElseThrow(() -> new RuntimeException("Cuenta corriente no encontrada"));

        double nuevoSaldo = cuenta.getSaldo() - monto;
        cuenta.setSaldo(nuevoSaldo);
        cuenta.setFechaUltimoMovimiento(LocalDate.now());
        cuentaCorrienteRepository.save(cuenta);

        return movimientoRepository.save(
                new MovimientoCuentaCorriente(cuenta, "CREDITO", concepto, monto, nuevoSaldo));
    }

    public List<MovimientoCuentaCorriente> getMovimientos(Long cuentaId) {
        return movimientoRepository.findByCuentaCorrienteIdOrderByFechaDesc(cuentaId);
    }

    public CuentaCorriente setLimiteCredito(Long cuentaId, Double limite) {
        CuentaCorriente cuenta = cuentaCorrienteRepository.findById(cuentaId)
                .orElseThrow(() -> new RuntimeException("Cuenta corriente no encontrada"));
        cuenta.setLimiteCredito(limite);
        return cuentaCorrienteRepository.save(cuenta);
    }

    public CuentaCorriente activar(Long id) {
        CuentaCorriente c = cuentaCorrienteRepository.findById(id).orElseThrow();
        c.setActiva(true);
        return cuentaCorrienteRepository.save(c);
    }

    public CuentaCorriente desactivar(Long id) {
        CuentaCorriente c = cuentaCorrienteRepository.findById(id).orElseThrow();
        c.setActiva(false);
        return cuentaCorrienteRepository.save(c);
    }
}
