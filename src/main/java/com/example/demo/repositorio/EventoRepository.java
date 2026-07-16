package com.example.demo.repositorio;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.modelo.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer> {
    // Como tu clase Evento usa "tipo" (ej: cumpleaños, boda), buscamos por ahí
    Optional<Evento> findByTipo(String tipo); 
}