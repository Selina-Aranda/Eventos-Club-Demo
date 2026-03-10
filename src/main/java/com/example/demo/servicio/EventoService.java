package com.example.demo.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.modelo.Evento;
import com.example.demo.repositorio.EventoRepository;

@Service
public class EventoService {
    @Autowired
    private EventoRepository eventoRepository;


    public List<Evento> listar(){
        return eventoRepository.findAll();
    }

    public Evento obtenerPorId(Integer id) {
        return eventoRepository.findById(id).orElse(null);
    }

    public void guardar(Evento evento) {
        eventoRepository.save(evento);
    }

    public void eliminar(Integer id) {
        eventoRepository.deleteById(id);
    }
}
