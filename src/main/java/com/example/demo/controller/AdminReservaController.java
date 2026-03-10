package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.modelo.EventoReservado;
import com.example.demo.servicio.EventoService;
import com.example.demo.servicio.ReservaService;
import com.example.demo.servicio.UsuarioService;

@Controller
public class AdminReservaController {
    @Autowired
    private ReservaService reservaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EventoService eventoService;

    @GetMapping("/reservas")
    public String mostrarReservas(Model model){
        model.addAttribute("reserva", new EventoReservado());
        model.addAttribute("reservas", reservaService.listar());
        model.addAttribute("usuarios", usuarioService.listar());
        model.addAttribute("eventos", eventoService.listar());
        return "reservasCrud";
    }

    @PostMapping("/admin/reservas/guardar")
    public String guardarReserva(@ModelAttribute EventoReservado reserva) {
        reservaService.guardar(reserva);
        return "redirect:/reservas?success";
    }

    @GetMapping("/admin/reservas/editar/{id}")
    public String editarReserva(@PathVariable Integer id, Model model) {

        EventoReservado reserva = reservaService.obtenerPorId(id);
        
        model.addAttribute("reserva", reserva);
        model.addAttribute("reservas", reservaService.listar());
        model.addAttribute("usuarios", usuarioService.listar());
        model.addAttribute("eventos", eventoService.listar());
        return "reservasCrud";

    }

    @GetMapping("/admin/reservas/eliminar/{id}")
    public String eliminarReserva(@PathVariable Integer id){
        reservaService.eliminar(id);
        return "redirect:/reservas?deleted";
    }
}
