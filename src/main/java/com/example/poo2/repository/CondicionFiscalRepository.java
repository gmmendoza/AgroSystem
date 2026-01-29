package com.example.poo2.repository;

import com.example.poo2.model.CondicionFiscal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CondicionFiscalRepository extends JpaRepository<CondicionFiscal, Long> {
    Optional<CondicionFiscal> findByNombre(String nombre);
}
