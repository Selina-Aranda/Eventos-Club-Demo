package com.example.demo.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendRecoveryCode(String to, String code) {

    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(to);
    message.setSubject("Código de recuperación");
    message.setText("Tu código de recuperación es: " + code + "\n\nExpira en 10 minutos.");

    mailSender.send(message);
}

}
