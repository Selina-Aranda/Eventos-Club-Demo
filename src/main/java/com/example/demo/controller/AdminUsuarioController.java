package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.modelo.Usuario;
import com.example.demo.servicio.UsuarioService;



@Controller
public class AdminUsuarioController {
    @Autowired

    private UsuarioService usuarioService;

    @GetMapping("/usuarios")
    public String mostrarUsuarios(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("usuarios", usuarioService.listar());
        return "usuariosCrud";

    }

    @PostMapping("/admin/usuarios/guardar")
    public String guardarUsuario(@ModelAttribute Usuario usuario) {
        usuarioService.guardar(usuario);
        return"redirect:/usuarios?success";
    }
    
    @GetMapping("/admin/usuarios/editar/{id}")
    public String editarUsuario(@PathVariable Integer id, Model model) {
        Usuario usuario = usuarioService.obtenerPorId(id);
        model.addAttribute("usuario", usuario);
        model.addAttribute("usuarios", usuarioService.listar());
        return "usuariosCrud";
    }
    
    @GetMapping("/admin/usuarios/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Integer id) {
        usuarioService.eliminar(id);
        return "redirect:/usuarios?deleted";
    }
    
}
