package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.modelo.Evento;
import com.example.demo.modelo.EventoReservado;
import com.example.demo.modelo.Usuario;
import com.example.demo.servicio.EventoService;
import com.example.demo.servicio.ReservaService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ReservaController {

    @Autowired

    ReservaService reservaServicio;

    @Autowired
    EventoService eventoServicio;

    @GetMapping("/reserva")
    public String reserva(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
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
        return "redirect:/login";
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

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        reserva.setCliente(usuario);

        Evento evento = eventoServicio.obtenerPorId(id_evento);
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
