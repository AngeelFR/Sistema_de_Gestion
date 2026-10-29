package com.gestion.app.controllers;

import com.gestion.app.models.Usuario;
import com.gestion.app.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody Usuario usuario) {
        // Guardamos el usuario directamente en la base de datos
        Usuario nuevoUsuario = usuarioRepository.save(usuario);
        nuevoUsuario.setPassword(null); // Ocultar contraseña por seguridad
        return ResponseEntity.status(201).body(nuevoUsuario);

    }

}