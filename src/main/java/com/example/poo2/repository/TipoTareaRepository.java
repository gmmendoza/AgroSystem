package com.example.poo2.repository;

import com.example.poo2.model.TipoTarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoTareaRepository extends JpaRepository<TipoTarea, Long> {
}
