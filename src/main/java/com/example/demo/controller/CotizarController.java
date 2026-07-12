package com.example.demo.controller;

import com.example.demo.dto.CotizacionDTO;
import com.example.demo.modelo.Cotizacion;
import com.example.demo.repositorio.CotizacionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CotizarController {

    @Autowired
    private CotizacionRepository cotizacionRepository;

    @GetMapping("/cotizar")
    public String mostrarFormulario() {
        return "cotizar";
    }

    @PostMapping("/api/cotizar/enviar")
    @ResponseBody
    public ResponseEntity<?> recibirCotizacion(@RequestBody CotizacionDTO cotizacionDTO) {
        Map<String, String> response = new HashMap<>();
        try {
            // Pasamos los datos del DTO a nuestra nueva entidad
            Cotizacion nuevaCotizacion = new Cotizacion();
            nuevaCotizacion.setNombre(cotizacionDTO.getNombre());
            nuevaCotizacion.setCorreo(cotizacionDTO.getCorreo());
            nuevaCotizacion.setTelefono(cotizacionDTO.getNumero());
            nuevaCotizacion.setServicio(cotizacionDTO.getServicio());
            nuevaCotizacion.setDetalles(cotizacionDTO.getDetalles());
            
            // Datos automáticos del sistema
            nuevaCotizacion.setEstado("PENDIENTE");
            nuevaCotizacion.setFechaSolicitud(LocalDateTime.now());

            // Guardamos directamente en la tabla 'cotizaciones'
            cotizacionRepository.save(nuevaCotizacion);

            response.put("mensaje", "Cotización enviada exitosamente");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", "Error al guardar la cotización");
            return ResponseEntity.internalServerError().body(response);
        }
    }
}