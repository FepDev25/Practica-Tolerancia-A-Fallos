CREATE TABLE estado_cuenta (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(20) NOT NULL UNIQUE
);

-- Datos iniciales
INSERT INTO estado_cuenta (nombre) VALUES ('ACTIVO'), ('INACTIVO'), ('BLOQUEADO');

CREATE TABLE tipo_transaccion (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(20) NOT NULL UNIQUE
);

-- Datos iniciales
INSERT INTO tipo_transaccion (nombre) VALUES ('DEPOSITO'), ('RETIRO'), ('TRANSFERENCIA');

CREATE TABLE usuario (
    id SERIAL PRIMARY KEY,
    correo VARCHAR(100) NOT NULL UNIQUE,
    contrasena VARCHAR(100) NOT NULL
);

CREATE TABLE cuenta (
    id SERIAL PRIMARY KEY,
    usuario_id INT NOT NULL,
    saldo NUMERIC(12, 2) NOT NULL DEFAULT 0.00,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (id) ON DELETE CASCADE
);

CREATE TABLE transaccion (
    id SERIAL PRIMARY KEY,
    cuenta_origen_id BIGINT,
    cuenta_destino_id BIGINT,
    monto NUMERIC(12, 2) NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_cuenta_origen FOREIGN KEY (cuenta_origen_id) REFERENCES cuenta (id) ON DELETE SET NULL,
    CONSTRAINT fk_cuenta_destino FOREIGN KEY (cuenta_destino_id) REFERENCES cuenta (id) ON DELETE SET NULL
);

