package com.gestion.app.repositories;

import com.gestion.app.models.Proyecto;
import com.gestion.app.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {
    List<Proyecto> findByUsuario(Usuario usuario);
    long countByUsuario(Usuario usuario);
}