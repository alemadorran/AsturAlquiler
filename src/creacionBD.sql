-- Crea la base de datos llamada "AsturAlquiler"
CREATE DATABASE IF NOT EXISTS asturalquiler;

-- Usa la base de datos "asturalquiler"
USE asturalquiler;

--
-- Table structure for table ciudad
--

DROP TABLE IF EXISTS ciudad;

CREATE TABLE ciudad (
  codigo varchar(10) NOT NULL,    
  nombre varchar(45) NOT NULL,
  PRIMARY KEY (codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `concesionario`
--

DROP TABLE IF EXISTS concesionario;

CREATE TABLE concesionario (
  codigo_concesionario varchar(10) NOT NULL,    
  nombre varchar(45) NOT NULL,
  codigo_ciudad varchar(10) NOT NULL,  
  PRIMARY KEY (codigo_concesionario),
  FOREIGN KEY (codigo_ciudad) REFERENCES ciudad(codigo)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table coche
--

DROP TABLE IF EXISTS coche;

CREATE TABLE coche (
  matricula varchar(10) NOT NULL,    
  marca varchar(45) DEFAULT NULL,
  modelo varchar(45) DEFAULT NULL,
  codigo_concesionario varchar(10) NOT NULL,  
  PRIMARY KEY (matricula),
  FOREIGN KEY (codigo_concesionario) REFERENCES concesionario(codigo_concesionario)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

