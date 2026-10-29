package com.gestion.app.controllers;

import com.gestion.app.models.Usuario;
import com.gestion.app.repositories.UsuarioRepository;
import com.gestion.app.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // Endpoint ara registrar usuarios
    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        Usuario nuevoUsuario = usuarioRepository.save(usuario);


        nuevoUsuario.setPassword(null);
        return ResponseEntity.status(201).body(nuevoUsuario);
    }

    //Endpoint público para hacer login y recibir el token JWT
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        try {
            // Buscamos si el usuario existe y validamos su contraseña
            Usuario usuarioDb = usuarioRepository.findByUsername(usuario.getUsername())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            if (!passwordEncoder.matches(usuario.getPassword(), usuarioDb.getPassword())) {
                return ResponseEntity.status(401).body("Contraseña incorrecta");
            }


            String token = jwtUtil.generateToken(usuarioDb.getUsername());

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("username", usuarioDb.getUsername());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Error en el login: " + e.getMessage());
        }
    }
}