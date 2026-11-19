CREATE DATABASE ACI;
USE ACI;
CREATE TABLE eventos (
evento_id INT PRIMARY KEY,
nombre VARCHAR(50) NOT NULL,
sala_id INT NOT NULL,
fecha_inicio DATETIME NOT NULL,
fecha_final DATE,
categoria VARCHAR(10) NOT NULL,
descripcion TEXT
);

ALTER TABLE eventos 
MODIFY COLUMN nombre VARCHAR(100);

CREATE TABLE salas (
sala_id INT PRIMARY KEY,
nombre VARCHAR(100) NOT NULL,
web VARCHAR(255),
direccion VARCHAR(255)
);

CREATE TABLE usuario (
user_id INT PRIMARY KEY,
user_name VARCHAR(59) NOT NULL,
email VARCHAR(255) NOT NULL,
contrasenya_hash VARCHAR(255) NOT NULL
);
ALTER TABLE usuario 
MODIFY COLUMN user_name VARCHAR(50);

CREATE TABLE favoritos (
user_id INT REFERENCES usuario.user_id,
evento_id INT REFERENCES eventos.evento_id,
PRIMARY KEY (user_id, evento_id)
);

CREATE TABLE salas_favoritas (
user_id INT REFERENCES usuario.user_id,
sala_id INT REFERENCES salas.sala_id,
PRIMARY KEY (user_id, sala_id)
);

ALTER TABLE favoritos
ADD CONSTRAINT fk_favoritos
 FOREIGN KEY (user_id) REFERENCES usuario (user_id)
ON DELETE CASCADE;

ALTER TABLE favoritos
ADD CONSTRAINT fk_favoritos_evento
 FOREIGN KEY (evento_id) REFERENCES eventos (evento_id)
ON DELETE CASCADE;

ALTER TABLE salas_favoritas
ADD CONSTRAINT fk_salasfav_user
 FOREIGN KEY (user_id) REFERENCES usuario (user_id)
ON DELETE CASCADE;

ALTER TABLE salas_favoritas
ADD CONSTRAINT fk_salasfav_sala
 FOREIGN KEY (sala_id) REFERENCES salas (sala_id)
 ON DELETE RESTRICT;

ALTER TABLE eventos
ADD COLUMN hora TIME;

ALTER TABLE eventos
MODIFY COLUMN fecha_inicio DATE;

CREATE TABLE eventos_backup AS
SELECT * FROM eventos;

-- Se añade una restricción para que no se añadan eventos duplicados al volver a ejecutar los scripts de los scrapers
ALTER TABLE eventos
ADD CONSTRAINT unique_pelicula
UNIQUE (nombre, sala_id, fecha_inicio, hora, categoria),
ADD CONSTRAINT unique_exposicion
UNIQUE (nombre, sala_id, fecha_inicio, fecha_final, categoria);

-- Durante el desarrollo de la API REST y el diseño de la app decidí que en la pantalla Salas se debería poder filtrar también por categoría
ALTER TABLE salas
ADD categoria VARCHAR(50);

-- Añado las filas de las salas que tengo, más adelante haré web scrapers para que obtengan esta información automáticamente
INSERT INTO salas (sala_id, nombre, web, direccion)
VALUES
    (1, "Cines Embajadores", "https://cinesembajadores.es/madrid/", "Glorieta Sta. María de la Cabeza, 5, Arganzuela, 28045 Madrid"),
    (2, "Círculo de Bellas Artes", "https://www.circulobellasartes.com/cine-estudio/", "C. Alcalá, 42, Centro, 28014 Madrid, Spain"),
    (5, "Museo Thyssen-Bornemisza", "https://www.museothyssen.org/exposiciones", "P.º del Prado, 8, Centro, 28014 Madrid, Spain");
    
UPDATE salas
SET categoria = 'peliculas'
WHERE sala_id = 1;

UPDATE salas
SET categoria = 'peliculas'
WHERE sala_id = 2;

UPDATE salas
SET categoria = 'exposiciones'
WHERE sala_id = 5;

-- hago las columnas email y nombre en usuario UNIQUE para que no pueda haber duplicados
ALTER TABLE usuario
ADD CONSTRAINT unique_email
UNIQUE (email);

ALTER TABLE usuario
ADD CONSTRAINT unique_nombre
UNIQUE (user_name);
