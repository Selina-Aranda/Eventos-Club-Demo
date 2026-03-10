package com.example.demo.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String codigo;    // <-- Código de 6 dígitos
    private LocalDateTime expiration;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public LocalDateTime getExpiration() {
        return expiration;
    }
    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }

    // getters y setters
    
}
