package com.example.demo.repositorio;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modelo.Evento;

public interface EventoRepository extends JpaRepository<Evento, Integer> {}