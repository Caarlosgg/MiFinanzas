package com.example.MiFinanzas.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        // Invalida la sesión actual
        request.getSession().invalidate();
        // Redirige al login
        return "redirect:/login";
    }
}
