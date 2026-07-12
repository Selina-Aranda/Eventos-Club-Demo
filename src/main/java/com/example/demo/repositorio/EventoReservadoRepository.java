package com.example.demo.repositorio;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.modelo.EventoReservado;

@Repository
public interface EventoReservadoRepository extends JpaRepository<EventoReservado, Integer> {

    @Query("SELECT r FROM EventoReservado r ORDER BY r.fecha_reservada DESC")
    List<EventoReservado> obtenerUltimasReservas(Pageable pageable);

    // 👇 ESTA ES LA MAGIA PARA VALIDAR DISPONIBILIDAD
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM EventoReservado r WHERE r.fecha_reservada = :fecha")
    boolean existeReservaEnFecha(@Param("fecha") LocalDate fecha);
}