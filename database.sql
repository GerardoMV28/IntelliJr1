-- ====================================================================
-- SCRIPT DE CREACIÓN DE BASE DE DATOS Y TABLA POSTGRESQL
-- PROYECTO: API REST PARA LA ADMINISTRACIÓN DE USUARIOS
-- ====================================================================

-- Paso 1: Crear base de datos
CREATE DATABASE db_usuarios
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Spanish_Mexico.1252'
    LC_CTYPE = 'Spanish_Mexico.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

\c db_usuarios;

-- Paso 2: Crear tabla de Usuarios
DROP TABLE IF EXISTS usuarios CASCADE;

CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(150) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_usuarios_correo UNIQUE (correo)
);

-- Paso 3: Índice compuesto para optimizar búsquedas por nombre y correo (Llave de búsqueda)
CREATE INDEX idx_usuarios_nombre_correo ON usuarios (nombre, correo);

-- Paso 4: Registros de prueba
INSERT INTO usuarios (nombre, correo, telefono, activo, fecha_creacion)
VALUES 
    ('Juan Pérez', 'juan.perez@correo.com', '4771234567', TRUE, '2026-10-14 10:30:00'),
    ('María López', 'maria.lopez@correo.com', '4779876543', TRUE, '2026-10-15 11:00:00'),
    ('Carlos García', 'carlos.garcia@correo.com', '4775551234', FALSE, '2026-10-16 12:15:00');
