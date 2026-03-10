package com.example.demo.controller;

import com.example.demo.modelo.PasswordResetToken;
import com.example.demo.modelo.Usuario;
import com.example.demo.repositorio.PasswordResetTokenRepository;
import com.example.demo.repositorio.UsuarioRepository;
import com.example.demo.servicio.EmailService;
import com.example.demo.servicio.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class RecoverController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private PasswordResetTokenRepository tokenRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    // FORM para ingresar email
    @GetMapping("/recover")
    public String recoverPage() {
        return "recover";
    }

    // Enviar código
    @PostMapping("/recover")
    public String processRecovery(@RequestParam String email, Model model) {

        Usuario user = usuarioRepo.findByEmail(email);
        if (user == null) {
            model.addAttribute("error", "No existe un usuario con ese email.");
            return "recover";
        }

        String code = tokenUtil.generateCode();

        PasswordResetToken reset = new PasswordResetToken();
        reset.setEmail(email);
        reset.setCodigo(code);
        reset.setExpiration(LocalDateTime.now().plusMinutes(10));

        tokenRepo.save(reset);

        emailService.sendRecoveryCode(email, code);

        model.addAttribute("email", email);
        return "verify-code"; // Pagina para ingresar el codigo
    }

    // Validar código
    @PostMapping("/recover/verify")
    public String verifyCode(@RequestParam String code, Model model) {

        PasswordResetToken reset = tokenRepo.findByCodigo(code).orElse(null);

        if (reset == null || reset.getExpiration().isBefore(LocalDateTime.now())) {
            model.addAttribute("error", "Código inválido o expirado");
            return "verify-code";
        }

        model.addAttribute("code", code);
        return "reset-password"; // Form para cambiar contraseña
    }

    // Guardar nueva contraseña
    @PostMapping("/recover/save")
    public String saveNewPassword(@RequestParam String code,
            @RequestParam String password,
            Model model) {

        PasswordResetToken reset = tokenRepo.findByCodigo(code).orElse(null);

        if (reset == null) {
            model.addAttribute("error", "Código inválido.");
            return "reset-password";
        }

        Usuario user = usuarioRepo.findByEmail(reset.getEmail());
        user.setContraseña(password);
        usuarioRepo.save(user);

        tokenRepo.delete(reset);

        model.addAttribute("message", "Contraseña actualizada.");
        return "redirect:/login";
    }
}
