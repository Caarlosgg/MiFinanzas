package com.example.MiFinanzas.controller;

import com.example.MiFinanzas.model.Movimiento;
import com.example.MiFinanzas.model.Usuario;
import com.example.MiFinanzas.service.MetaAhorroService;
import com.example.MiFinanzas.service.MovimientoService;
import com.example.MiFinanzas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MetaAhorroService metaAhorroService;

    @GetMapping
    public String listarMovimientos(Model model, Authentication auth) {
        Usuario u = usuarioService.buscarPorEmail(auth.getName())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        model.addAttribute("movimientos",
                movimientoService.listarPorUsuario(u.getId()));
        return "movimientos/lista";
    }

    @GetMapping("/nuevo")
    public String nuevoMovimiento(Model model, Authentication auth) {
        model.addAttribute("movimiento", new Movimiento());
        Usuario u = usuarioService.buscarPorEmail(auth.getName())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        model.addAttribute("metas",
                metaAhorroService.listarPorUsuario(u.getId()));
        return "movimientos/formulario";
    }

    @PostMapping("/guardar")
    public String guardarMovimiento(@ModelAttribute Movimiento movimiento,
                                    Authentication auth) {
        Usuario u = usuarioService.buscarPorEmail(auth.getName())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        movimiento.setUsuarioId(u.getId());
        if (movimiento.getFecha() == null) {
            movimiento.setFecha(LocalDate.now());
        }
        movimientoService.guardar(movimiento);
        if ("ingreso".equalsIgnoreCase(movimiento.getTipo())
                && movimiento.getMetaId() != null
                && !movimiento.getMetaId().isBlank()) {
            metaAhorroService.sumarAhorroAMeta(
                    movimiento.getMetaId(),
                    movimiento.getCantidad()
            );
        }
        return "redirect:/movimientos";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarMovimiento(
            @PathVariable("id") String id,      // ← obligatorio el nombre
            Authentication auth) {
        Usuario u = usuarioService.buscarPorEmail(auth.getName())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        movimientoService.eliminarSiEsDelUsuario(id, u.getId());
        return "redirect:/movimientos";
    }
}
