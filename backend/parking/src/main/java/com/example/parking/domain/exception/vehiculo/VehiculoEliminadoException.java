package com.example.parking.domain.exception.vehiculo;

import com.example.parking.domain.exception.BaseException;
import com.example.parking.domain.exception.ErrorCatalog;

public class VehiculoEliminadoException extends BaseException{

	private static final long serialVersionUID = 8640450894693670370L;
	
	public VehiculoEliminadoException () {
		super(ErrorCatalog.VEHICULO_ELIMINADO);
	}
	
	public VehiculoEliminadoException (String detalle) {
		super(ErrorCatalog.VEHICULO_ELIMINADO, detalle);
	}
}
