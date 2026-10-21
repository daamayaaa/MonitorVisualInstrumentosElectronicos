CREATE DATABASE IF NOT EXISTS monitor_visual_instrumentos;

CREATE USER 'monitor'@'localhost' IDENTIFIED BY '12345678';

GRANT ALL PRIVILEGES ON monitor_visual_instrumentos.* TO 'monitor'@'localhost';

FLUSH PRIVILEGES;

CREATE TABLE datos_balanza	(
	id_datos_balanza		BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
	fecha_hora_registro 	VARCHAR(45), 
	peso_balanza			DECIMAL(5,2)
);


CREATE TABLE datos_EW32907	(
	id_datos_EW32907		BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
	fecha_hora_registro, 	VARCHAR(19),
	presion_Absoluta_PSIA	DECIMAL(10,8), 
	temperatura				DECIMAL(5,2), 
	volumen_flujo			DECIMAL(10,8), 
	flujo_masa				DECIMAL(10,8), 
	valor_puesta_punto		DECIMAL(10,8)
);


CREATE TABLE datos_hyt		(
	id_datos_hyt			BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
	fecha_hora_registro		VARCHAR(19), 
	temperatura_sensor_1	DECIMAL(5,2), 
	temperatura_sensor_2	DECIMAL(5,2),
	humedad_sensor_3		DECIMAL(5,2) 
);