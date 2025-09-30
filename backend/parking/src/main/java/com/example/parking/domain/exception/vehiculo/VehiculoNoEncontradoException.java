package com.example.parking.domain.exception.vehiculo;

import com.example.parking.domain.exception.BaseException;
import com.example.parking.domain.exception.ErrorCatalog;

public class VehiculoNoEncontradoException extends BaseException {
    
	private static final long serialVersionUID = -5677389914616380389L;

	public VehiculoNoEncontradoException() {
        super(ErrorCatalog.VEHICULO_NO_ENCONTRADO);
    }

    public VehiculoNoEncontradoException(String detalle) {
        super(ErrorCatalog.VEHICULO_NO_ENCONTRADO, detalle);
    }

}
