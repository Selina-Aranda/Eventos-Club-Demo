package com.example.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.modelo.Evento;
import com.example.demo.servicio.EventoService;

@Controller
public class AdminEventosController {

    @Autowired
    private EventoService eventoService;

    @GetMapping("/eventos")
    public String mostrarEventos(Model model) {
        model.addAttribute("evento", new Evento());
        model.addAttribute("eventos", eventoService.listar());
        return "eventosCrud";
    }

   @PostMapping("/admin/eventos/guardar")
public String guardarEvento(@ModelAttribute Evento evento,
                            @RequestParam("archivoImagen") MultipartFile archivo) throws IOException {

    if (!archivo.isEmpty()) {
        String nombreImagen = archivo.getOriginalFilename();

        Path ruta = Paths.get("src/main/resources/static/img/eventos/" + nombreImagen);
        Files.write(ruta, archivo.getBytes());

        
        evento.setRuta_imagen("eventos/" + nombreImagen);
    }

    eventoService.guardar(evento);
    return "redirect:/eventos?success";
}


    @GetMapping("/admin/eventos/editar/{id}")
    public String editarEventos(@PathVariable Integer id, Model model) {
        Evento evento = eventoService.obtenerPorId(id);
        model.addAttribute("evento", evento);
        model.addAttribute("eventos", eventoService.listar());
        return "eventosCrud";
    }

    @GetMapping("/admin/eventos/eliminar/{id}")
    public String eliminarEventos(@PathVariable Integer id) {
        eventoService.eliminar(id);
        return "redirect:/eventos?deleted";
    }

}
