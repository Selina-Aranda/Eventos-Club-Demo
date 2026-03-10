package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.modelo.EventoReservado;
import com.example.demo.modelo.Usuario;
import com.example.demo.servicio.ReservaService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping("/admin")
    public String adminHome(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null || !"ADMIN".equalsIgnoreCase(usuario.getRol())) {
            return "redirect:/login";
        }

        model.addAttribute("nombre", usuario.getNombre());
        model.addAttribute("totalEventos", 8);
        model.addAttribute("totalReservas", 12);
        model.addAttribute("totalUsuarios", 25);

        List<EventoReservado> ultimas = reservaService.obtenerUltimasReservas();
        System.out.println("Reservas encontradas: " + ultimas.size());
        model.addAttribute("reservas", ultimas);

        return "admin"; // Carga admin.html
    }

    @GetMapping("/reportes")
    public String verReportes() {
        return "reporte";
    }

}
