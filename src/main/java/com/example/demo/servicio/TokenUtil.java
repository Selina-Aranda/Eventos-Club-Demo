package com.example.demo.servicio;

import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class TokenUtil {

    public String generateCode() {
        int code = (int)(Math.random() * 900000) + 100000;
        return String.valueOf(code);
    }
}
