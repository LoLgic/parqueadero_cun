package com.example.parking.domain.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

	private static final long serialVersionUID = -4975027816571780073L;

	private final ErrorCatalog errorCatalog;

    public BaseException(ErrorCatalog errorCatalog) {
        super(errorCatalog.getMensaje());
        this.errorCatalog = errorCatalog;
    }

    public BaseException(ErrorCatalog errorCatalog, String detalle) {
        super(detalle);
        this.errorCatalog = errorCatalog;
    }

}
