package com.example.parking.domain.exception.ticket;

import com.example.parking.domain.exception.BaseException;
import com.example.parking.domain.exception.ErrorCatalog;

public class TipoVehiculoNoCoincideException extends BaseException {

	private static final long serialVersionUID = -951457653592390120L;

	public TipoVehiculoNoCoincideException(String detalle) {
        super(ErrorCatalog.TIPO_VEHICULO_NO_COINCIDE, detalle);
    }

}
