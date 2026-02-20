package com.example.poo2.service;

import com.example.poo2.model.Almacen;
import com.example.poo2.repository.AlmacenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlmacenService {

    @Autowired
    private AlmacenRepository almacenRepository;

    public List<Almacen> findAll() {
        return almacenRepository.findAll();
    }

    public List<Almacen> findActivos() {
        return almacenRepository.findAll().stream()
                .filter(Almacen::isActivo)
                .collect(Collectors.toList());
    }

    public Optional<Almacen> findById(Long id) {
        return almacenRepository.findById(id);
    }

    public Almacen save(Almacen almacen) {
        return almacenRepository.save(almacen);
    }

    public void delete(Long id) {
        almacenRepository.deleteById(id);
    }
}
