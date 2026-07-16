package com.example.demo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.modelo.Cotizacion;

@Repository
public interface CotizacionRepository extends JpaRepository<Cotizacion, Integer> {
}