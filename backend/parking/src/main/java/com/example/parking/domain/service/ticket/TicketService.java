package com.example.parking.domain.service.ticket;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.parking.domain.exception.espacio.EspacioNoEncontradoException;
import com.example.parking.domain.exception.espacio.EspacioOcupadoException;
import com.example.parking.domain.exception.ticket.AccesoNoPermitidoException;
import com.example.parking.domain.exception.ticket.TarifaNoDefinidaException;
import com.example.parking.domain.exception.ticket.TicketNoActivoException;
import com.example.parking.domain.exception.ticket.TicketNoEncontradoException;
import com.example.parking.domain.exception.ticket.TipoVehiculoNoCoincideException;
import com.example.parking.domain.exception.usuario.UsuarioNoEncontradoException;
import com.example.parking.domain.exception.vehiculo.VehiculoEliminadoException;
import com.example.parking.domain.exception.vehiculo.VehiculoNoEncontradoException;
import com.example.parking.domain.model.espacio.Espacio;
import com.example.parking.domain.model.notificacion.Notificacion;
import com.example.parking.domain.model.pago.Pago;
import com.example.parking.domain.model.tarifa.Tarifa;
import com.example.parking.domain.model.ticket.EstadoTicket;
import com.example.parking.domain.model.ticket.Ticket;
import com.example.parking.domain.model.usuario.Rol;
import com.example.parking.domain.model.usuario.Usuario;
import com.example.parking.domain.model.vehiculo.Vehiculo;
import com.example.parking.domain.repo.espacio.EspacioRepository;
import com.example.parking.domain.repo.notificacion.NotificacionRepository;
import com.example.parking.domain.repo.pago.PagoRepository;
import com.example.parking.domain.repo.tarifa.TarifaRepository;
import com.example.parking.domain.repo.ticket.TicketRepository;
import com.example.parking.domain.repo.usuario.UsuarioRepository;
import com.example.parking.domain.repo.vehiculo.VehiculoRepository;
import com.example.parking.web.dto.ticket.AdminTicketResponse;
import com.example.parking.web.dto.ticket.TicketCreateRequest;
import com.example.parking.web.dto.ticket.TicketResponse;
import com.example.parking.web.mapper.ticket.TicketMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketService {

	private final TicketRepository ticketRepository;
	private final UsuarioRepository usuarioRepository;
	private final VehiculoRepository vehiculoRepository;
	private final EspacioRepository espacioRepository;
	private final TarifaRepository tarifaRepository;
	private final PagoRepository pagoRepository;
	private final NotificacionRepository notificacionRepository;
	
	@Transactional
	public TicketResponse crear(TicketCreateRequest  req) {
		Usuario usuario = getCurrentUserOrThrow();
		
		Vehiculo vehiculo = vehiculoRepository.findById(req.idVehiculo())
                .orElseThrow(() -> new VehiculoNoEncontradoException(
                		"Vehículo no encontrado con id: " + req.idVehiculo()));
		
		if (vehiculo.isEliminado()) {
			throw new VehiculoEliminadoException("No se puede crear ticket para un vehículo eliminado placa: " 
		+ vehiculo.getPlaca());
		}
		
		// Si es CLIENTE, solo puede crear tickets para SUS vehículos
        if (usuario.getRol() == Rol.CLIENTE && (vehiculo.getUsuario() 
        		== null || !vehiculo.getUsuario().getIdUsuario().equals(usuario.getIdUsuario()))) {
            throw new AccesoNoPermitidoException("No puedes crear ticket para un vehículo que no es tuyo");
        }
        
        Espacio espacio = espacioRepository.findById(req.idEspacio())
                .orElseThrow(() -> new EspacioNoEncontradoException(
                		"Espacio no encontrado con id: " + req.idEspacio()));
        
        //  Validar que el tipo de vehículo coincida con el tipo de espacio
        if (!vehiculo.getTipo().equals(espacio.getTipo())) {
            throw new TipoVehiculoNoCoincideException("Vehículo tipo " + vehiculo.getTipo() +
                    " no coincide con espacio tipo " + espacio.getTipo());
        }
        
        
        // Validar que el espacio no esté ocupado (doble validación: flag + tickets activos)
        boolean ocupadoPorFlag = Boolean.TRUE.equals(espacio.isOcupado());
        boolean ocupadoPorTicket = ticketRepository.existsByEspacio_IdEspacioAndEstadoIn(
                espacio.getIdEspacio(), List.of(EstadoTicket.ACTIVO));
        
        if (ocupadoPorFlag || ocupadoPorTicket) {
            throw new EspacioOcupadoException(
            		"El espacio ya está ocupado (id: " + espacio.getIdEspacio() + ")");
        }
        
        // Crear ticket
        Ticket ticket = Ticket.builder()
                .usuario(usuario)  // quien genera la reserva
                .vehiculo(vehiculo)
                .espacio(espacio)
                .fechaEntrada(LocalDateTime.now())
                .estado(EstadoTicket.ACTIVO)
                .build();
        
        // Marcar espacio ocupado
        espacio.setOcupado(true);
        espacioRepository.save(espacio);
        
        Ticket saved = ticketRepository.save(ticket);
        
        // Inicializar perezosos para respuesta (si usas LAZY)
        saved.getUsuario().getEmail();
        saved.getVehiculo().getPlaca();
        saved.getEspacio().getCodigo();
        
        return TicketMapper.toResponse(saved, obtenerMonto(saved.getIdTicket())); 
	}
	
	
	@Transactional(readOnly = true)
    public List<TicketResponse> listarMios() {
        Usuario usuario = getCurrentUserOrThrow();
        return ticketRepository.findByUsuario_IdUsuario(usuario.getIdUsuario())
                .stream().map(t -> TicketMapper.toResponse(t, obtenerMonto(t.getIdTicket()))).toList();
    }
	
	@Transactional(readOnly = true)
    public List<TicketResponse> listarTodos() {
        // Se filtra por rol en el controlador con @PreAuthorize
        return ticketRepository.findAll()
        		.stream()
        		.map(t -> TicketMapper.toResponse(t, obtenerMonto(t.getIdTicket()))).toList();
    }
	
 	@Transactional
    public TicketResponse finalizar(Long idTicket) {
 		Ticket ticket = ticketRepository.findById(idTicket)
                .orElseThrow(() -> new TicketNoEncontradoException(
                		"Ticket no encontrado con id: " + idTicket));
 		
 		if (ticket.getEstado() != EstadoTicket.ACTIVO) {
            throw new TicketNoActivoException("El ticket con id " + idTicket + " no está activo");
        }
 		
 		ticket.setFechaSalida(LocalDateTime.now());
        ticket.setEstado(EstadoTicket.FINALIZADO);
        
        // Liberar espacio
        Espacio espacio = ticket.getEspacio();
        espacio.setOcupado(false);
        espacioRepository.save(espacio);
        
     // ====== NUEVO: calcular tarifa ======
        Vehiculo vehiculo = ticket.getVehiculo();
        
        //verificar cual es la correcta opcion.
        Tarifa tarifa = tarifaRepository.findByTipoVehiculo(vehiculo.getTipo())
                .orElseThrow(() -> new TarifaNoDefinidaException("No existe tarifa para vehículo tipo " + vehiculo.getTipo()));
        
        long horas = Duration.between(ticket.getFechaEntrada(), ticket.getFechaSalida()).toHours();
        
        if (horas == 0) horas = 1; // mínimo 1 hora
        BigDecimal monto = tarifa.getValorHora()
        		.multiply(BigDecimal.valueOf(horas));  //ACA PUEDE ESTAR EL ERROR, FALTA LA TARIFA $
        
     // Crear pago
        Pago pago = Pago.builder()
                .ticket(ticket)
                .monto(monto)
                .fechaPago(LocalDateTime.now())
                .build();
        pagoRepository.save(pago);
        
        
     // Crear notificación al cliente
        Notificacion notif = Notificacion.builder()
                .usuario(ticket.getUsuario())
                .mensaje("Tu ticket #" + ticket.getIdTicket() + " fue finalizado. Monto a pagar: $" + monto)
                .fecha(LocalDateTime.now())
                .build();
        notificacionRepository.save(notif);
        
        Ticket saved = ticketRepository.save(ticket);
        saved.getUsuario().getEmail();
        saved.getVehiculo().getPlaca();
        saved.getEspacio().getCodigo();
        
        return TicketMapper.toResponse(saved, Optional.of(monto));
 	}
 	
 	 @Transactional(readOnly = true)
     public AdminTicketResponse obtenerTicketPorId(Long idTicket) {
 		Ticket t = ticketRepository.findById(idTicket)
                .orElseThrow(() -> new TicketNoEncontradoException("Ticket no encontrado con id: " + idTicket));
 		
 		t.getUsuario().getEmail();
        t.getVehiculo().getPlaca();
        t.getEspacio().getCodigo();
        return TicketMapper.toResponseAdmin(t, obtenerMonto(t.getIdTicket()));
 	 }
 	 
 	private Usuario getCurrentUserOrThrow() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		
		return usuarioRepository.findByEmail(email)
				.orElseThrow(() -> new UsuarioNoEncontradoException(
						"Usuario autenticado no encontrado con email: " + email));
	}
	
	private Optional<BigDecimal> obtenerMonto(Long idTicket) {
        return pagoRepository.findAll().stream()
                .filter(p -> p.getTicket().getIdTicket().equals(idTicket))
                .map(Pago::getMonto)
                .findFirst();
    }
}
