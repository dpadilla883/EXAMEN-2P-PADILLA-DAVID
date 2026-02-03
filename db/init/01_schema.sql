-- PostgreSQL
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE usuarios (
  id UUID PRIMARY KEY,
  nombre VARCHAR(120) NOT NULL,
  email VARCHAR(160) NOT NULL UNIQUE,
  capital_disponible NUMERIC(10,2) NOT NULL
);

CREATE TABLE productos_financieros (
  id UUID PRIMARY KEY,
  nombre VARCHAR(140) NOT NULL,
  descripcion TEXT NOT NULL,
  costo NUMERIC(10,2) NOT NULL,
  porcentaje_retorno NUMERIC(5,2) NOT NULL,
  activo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE simulaciones (
  id UUID PRIMARY KEY,
  usuario_id UUID NOT NULL REFERENCES usuarios(id),
  fecha_simulacion TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  capital_disponible NUMERIC(10,2) NOT NULL,
  costo_total NUMERIC(10,2) NOT NULL,
  capital_restante NUMERIC(10,2) NOT NULL,
  ganancia_total NUMERIC(10,2) NOT NULL,
  retorno_total_porcentaje NUMERIC(7,2) NOT NULL,
  riesgo_promedio NUMERIC(7,2) NOT NULL
);

-- Guardamos candidatos evaluados y seleccionados (auditoría y trazabilidad)
CREATE TABLE simulacion_detalle (
  id UUID PRIMARY KEY,
  simulacion_id UUID NOT NULL REFERENCES simulaciones(id) ON DELETE CASCADE,
  producto_nombre VARCHAR(140) NOT NULL,
  precio NUMERIC(10,2) NOT NULL,
  riesgo NUMERIC(7,2) NOT NULL,
  porcentaje_ganancia NUMERIC(7,2) NOT NULL,
  ganancia_esperada NUMERIC(10,2) NOT NULL,
  seleccionado BOOLEAN NOT NULL
);
