-- Script de migración para cambiar la clave primaria de Huesped
-- de numero_documento a huesped_id

-- Paso 1: Agregar la columna huesped_id si no existe
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'huesped' AND column_name = 'huesped_id'
    ) THEN
        ALTER TABLE huesped ADD COLUMN huesped_id BIGINT;
    END IF;
END $$;

-- Paso 1.5: Crear secuencia si no existe
CREATE SEQUENCE IF NOT EXISTS huesped_huesped_id_seq;

-- Paso 2: Poblar huesped_id con valores secuenciales para registros existentes
DO $$
DECLARE
    max_id BIGINT;
BEGIN
    -- Obtener el máximo ID actual o empezar desde 1
    SELECT COALESCE(MAX(huesped_id), 0) INTO max_id FROM huesped;
    
    -- Si hay registros sin ID, asignarles valores secuenciales
    IF EXISTS (SELECT 1 FROM huesped WHERE huesped_id IS NULL) THEN
        -- Establecer el valor de la secuencia al máximo + 1
        PERFORM setval('huesped_huesped_id_seq', GREATEST(max_id, (SELECT COUNT(*) FROM huesped)));
        
        -- Actualizar registros sin ID
        UPDATE huesped 
        SET huesped_id = nextval('huesped_huesped_id_seq')
        WHERE huesped_id IS NULL;
    END IF;
END $$;

-- Paso 2.5: Asociar la secuencia con la columna para auto-incremento
ALTER TABLE huesped 
    ALTER COLUMN huesped_id SET DEFAULT nextval('huesped_huesped_id_seq');

-- Paso 3: Actualizar la tabla estadia_acompaniantes para usar huesped_id
-- Primero, agregar la columna acompaniante_id si no existe
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'estadia_acompaniantes' AND column_name = 'acompaniante_id'
    ) THEN
        ALTER TABLE estadia_acompaniantes ADD COLUMN acompaniante_id BIGINT;
        
        -- Migrar datos: buscar el huesped_id correspondiente a cada acompaniante_documento
        UPDATE estadia_acompaniantes ea
        SET acompaniante_id = h.huesped_id
        FROM huesped h
        WHERE ea.acompaniante_documento = h.numero_documento;
    END IF;
END $$;

-- Paso 4: Actualizar la tabla estadia para usar titular_id
-- Primero, agregar la columna titular_id si no existe
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'estadia' AND column_name = 'titular_id'
    ) THEN
        ALTER TABLE estadia ADD COLUMN titular_id BIGINT;
        
        -- Migrar datos: buscar el huesped_id correspondiente a cada titular_documento
        UPDATE estadia e
        SET titular_id = h.huesped_id
        FROM huesped h
        WHERE e.titular_documento = h.numero_documento;
    END IF;
END $$;

-- Paso 5: Eliminar las restricciones de clave foránea antiguas si existen
DO $$
BEGIN
    -- Eliminar FK de estadia.titular_documento si existe
    IF EXISTS (
        SELECT 1 FROM information_schema.table_constraints 
        WHERE table_name = 'estadia' 
        AND constraint_name LIKE '%titular%'
        AND constraint_type = 'FOREIGN KEY'
    ) THEN
        ALTER TABLE estadia DROP CONSTRAINT IF EXISTS estadia_titular_documento_fkey;
    END IF;
    
    -- Eliminar FK de estadia_acompaniantes.acompaniante_documento si existe
    IF EXISTS (
        SELECT 1 FROM information_schema.table_constraints 
        WHERE table_name = 'estadia_acompaniantes' 
        AND constraint_name LIKE '%acompaniante_documento%'
        AND constraint_type = 'FOREIGN KEY'
    ) THEN
        ALTER TABLE estadia_acompaniantes DROP CONSTRAINT IF EXISTS estadia_acompaniantes_acompaniante_documento_fkey;
    END IF;
END $$;

-- Paso 6: Eliminar la clave primaria antigua de numero_documento
ALTER TABLE huesped DROP CONSTRAINT IF EXISTS huesped_pkey;

-- Paso 7: Establecer huesped_id como NOT NULL y clave primaria
ALTER TABLE huesped 
    ALTER COLUMN huesped_id SET NOT NULL,
    ADD PRIMARY KEY (huesped_id);

-- Paso 8: Eliminar columnas antiguas de estadia y estadia_acompaniantes
ALTER TABLE estadia DROP COLUMN IF EXISTS titular_documento;
ALTER TABLE estadia_acompaniantes DROP COLUMN IF EXISTS acompaniante_documento;

-- Paso 9: Crear nuevas claves foráneas usando huesped_id
ALTER TABLE estadia 
    ADD CONSTRAINT estadia_titular_id_fkey 
    FOREIGN KEY (titular_id) REFERENCES huesped(huesped_id);

ALTER TABLE estadia_acompaniantes 
    ADD CONSTRAINT estadia_acompaniantes_acompaniante_id_fkey 
    FOREIGN KEY (acompaniante_id) REFERENCES huesped(huesped_id);

-- Paso 10: Hacer que numero_documento ya no sea único (si tiene restricción unique)
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.table_constraints 
        WHERE table_name = 'huesped' 
        AND constraint_name LIKE '%numero_documento%'
        AND constraint_type = 'UNIQUE'
    ) THEN
        ALTER TABLE huesped DROP CONSTRAINT IF EXISTS huesped_numero_documento_key;
    END IF;
END $$;

