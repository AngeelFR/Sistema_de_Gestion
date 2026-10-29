package com.gestion.app.services;

import com.gestion.app.models.Proyecto;
import com.gestion.app.models.Tarea;
import com.gestion.app.models.enums.EstadoTarea;
import com.gestion.app.repositories.ProyectoRepository;
import com.gestion.app.repositories.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TareaService {

    @Autowired
    private TareaRepository tareaRepository;

    @Autowired
    private ProyectoRepository proyectoRepository;

    public Tarea crearTarea(String titulo, Long proyectoId) {
        Proyecto proyecto = proyectoRepository.findById(proyectoId)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        Tarea tarea = new Tarea();
        tarea.setTitulo(titulo);
        tarea.setEstado(EstadoTarea.PENDIENTE.name());
        tarea.setProyecto(proyecto);

        return tareaRepository.save(tarea);
    }

    public Tarea actualizarEstado(Long tareaId, String nuevoEstado) {
        Tarea tarea = tareaRepository.findById(tareaId)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        tarea.setEstado(nuevoEstado);
        return tareaRepository.save(tarea);
    }
}
