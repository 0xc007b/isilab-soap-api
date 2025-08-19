-- Setup script for Simple SOAP API Database
-- Run this script in PostgreSQL to create the database

-- Create the database (run this as postgres user)
CREATE DATABASE simple_soap_api;

-- Connect to the database
\c simple_soap_api;

-- Create the tables (these will be auto-created by Hibernate, but here for reference)
-- The application uses spring.jpa.hibernate.ddl-auto=update so these are optional

-- Sectors table
CREATE TABLE IF NOT EXISTS sectors (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(120) NOT NULL UNIQUE
);

-- Classes table
CREATE TABLE IF NOT EXISTS classes (
    id BIGSERIAL PRIMARY KEY,
    class_name VARCHAR(120) NOT NULL,
    description VARCHAR(255),
    sector_id BIGINT NOT NULL,
    CONSTRAINT fk_class_sector FOREIGN KEY (sector_id) REFERENCES sectors(id) ON DELETE CASCADE,
    CONSTRAINT uk_class_name_sector UNIQUE (class_name, sector_id)
);

-- Insert sample data (optional)
INSERT INTO sectors (name) VALUES
    ('Informatique'),
    ('Génie Civil'),
    ('Électronique')
ON CONFLICT (name) DO NOTHING;

INSERT INTO classes (class_name, description, sector_id) VALUES
    ('L3 GL', 'Licence 3 Génie Logiciel', 1),
    ('M1 GL', 'Master 1 Génie Logiciel', 1),
    ('M2 GL', 'Master 2 Génie Logiciel', 1),
    ('L3 GC', 'Licence 3 Génie Civil', 2),
    ('M1 GC', 'Master 1 Génie Civil', 2)
ON CONFLICT (class_name, sector_id) DO NOTHING;

-- Display created data
SELECT 'Sectors created:' as info;
SELECT id, name FROM sectors ORDER BY id;

SELECT 'Classes created:' as info;
SELECT c.id, c.class_name, c.description, s.name as sector_name
FROM classes c
JOIN sectors s ON c.sector_id = s.id
ORDER BY c.id;
