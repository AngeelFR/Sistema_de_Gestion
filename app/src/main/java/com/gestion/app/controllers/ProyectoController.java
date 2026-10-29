package com.gestion.app.controllers;

import com.gestion.app.models.Proyecto;
import com.gestion.app.models.Usuario;
import com.gestion.app.repositories.UsuarioRepository;
import com.gestion.app.services.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<List<Proyecto>> listarProyectos() {
        return ResponseEntity.ok(proyectoService.obtenerProyectosPorUsuario(null)); // o como manejes tu listado general
    }

    @PostMapping
    public ResponseEntity<Proyecto> crearProyecto(@RequestBody Proyecto proyecto, Authentication authentication) {
        // Obtenemos el username del usuario autenticado a través del token JWT
        String username = authentication.getName();

        // buscamos el usuario completo en la base de datos
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        // aquí es donde se ejecuta la validación de los 3 proyectos)
        Proyecto nuevoProyecto = proyectoService.crearProyecto(proyecto, usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProyecto);
    }
}