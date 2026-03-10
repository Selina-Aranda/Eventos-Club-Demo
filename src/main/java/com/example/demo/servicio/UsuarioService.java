package com.example.demo.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.modelo.Usuario;
import com.example.demo.repositorio.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;


    public Usuario buscarPorNombreYContraseña(String nombre, String contraseña) {
        return usuarioRepository.findByNombreAndContraseña(nombre, contraseña);
    }

    public List<Usuario> listar(){
        return usuarioRepository.findAll();
    }

    public Usuario obtenerPorId(Integer id) {
       return usuarioRepository.findById(id).orElse(null);
    }

    public void guardar(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public void eliminar(Integer id) {
        usuarioRepository.deleteById(id);
    }

}
