package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.modelo.Usuario;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @GetMapping("/")
    public String root(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario != null) {
            if ("ADMIN".equalsIgnoreCase(usuario.getRol())) return "redirect:/admin";
            else return "redirect:/reserva";
        }

        return "index";
    }
}
