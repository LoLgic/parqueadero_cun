package com.example.parking.domain.exception.usuario;

import com.example.parking.domain.exception.BaseException;
import com.example.parking.domain.exception.ErrorCatalog;

public class UsuarioNoEncontradoException extends BaseException {
    
	private static final long serialVersionUID = -1069513874861226274L;

	public UsuarioNoEncontradoException() {
        super(ErrorCatalog.USUARIO_NO_ENCONTRADO);
    }

    public UsuarioNoEncontradoException(String detalle) {
        super(ErrorCatalog.USUARIO_NO_ENCONTRADO, detalle);
    }

}
