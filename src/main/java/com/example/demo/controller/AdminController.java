package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.modelo.EventoReservado;
import com.example.demo.modelo.Usuario;
import com.example.demo.repositorio.EventoReservadoRepository;
import com.example.demo.servicio.ReservaService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private EventoReservadoRepository reservaRepository;

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
    public String verReportes(Model model) {
    List<Object[]> datosTipo = reservaRepository.contarReservasPorTipo();
    List<Object[]> datosMes = reservaRepository.contarReservasPorMes();

    // Convertir meses numéricos a nombres
    String[] nombresMeses = {"", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

    model.addAttribute("tipos", datosTipo.stream().map(obj -> obj[0].toString()).toList());
    model.addAttribute("cantidades", datosTipo.stream().map(obj -> obj[1]).toList());
    
    model.addAttribute("etiquetas", datosMes.stream().map(obj -> nombresMeses[((Number)obj[0]).intValue()]).toList());
    model.addAttribute("valores", datosMes.stream().map(obj -> obj[1]).toList());

    return "reporte";
    }

}
