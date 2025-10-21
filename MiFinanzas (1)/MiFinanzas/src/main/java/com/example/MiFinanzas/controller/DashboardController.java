package com.example.MiFinanzas.controller;

import com.example.MiFinanzas.model.MetaAhorro;
import com.example.MiFinanzas.model.Movimiento;
import com.example.MiFinanzas.model.Presupuesto;
import com.example.MiFinanzas.model.Usuario;
import com.example.MiFinanzas.service.MetaAhorroService;
import com.example.MiFinanzas.service.MovimientoService;
import com.example.MiFinanzas.service.PresupuestoService;
import com.example.MiFinanzas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class DashboardController {

    @Autowired private UsuarioService usuarioService;
    @Autowired private MovimientoService movimientoService;
    @Autowired private PresupuestoService presupuestoService;
    @Autowired private MetaAhorroService metaAhorroService;

    @GetMapping({"/", "/dashboard"})
    public String mostrarDashboard(Model model, Authentication auth) {
        // 1) Sacamos el ID real del usuario
        Usuario u = usuarioService
                .buscarPorEmail(auth.getName())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        String userId = u.getId();

        // 2) Movimientos
        List<Movimiento> movimientos = movimientoService.listarPorUsuario(userId);

        // 3) Totales
        double totalIngresos = movimientos.stream()
                .filter(m -> "ingreso".equalsIgnoreCase(m.getTipo()))
                .mapToDouble(Movimiento::getCantidad).sum();

        double totalGastos = movimientos.stream()
                .filter(m -> "gasto".equalsIgnoreCase(m.getTipo()))
                .mapToDouble(Movimiento::getCantidad).sum();

        double balanceTotal = totalIngresos - totalGastos;

        // 4) Gastos por categoría
        Map<String, Double> categorias = movimientos.stream()
                .filter(m -> "gasto".equalsIgnoreCase(m.getTipo()))
                .collect(Collectors.groupingBy(
                        Movimiento::getCategoria,
                        Collectors.summingDouble(Movimiento::getCantidad)
                ));

        // 5) Últimos 5 por fecha
        List<Movimiento> ultimosMovimientos = movimientos.stream()
                .filter(m -> m.getFecha()!=null)
                .sorted(Comparator.comparing(Movimiento::getFecha).reversed())
                .limit(5)
                .collect(Collectors.toList());

        // 6) Presupuestos
        List<Presupuesto> presupuestos = presupuestoService.listarPorUsuario(userId);
        int totalPresupuestos = presupuestos.size(), superados = 0, dentro = 0;
        for (Presupuesto p : presupuestos) {
            double gastCat = movimientoService
                    .totalGastosPorCategoria(userId, p.getCategoria());
            if (gastCat > p.getLimite()) superados++;
            else dentro++;
        }

        // 7) Metas
        List<MetaAhorro> metas = metaAhorroService.listarPorUsuario(userId);

        // 8) Cargo el modelo
        model.addAttribute("ingresosTotales", totalIngresos);
        model.addAttribute("gastosTotales",   totalGastos);
        model.addAttribute("balanceTotal",     balanceTotal);
        model.addAttribute("categorias",       categorias);
        model.addAttribute("ultimosMovimientos", ultimosMovimientos);
        model.addAttribute("totalPresupuestos",   totalPresupuestos);
        model.addAttribute("presupuestosSuperados", superados);
        model.addAttribute("presupuestosDentro",    dentro);
        model.addAttribute("metas", metas);

        return "dashboard";
    }
}
