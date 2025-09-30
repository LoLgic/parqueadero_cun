package com.example.parking.domain.exception.vehiculo;

import com.example.parking.domain.exception.BaseException;
import com.example.parking.domain.exception.ErrorCatalog;

public class VehiculoYaEliminadoException extends BaseException{

	private static final long serialVersionUID = 108002437880012169L;

	public VehiculoYaEliminadoException(String message) {
        super(ErrorCatalog.VEHICULO_YA_ELIMINADO, message);
    }
}
