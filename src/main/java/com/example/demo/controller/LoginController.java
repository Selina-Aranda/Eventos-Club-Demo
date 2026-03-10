package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.modelo.Usuario;
import com.example.demo.repositorio.UsuarioRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UsuarioRepository repo;

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login"; 
    }

    @PostMapping("/login")
    public String login(@RequestParam String nombre,
                        @RequestParam String contraseña,
                        HttpSession session,
                        Model model) {

        Usuario usuario = repo.findByNombreAndContraseña(nombre, contraseña);

        if (usuario != null) {
      
            session.setAttribute("usuario", usuario);

       
            if ("ADMIN".equalsIgnoreCase(usuario.getRol())) {
                return "redirect:/admin";   
            } else {
                return "redirect:/reserva"; 
            }
        } else {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    //registro

    @GetMapping("/registro")
    public String registro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "Login";
    }

    @PostMapping("/registro")
    public String guardarRegistro(@ModelAttribute Usuario usuario) {
        repo.save(usuario);
        return "Login";
    }
    
    /*@GetMapping("/recover")
    public String recoverPage() {
        return "recover";  // Nombre del archivo recover.html
    }
    */
}
