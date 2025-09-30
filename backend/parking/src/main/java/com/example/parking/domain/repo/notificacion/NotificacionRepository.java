package com.example.parking.domain.repo.notificacion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.parking.domain.model.notificacion.Notificacion;
import com.example.parking.domain.model.usuario.Usuario;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
	List<Notificacion> findByUsuarioOrderByFechaDesc(Usuario usuario);
}
