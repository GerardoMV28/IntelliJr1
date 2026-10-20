-- ====================================================================
-- Script de Creación de Base de Datos y Tabla para API de Usuarios
-- Asignatura: Introducción a Aplicaciones Web Progresivas (PWA)
-- Cuatrimestre: Septiembre – Diciembre 2025
-- ====================================================================

-- 1. Crear base de datos (Ejecutar conectado como postgres)
-- CREATE DATABASE db_usuarios;

-- 2. Conectarse a la base de datos db_usuarios antes de ejecutar lo siguiente:
-- \c db_usuarios;

-- 3. Eliminar tabla si ya existe (para reinicios limpios de pruebas)
DROP TABLE IF EXISTS usuarios CASCADE;

-- 4. Crear tabla usuarios con restricciones de integridad
CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(150) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_usuarios_correo UNIQUE (correo)
);

-- 5. Índice para optimizar búsquedas por llave compuesta (nombre + correo)
CREATE INDEX idx_usuarios_nombre_correo ON usuarios (nombre, correo);

-- 6. Inserción de datos iniciales para pruebas (opcional)
INSERT INTO usuarios (nombre, correo, telefono, activo, fecha_creacion)
VALUES 
    ('Juan Pérez', 'juan.perez@correo.com', '4771234567', TRUE, '2026-10-14 10:30:00'),
    ('María López', 'maria.lopez@correo.com', '4779876543', TRUE, '2026-10-15 11:00:00'),
    ('Carlos García', 'carlos.garcia@correo.com', '4775551234', FALSE, '2026-10-16 12:15:00');
