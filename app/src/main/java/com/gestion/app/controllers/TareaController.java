package com.gestion.app.controllers;

import com.gestion.app.models.Tarea;
import com.gestion.app.repositories.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tareas")
public class TareaController {

    @Autowired
    private TareaRepository tareaRepository;

    // GET http://localhost:8080/api/tareas
    @GetMapping
    public ResponseEntity<List<Tarea>> listarTareas() {
        return ResponseEntity.ok(tareaRepository.findAll());
    }

    // POST http://localhost:8080/api/tareas
    @PostMapping
    public ResponseEntity<Tarea> crearTarea(@RequestBody Tarea tarea) {
        Tarea nuevaTarea = tareaRepository.save(tarea);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaTarea);
    }
}