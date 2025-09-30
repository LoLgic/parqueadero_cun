package com.example.parking.domain.exception.espacio;

import com.example.parking.domain.exception.BaseException;
import com.example.parking.domain.exception.ErrorCatalog;

public class EspacioNoEncontradoException extends BaseException {

	private static final long serialVersionUID = 3703952183373679020L;

	public EspacioNoEncontradoException(String detalle) {
        super(ErrorCatalog.ESPACIO_NO_ENCONTRADO, detalle);
    }

}
