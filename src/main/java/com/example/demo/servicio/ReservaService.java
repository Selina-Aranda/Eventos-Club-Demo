package com.example.demo.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.modelo.EventoReservado;
import com.example.demo.repositorio.EventoReservadoRepository;

@Service
public class ReservaService {

    @Autowired
    private EventoReservadoRepository reservaRepository;
    
    public List<EventoReservado> listar() {
        return reservaRepository.findAll();
    }

    public EventoReservado obtenerPorId(Integer id) {
        return reservaRepository.findById(id).orElse(null);
    }

    public void guardar(EventoReservado reserva) {
        reservaRepository.save(reserva);
    }

    public void eliminar(Integer id) {
        reservaRepository.deleteById(id);
    }

    public List<EventoReservado> obtenerUltimasReservas() {
        Pageable limit = PageRequest.of(0, 5);
        return reservaRepository.obtenerUltimasReservas(limit);
    }
}
