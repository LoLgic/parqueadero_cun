package com.example.parking.domain.service.vehiculo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.parking.domain.exception.ticket.AccesoNoPermitidoException;
import com.example.parking.domain.exception.usuario.UsuarioNoEncontradoException;
import com.example.parking.domain.exception.vehiculo.VehiculoConTicketsAsociadosException;
import com.example.parking.domain.exception.vehiculo.VehiculoDuplicadoException;
import com.example.parking.domain.exception.vehiculo.VehiculoNoEncontradoException;
import com.example.parking.domain.exception.vehiculo.VehiculoYaEliminadoException;
import com.example.parking.domain.model.ticket.EstadoTicket;
import com.example.parking.domain.model.usuario.Rol;
import com.example.parking.domain.model.usuario.Usuario;
import com.example.parking.domain.model.vehiculo.Vehiculo;
import com.example.parking.domain.repo.ticket.TicketRepository;
import com.example.parking.domain.repo.usuario.UsuarioRepository;
import com.example.parking.domain.repo.vehiculo.VehiculoRepository;
import com.example.parking.web.dto.vehiculo.VehiculoRequest;
import com.example.parking.web.dto.vehiculo.VehiculoResponse;
import com.example.parking.web.mapper.vehiculo.VehiculoMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VehiculoService {

	private final VehiculoRepository vehiculoRepository;
    private final UsuarioRepository usuarioRepository;
    private final TicketRepository ticketRepository;
    
    // CLIENTE: registrar su vehículo
    @Transactional
    public VehiculoResponse registrar(VehiculoRequest req, Authentication auth) {
    	// para buscar el token del usuario por username
    	// Usuario usuario = usuarioRepo.findByUsername(auth.getName())
    	
    	// para buscarlo por email como en com.example.parking.security.JwtUtil;
    	Usuario usuario = usuarioRepository.findByEmail(auth.getName())
    			.orElseThrow(()-> new UsuarioNoEncontradoException(
    					"No se encontró el usuario con email: " + auth.getName()));
    	
    	return vehiculoRepository.findByPlaca(req.placa())
    			.map(vehiculoExistente -> {
    				if (!vehiculoExistente.isEliminado()) {
    					// Caso 1: ya existe y está activo → error
    					throw new VehiculoDuplicadoException("La placa ya está registrada: " + req.placa());
    				} else {
    					// Caso 2: existe pero estaba eliminado → resurrección
    					vehiculoExistente.restaurar();
    					vehiculoExistente.setUsuario(usuario); // reasignar usuario actual
    					vehiculoExistente.setTipo(req.tipo());
    					Vehiculo restaurar = vehiculoRepository.save(vehiculoExistente);
    					return VehiculoMapper.toResponse(restaurar);
    				}
    			})
    			.orElseGet(() -> {
    				// Caso 3: no existe nunca → crear nuevo
    				Vehiculo nuevo = VehiculoMapper.toEntity(req, usuario);
    				Vehiculo guardado = vehiculoRepository.save(nuevo);
    				return VehiculoMapper.toResponse(guardado);
    			});	
	}
    
    
    // CLIENTE: listar sus vehículos
    @Transactional(readOnly = true)
    public  List<VehiculoResponse> listarMisVehiculos(Authentication auth) {
    	// para buscar el token del usuario por email 
    	Usuario usuario = usuarioRepository.findByEmail(auth.getName())
    			.orElseThrow(() -> new UsuarioNoEncontradoException(
    					"No se encontró el usuario con email: " + auth.getName()));
    	
    	
    	return vehiculoRepository.findByUsuario(usuario).stream()
    			.map(VehiculoMapper::toResponse)
    			.toList();
    }
    
    // ADMIN/OPERADOR: listar todos los vehículos
    @Transactional(readOnly = true)
    public List<VehiculoResponse> listarTodos() {
    	return vehiculoRepository.findAll().stream()
    			.map(VehiculoMapper::toResponse)
    			.toList();
    } 
    
    // Eliminar vehículo (CLIENTE solo los suyos, ADMIN/OPERADOR cualquiera)
    @Transactional
    public void eliminar(Long idVehiculo, Authentication  auth) {
    	Usuario usuario = usuarioRepository.findByEmail(auth.getName())
    			.orElseThrow(() -> new UsuarioNoEncontradoException(
    					"No se encontró el usuario con el email: " +auth.getName()));
    	
    	Vehiculo vehiculo = vehiculoRepository.findById(idVehiculo)
    			.orElseThrow(() -> new VehiculoNoEncontradoException(
    					"No se encontró el vehiculo con id: " + idVehiculo));
    	
    	//  Si ya está eliminado → no volver a eliminar
        if (vehiculo.isEliminado()) {
            throw new VehiculoYaEliminadoException(
                    "El vehículo con id " + idVehiculo + " ya fue eliminado o no está disponible");
        }
    	
    	// Validar permisos
    	if (usuario.getRol() == Rol.CLIENTE &&
			vehiculo.getUsuario() == null ||
			!vehiculo.getUsuario().getIdUsuario().equals(usuario.getIdUsuario())) {
    		throw new AccesoNoPermitidoException("No puedes eliminar un vehículo que no es tuyo");
    	}

    	
    	// Verificar si tiene tickets asociados
    	boolean tieneTicketsActivos = ticketRepository.existsByVehiculoAndEstado(vehiculo, EstadoTicket.ACTIVO);
    	
    	if (tieneTicketsActivos) {
    		throw new VehiculoConTicketsAsociadosException(
    				"El vehículo con placa " + vehiculo.getPlaca() + 
    				" tiene tickets asociados y no puede eliminarse");
    		
    	}
    	
    	
    	//  Soft delete
        vehiculo.setEliminado(true);
        vehiculo.setFechaEliminado(LocalDateTime.now());
        vehiculoRepository.save(vehiculo);
    }
}

