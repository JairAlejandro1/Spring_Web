package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;    

@Controller
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/inicio")
    public String handleInicio(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "password", required = false) String password,
            Model model,
            HttpSession session) {

        // Si ya hay una sesión activa
        if (session.getAttribute("username") != null) {
            model.addAttribute("username", session.getAttribute("username"));
            return "home";
        }

        // Si no hay parámetros, mostrar el formulario
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            return "login";
        }

        // Si hay parámetros, procesar el inicio de sesión
        User user = new User(username, password);

        if (userService.authenticate(user)) {
            // Login exitoso
            session.setAttribute("username", username);
            model.addAttribute("username", username);
            return "home";
        } else {
            // Login fallido
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            model.addAttribute("username", username);
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/inicio";
    }
}