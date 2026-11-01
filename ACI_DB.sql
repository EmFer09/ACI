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

