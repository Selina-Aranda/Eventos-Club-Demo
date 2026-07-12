package com.example.demo.controller;

import com.example.demo.dto.ReservaDTO;
import com.example.demo.modelo.Evento;
import com.example.demo.modelo.EventoReservado;
import com.example.demo.modelo.Usuario;
import com.example.demo.repositorio.EventoRepository;
import com.example.demo.repositorio.EventoReservadoRepository;
import com.example.demo.repositorio.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ReservaController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private EventoReservadoRepository eventoReservadoRepository;

    // ESTA ES LA RUTA EN EL NAVEGADOR
    @GetMapping("/reserva")
    public String mostrarFormulario() {
        return "reserva"; 
    }

    @PostMapping("/api/reservar/enviar")
    @ResponseBody
    public ResponseEntity<?> procesarReserva(@RequestBody ReservaDTO dto) {
        Map<String, String> response = new HashMap<>();
        try {
            LocalDate fechaDeseada = LocalDate.parse(dto.getFecha());

            // 1. VALIDACIÓN DE DISPONIBILIDAD
            if (eventoReservadoRepository.existeReservaEnFecha(fechaDeseada)) {
                response.put("error", "Lo sentimos, esa fecha ya está reservada. Por favor elige otra.");
                return ResponseEntity.badRequest().body(response); 
            }

            // 2. LÓGICA DEL USUARIO
            Usuario cliente = usuarioRepository.findByEmail(dto.getCorreo());
            if (cliente == null) {
                cliente = new Usuario();
                cliente.setNombre(dto.getNombre());
                cliente.setEmail(dto.getCorreo());
                cliente = usuarioRepository.save(cliente);
            }

            // 3. LÓGICA DEL EVENTO/SERVICIO
            Evento evento = eventoRepository.findByTipo(dto.getServicio())
                .orElseGet(() -> {
                    Evento nuevoEvento = new Evento();
                    nuevoEvento.setTipo(dto.getServicio());
                    nuevoEvento.setDescripcion("Servicio automático generado desde reservas");
                    return eventoRepository.save(nuevoEvento);
                });

            // 4. CREAR LA RESERVA FINAL
            EventoReservado reserva = new EventoReservado();
            reserva.setCliente(cliente);
            reserva.setEvento(evento);
            reserva.setTelefono(dto.getNumero());
            reserva.setObservaciones(dto.getDetalles());
            reserva.setFecha_reservada(fechaDeseada);
            reserva.setEstado("PENDIENTE_CONFIRMACION"); 

            eventoReservadoRepository.save(reserva);

            response.put("mensaje", "¡Reserva solicitada con éxito! La fecha ha sido bloqueada.");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", "Error interno al procesar la reserva. Verifica el formato de fecha.");
            return ResponseEntity.internalServerError().body(response);
        }
    }
}