package com.example.parking.domain.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCatalog {

	VALIDATION_ERROR("E001", "Error de validación", HttpStatus.BAD_REQUEST),
    USUARIO_NO_ENCONTRADO("E002", "Usuario no encontrado", HttpStatus.NOT_FOUND),
    VEHICULO_NO_ENCONTRADO("E003", "Vehículo no encontrado", HttpStatus.NOT_FOUND),
    ESPACIO_NO_ENCONTRADO("E004", "Espacio no encontrado", HttpStatus.NOT_FOUND),
    ESPACIO_OCUPADO("E005", "El espacio ya está ocupado", HttpStatus.CONFLICT),
    TICKET_NO_ACTIVO("E006", "El ticket no está activo", HttpStatus.BAD_REQUEST),
    TARIFA_NO_DEFINIDA("E007", "No existe tarifa para este tipo de vehículo", HttpStatus.NOT_FOUND),
    USUARIO_DUPLICADO("E008", "El usuario ya existe", HttpStatus.CONFLICT),
    CREDENCIALES_INVALIDAS("E009", "Credenciales inválidas o usuario inactivo", HttpStatus.UNAUTHORIZED),
    VEHICULO_DUPLICADO("E009", "Ya existe un vehículo con esa placa", HttpStatus.CONFLICT),
    ESPACIO_DUPLICADO("E010", "Ya existe un espacio con este código", HttpStatus.CONFLICT),
   
    TICKET_NO_ENCONTRADO("E008", "Ticket no encontrado", HttpStatus.NOT_FOUND),
    TIPO_VEHICULO_NO_COINCIDE("E010", "El tipo de vehículo no coincide con el espacio", HttpStatus.BAD_REQUEST),
    ACCESO_NO_PERMITIDO("E012", "Acceso denegado o no autorizado", HttpStatus.FORBIDDEN),
    
    VEHICULO_CON_TICKETS("E008", "El vehículo tiene tickets no finalizados y no puede eliminarse", HttpStatus.CONFLICT),
    VEHICULO_YA_ELIMINADO("E008", "El vehículo ya fue eliminado", HttpStatus.BAD_REQUEST),
    
    DATA_INTEGRITY_ERROR("E010", "Violación de integridad de datos", HttpStatus.CONFLICT),
    ACCESS_DENIED("E011", "Acceso denegado", HttpStatus.FORBIDDEN),
    RUNTIME_ERROR("E012", "Error de ejecución", HttpStatus.BAD_REQUEST),
    ERROR_INTERNO("E999", "Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR),
	
	VEHICULO_ELIMINADO("E015", "No se puede crear ticket ni usar un vehículo eliminado", HttpStatus.CONFLICT);
	
    

    private final String codigo;
    private final String mensaje;
    private final HttpStatus httpStatus;

    ErrorCatalog(String codigo, String mensaje, HttpStatus httpStatus) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.httpStatus = httpStatus;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
