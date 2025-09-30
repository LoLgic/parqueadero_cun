package com.example.parking.domain.exception.vehiculo;

import com.example.parking.domain.exception.BaseException;
import com.example.parking.domain.exception.ErrorCatalog;

public class VehiculoDuplicadoException extends BaseException {

	private static final long serialVersionUID = -8139456503205504391L;

	public VehiculoDuplicadoException(String detalle) {
        super(ErrorCatalog.VEHICULO_DUPLICADO, detalle);
    }

}
