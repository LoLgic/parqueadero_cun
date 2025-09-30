
-- ======================
-- TABLA: usuarios	
-- ======================
CREATE TABLE usuarios (
    id_usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    celular VARCHAR(11) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    rol VARCHAR(20) NOT NULL, -- CLIENTE, ADMIN, OPERADOR (Enum en Java)
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_registro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- ======================
-- TABLA: vehiculos
-- ======================
CREATE TABLE vehiculos (
    id_vehiculo BIGINT AUTO_INCREMENT PRIMARY KEY,
    placa VARCHAR(15) NOT NULL,
    tipo VARCHAR(20) NOT NULL, -- Enum en Java: CARRO, MOTO, CAMION, BICICLETA
    id_usuario BIGINT NOT NULL,
    eliminado BOOLEAN NOT NULL DEFAULT FALSE,
    fecha_eliminado TIMESTAMP NULL,
    CONSTRAINT UK_vehiculo_placa UNIQUE (placa),
    CONSTRAINT FK_vehiculo_usuario FOREIGN KEY (id_usuario)
        REFERENCES usuarios(id_usuario)
);

-- ======================
-- TABLA: espacios
-- ======================
CREATE TABLE espacios (
    id_espacio BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(10) NOT NULL UNIQUE, -- Ejemplo: A1, B2
    tipo VARCHAR(20) NOT NULL, -- Enum en Java: CARRO, MOTO, CAMIONETA
    ocupado BOOLEAN NOT NULL DEFAULT FALSE,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- ======================
-- TABLA: tarifas
-- ======================
CREATE TABLE tarifas (
    id_tarifa BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo_vehiculo VARCHAR(20) NOT NULL UNIQUE, -- Enum en Java
    valor_hora DECIMAL(10,2) NOT NULL
);

-- ======================
-- TABLA: tickets
-- ======================
CREATE TABLE tickets (
    id_ticket BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_usuario BIGINT NOT NULL,
    id_vehiculo BIGINT NOT NULL,
    id_espacio BIGINT NOT NULL,
    estado VARCHAR(20) NOT NULL, -- Enum en Java: ACTIVO, FINALIZADO, CANCELADO
    fecha_entrada TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_salida TIMESTAMP NULL,
    CONSTRAINT FK_ticket_usuario FOREIGN KEY (id_usuario)
        REFERENCES usuarios(id_usuario),
    CONSTRAINT FK_ticket_vehiculo FOREIGN KEY (id_vehiculo)
        REFERENCES vehiculos(id_vehiculo),
    CONSTRAINT FK_ticket_espacio FOREIGN KEY (id_espacio)
        REFERENCES espacios(id_espacio)
);

-- ======================
-- TABLA: pagos
-- ======================
CREATE TABLE pagos (
    id_pago BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_ticket BIGINT NOT NULL UNIQUE,
    monto DECIMAL(10,2) NOT NULL,
    fecha_pago TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT FK_pago_ticket FOREIGN KEY (id_ticket)
        REFERENCES tickets(id_ticket)
);

-- ======================
-- TABLA: notificaciones
-- ======================
CREATE TABLE notificaciones (
    id_notificacion BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_usuario BIGINT NOT NULL,
    mensaje VARCHAR(255) NOT NULL,
    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT FK_notificacion_usuario FOREIGN KEY (id_usuario)
        REFERENCES usuarios(id_usuario)
);
