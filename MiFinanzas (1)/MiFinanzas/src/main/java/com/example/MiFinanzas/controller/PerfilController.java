package com.example.MiFinanzas.controller;

import com.example.MiFinanzas.model.Usuario;
import com.example.MiFinanzas.service.MovimientoService;
import com.example.MiFinanzas.service.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Controller
@RequestMapping("/usuarios")
public class PerfilController {

    private final UsuarioService usuarioService;
    private final MovimientoService movimientoService;

    public PerfilController(UsuarioService usuarioService,
                            MovimientoService movimientoService) {
        this.usuarioService  = usuarioService;
        this.movimientoService = movimientoService;
    }

    @GetMapping("/perfil")
    public String mostrarPerfil(Model model, Authentication auth) {
        Usuario u = usuarioService
                .buscarPorEmail(auth.getName())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        double ingresos = movimientoService.totalPorTipo(u.getId(), "ingreso");
        double gastos   = movimientoService.totalPorTipo(u.getId(), "gasto");
        double balance  = ingresos - gastos;

        model.addAttribute("usuario", u);
        model.addAttribute("ingresos", ingresos);
        model.addAttribute("gastos", gastos);
        model.addAttribute("balance", balance);
        return "usuarios/perfil";
    }

    @PostMapping("/subirFoto")
    public String subirFoto(@RequestParam("foto") MultipartFile foto,
                            Authentication auth) throws IOException {
        Usuario u = usuarioService
                .buscarPorEmail(auth.getName())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if (!foto.isEmpty()) {
            byte[] bytes = foto.getBytes();
            String b64 = Base64.getEncoder().encodeToString(bytes);
            String mime = foto.getContentType();
            u.setFoto("data:" + mime + ";base64," + b64);
            usuarioService.guardar(u);
        }
        return "redirect:/usuarios/perfil";
    }
}
