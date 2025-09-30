-- ======================
-- V2__insert_data.sql
-- ======================

-- Insertar usuario ADMIN por defecto
INSERT INTO usuarios (nombre, apellido, celular, email, password_hash, rol, activo, fecha_registro)
VALUES ('Admin', 'Super', '3002563645', 'admin@super.com',
        -- Clave encriptada con BCrypt para "admin123"
        '$2a$10$984Njm8wWmIzPfArXAu8w.8mnU4Da6V3/mSClX05hKqBzhy.QGHfa',
        'ADMIN', TRUE, NOW());


-- Insertar tarifas base
INSERT INTO tarifas (tipo_vehiculo, valor_hora) VALUES ('CARRO', 3000.00);
INSERT INTO tarifas (tipo_vehiculo, valor_hora) VALUES ('MOTO', 1500.00);
INSERT INTO tarifas (tipo_vehiculo, valor_hora) VALUES ('BICICLETA', 500.00);
