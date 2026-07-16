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
    public String reserva(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/Login";
        }

        model.addAttribute("nombreUsuario", usuario.getNombre());
        return "reserva"; // Carga reserva.html
    }

@GetMapping("/reservar")
public String mostrarFormularioReserva(
        @RequestParam Integer id_evento,
        @RequestParam String evento,
        @RequestParam double precio,
        @RequestParam String img,
        HttpSession session,
        Model model
) {
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null) {
        return "redirect:/Login";
    }

    model.addAttribute("cliente", usuario.getNombre());
    model.addAttribute("evento", evento);
    model.addAttribute("precio", precio);
    model.addAttribute("img", img);
    model.addAttribute("id_evento", id_evento); 

    return "formReserva";
}

    @PostMapping("/reservas/guardar")
    public String guardarReserva(
            @RequestParam("id_evento") Integer id_evento,
            @ModelAttribute EventoReservado reserva,
            HttpSession session,
            Model model) {
        System.out.println("==== ID DEL EVENTO RECIBIDO EN POST: " + id_evento + " ====");
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/Login";
        }

        Evento evento = eventoServicio.obtenerPorId(id_evento);

        if(evento == null){
            return "redirect:/servicios";
        }
        reserva.setCliente(usuario);
        reserva.setEvento(evento);
        double precio = evento.getPrecio();

        reserva.setEstado("Confirmado");

        reservaServicio.guardar(reserva);
        model.addAttribute("reserva", reserva);

        model.addAttribute("cliente", usuario.getNombre());
        model.addAttribute("evento", reserva.getEvento());
        model.addAttribute("precio", precio);
        model.addAttribute("fecha", reserva.getFecha_reservada());
        model.addAttribute("hora", reserva.getHora_reservada());
        model.addAttribute("telefono", reserva.getTelefono());
        model.addAttribute("metodo", reserva.getMetodo_pago());
        model.addAttribute("estado", reserva.getEstado());

        return "reserva-confirmada";
    }
}