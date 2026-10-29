package com.gestion.app.services;

import com.gestion.app.models.Proyecto;
import com.gestion.app.models.Usuario;
import com.gestion.app.repositories.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProyectoService {

    @Autowired
    private ProyectoRepository proyectoRepository;

    public List<Proyecto> obtenerProyectosPorUsuario(Usuario usuario) {
        return proyectoRepository.findByUsuario(usuario);
    }

    public Proyecto crearProyecto(Proyecto proyecto, Usuario usuario) {
        // REGLA DE NEGOCIO: Un usuario no puede tener más de 3 proyectos activos
        long proyectosActivos = proyectoRepository.countByUsuario(usuario);
        System.out.println(">>> Proyectos activos encontrados para este usuario: " + proyectosActivos);

        // Cambiamos el límite máximo son 3
        if (proyectosActivos >= 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Límite alcanzado: Un usuario no puede tener más de 3 proyectos activos creados en su cuenta.");
        }
        proyecto.setUsuario(usuario);
        return proyectoRepository.save(proyecto);
    }

}
