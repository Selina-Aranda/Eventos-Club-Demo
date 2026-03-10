package com.example.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ServicioController {


    @GetMapping("/servicios")
    public String mostrarServicios() {

        return "Servicios";
    }
}
