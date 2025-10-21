package com.example.MiFinanzas.controller;

import com.example.MiFinanzas.model.Usuario;
import com.example.MiFinanzas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistroController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        System.out.println("[REGISTRO] Mostrando formulario");
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute Usuario usuario) {
        System.out.println("[REGISTRO] Intentando registrar: " + usuario.getEmail());

        // Encriptar contraseña antes de guardar
        String encriptada = passwordEncoder.encode(usuario.getContraseña());
        System.out.println("[REGISTRO] Contraseña cifrada: " + encriptada);

        usuario.setContraseña(encriptada);
        usuarioService.guardar(usuario);

        System.out.println("[REGISTRO] Usuario guardado");
        return "redirect:/login";
    }
}
