package com.example.MiFinanzas.controller;

import com.example.MiFinanzas.model.MetaAhorro;
import com.example.MiFinanzas.model.Usuario;
import com.example.MiFinanzas.service.MetaAhorroService;
import com.example.MiFinanzas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/metas")
public class MetaAhorroController {

    @Autowired
    private MetaAhorroService metaAhorroService;

    @Autowired
    private UsuarioService usuarioService;

    /** Lista todas las metas del usuario */
    @GetMapping
    public String listarMetas(Model model, Authentication auth) {
        Usuario u = usuarioService
                .buscarPorEmail(auth.getName())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        List<MetaAhorro> metas = metaAhorroService.listarPorUsuario(u.getId());
        model.addAttribute("metas", metas);
        return "metas/lista";
    }

    /** Muestra el formulario para crear una meta nueva */
    @GetMapping("/nuevo")
    public String nuevoFormulario(Model model) {
        model.addAttribute("meta", new MetaAhorro());
        return "metas/formulario";
    }

    /** Guarda la nueva meta */
    @PostMapping("/guardar")
    public String guardarMeta(@ModelAttribute("meta") MetaAhorro meta,
                              Authentication auth) {
        Usuario u = usuarioService
                .buscarPorEmail(auth.getName())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        meta.setUsuarioId(u.getId());
        // actual ya viene a 0.0 por defecto
        metaAhorroService.guardar(meta);
        return "redirect:/metas";
    }

    /** Elimina una meta (sólo si pertenece al usuario) */
    @PostMapping("/eliminar/{id}")
    public String eliminarMeta(@PathVariable("id") String id,
                               Authentication auth) {
        Usuario u = usuarioService
                .buscarPorEmail(auth.getName())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        metaAhorroService.eliminarSiEsDelUsuario(id, u.getId());
        return "redirect:/metas";
    }
}
