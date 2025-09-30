package com.example.parking.domain.service.espacio;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.parking.domain.exception.espacio.EspacioDuplicadoException;
import com.example.parking.domain.exception.espacio.EspacioNoEncontradoException;
import com.example.parking.domain.model.espacio.Espacio;
import com.example.parking.domain.model.vehiculo.TipoVehiculo;
import com.example.parking.domain.repo.espacio.EspacioRepository;
import com.example.parking.web.dto.espacio.EspacioRequest;
import com.example.parking.web.dto.espacio.EspacioResponse;
import com.example.parking.web.mapper.espacio.EspacioMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EspacioService {

	private final EspacioRepository espacioRepository;
	
	// ADMIN: crear nuevo espacio
	@Transactional
	public EspacioResponse crear(EspacioRequest req) {
		
		if (espacioRepository.existsByCodigo(req.codigo())) {
            throw new EspacioDuplicadoException("El código de espacio ya existe: " + req.codigo());
		}
		Espacio espacio = EspacioMapper.toEntity(req);
		Espacio guardado = espacioRepository.save(espacio);
		
		return EspacioMapper.toResponse(guardado);
	}
	
	// ADMIN/OPERADOR: listar todos los espacios
	@Transactional(readOnly = true)
	public List<EspacioResponse> listarTodos() {
		return espacioRepository.findAll()
				.stream()
				.map(EspacioMapper::toResponse)
				.toList();
	}
	
	// Buscar espacios libres por tipo (para asignar tickets)
	@Transactional(readOnly = true)
	public List<EspacioResponse> listarLibresPorTipo(TipoVehiculo  tipo){
		return espacioRepository.findByTipoAndOcupadoFalse(tipo)
				.stream()
				.map(EspacioMapper::toResponse)
				.toList();
	}
	
	// ADMIN/OPERADOR: actualizar estado ocupado/libre
	@Transactional(readOnly = true)
	public EspacioResponse actualizarEstado(Long idEspacio, boolean ocupado) {
		Espacio espacio = espacioRepository.findById(idEspacio)
				.orElseThrow(() -> new EspacioNoEncontradoException("No se encontró espacio con id: " + idEspacio));
		
		espacio.setOcupado(ocupado);
		Espacio actualizado = espacioRepository.save(espacio);
		
		return EspacioMapper.toResponse(actualizado);	
	}	
}
