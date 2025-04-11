package com.example.demo.service;

import com.example.demo.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    // Simulamos una base de datos con un Map para almacenar usuarios
    private final Map<String, String> users = new HashMap<>();

    public UserService() {
        // Usuarios de prueba
        users.put("admin", "admin123");
        users.put("usuario", "password");
        users.put("test", "test123");
    }

    public boolean authenticate(User user) {
        // Verificamos si el usuario existe y la contraseña es correcta
        String storedPassword = users.get(user.getUsername());
        return storedPassword != null && storedPassword.equals(user.getPassword());
    }
}