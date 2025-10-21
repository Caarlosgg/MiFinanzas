package com.example.MiFinanzas.controller;

import com.example.MiFinanzas.model.Presupuesto;
import com.example.MiFinanzas.model.Usuario;
import com.example.MiFinanzas.service.MovimientoService;
import com.example.MiFinanzas.service.PresupuestoService;
import com.example.MiFinanzas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/presupuestos")
public class PresupuestoController {

    @Autowired private PresupuestoService presupuestoService;
    @Autowired private MovimientoService movimientoService;
    @Autowired private UsuarioService usuarioService;

    @GetMapping
    public String listarPresupuestos(Model model, Authentication auth) {
        // 1) ID real del usuario
        Usuario u = usuarioService
                .buscarPorEmail(auth.getName())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        String userId = u.getId();

        // 2) Traigo presupuestos
        List<Presupuesto> presupuestos = presupuestoService.listarPorUsuario(userId);

        // 3) Calculo gastos reales por presupuesto
        Map<String, Double> gastos = new HashMap<>();
        for (Presupuesto p : presupuestos) {
            double totalGasto = movimientoService
                    .totalGastosPorCategoria(userId, p.getCategoria());
            gastos.put(p.getId(), totalGasto);
        }

        model.addAttribute("presupuestos", presupuestos);
        model.addAttribute("gastos", gastos);
        return "presupuestos/lista";
    }

    @GetMapping("/nuevo")
    public String nuevoPresupuesto(Model model) {
        model.addAttribute("presupuesto", new Presupuesto());
        return "presupuestos/formulario";
    }

    @PostMapping("/guardar")
    public String guardarPresupuesto(@ModelAttribute Presupuesto p, Authentication auth) {
        Usuario u = usuarioService.buscarPorEmail(auth.getName())
                .orElseThrow();
        p.setUsuarioId(u.getId());
        presupuestoService.guardar(p);
        return "redirect:/presupuestos";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarPresupuesto(@PathVariable("id") String id) {
        presupuestoService.eliminar(id);
        return "redirect:/presupuestos";
    }
}
