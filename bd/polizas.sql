-- =====================================================================
-- Base de datos del Gestor de Pólizas (Equipo 5)
-- Versión 2: basada en el nuevo diagrama entidad-relación.
-- Único cambio respecto al diagrama: se agregó la columna "activo" a
-- cliente y vehiculo para darlos de baja sin borrar su historial (RN-14).
-- =====================================================================

DROP DATABASE IF EXISTS polizas;
CREATE DATABASE polizas CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE polizas;

-- ---------------------------------------------------------------------
-- Catálogos
-- ---------------------------------------------------------------------

CREATE TABLE marca (
    idMarca INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE
) ENGINE=InnoDB;

CREATE TABLE modelo (
    idModelo INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    idMarca INT NOT NULL,
    UNIQUE (idMarca, nombre),
    FOREIGN KEY (idMarca) REFERENCES marca(idMarca) ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE usovehiculo (
    idUso INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
) ENGINE=InnoDB;

CREATE TABLE aseguradora (
    idAseguradora INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    rfc VARCHAR(13) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    correo VARCHAR(100),
    nombreContacto VARCHAR(150)
) ENGINE=InnoDB;

CREATE TABLE formapago (
    idFormaPago INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    numeroPagos INT NOT NULL CHECK (numeroPagos >= 1)
) ENGINE=InnoDB;

CREATE TABLE cobertura (
    idCobertura INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion TEXT
) ENGINE=InnoDB;

CREATE TABLE metodopago (
    idMetodoPago INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
) ENGINE=InnoDB;

CREATE TABLE rol (
    idRol INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
) ENGINE=InnoDB;

CREATE TABLE tiposolicitud (
    idTipoSolicitud INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
) ENGINE=InnoDB;

-- ---------------------------------------------------------------------
-- Personas
-- ---------------------------------------------------------------------

CREATE TABLE cliente (
    idCliente INT AUTO_INCREMENT PRIMARY KEY,
    tipoPersona VARCHAR(10) NOT NULL CHECK (tipoPersona IN ('FISICA', 'MORAL')),
    nombre VARCHAR(150) NOT NULL,
    apellidoPaterno VARCHAR(80),
    apellidoMaterno VARCHAR(80),
    rfc VARCHAR(13) NOT NULL UNIQUE,
    curp VARCHAR(18) UNIQUE,
    telefono VARCHAR(20) NOT NULL,
    correo VARCHAR(100),
    fechaRegistro DATE NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE
) ENGINE=InnoDB;

CREATE TABLE usuario (
    idUsuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL,
    apellidoPaterno VARCHAR(80) NOT NULL,
    apellidoMaterno VARCHAR(80),
    nombreUsuario VARCHAR(50) NOT NULL UNIQUE,
    contrasenaHash VARCHAR(64) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    idRol INT NOT NULL,
    FOREIGN KEY (idRol) REFERENCES rol(idRol) ON UPDATE CASCADE
) ENGINE=InnoDB;

-- ---------------------------------------------------------------------
-- Vehículos y pólizas
-- ---------------------------------------------------------------------

CREATE TABLE vehiculo (
    idVehiculo INT AUTO_INCREMENT PRIMARY KEY,
    anio INT NOT NULL CHECK (anio BETWEEN 1950 AND 2100),
    numSerie VARCHAR(17) NOT NULL UNIQUE,
    placas VARCHAR(15) NOT NULL UNIQUE,
    color VARCHAR(30),
    idCliente INT NOT NULL,
    idModelo INT NOT NULL,
    idUso INT NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    FOREIGN KEY (idCliente) REFERENCES cliente(idCliente) ON UPDATE CASCADE,
    FOREIGN KEY (idModelo) REFERENCES modelo(idModelo) ON UPDATE CASCADE,
    FOREIGN KEY (idUso) REFERENCES usovehiculo(idUso) ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE poliza (
    idPoliza INT AUTO_INCREMENT PRIMARY KEY,
    numeroPoliza VARCHAR(50) UNIQUE,
    fechaInicio DATE NOT NULL,
    fechaFin DATE NOT NULL,
    primaTotal DECIMAL(10,2) NOT NULL CHECK (primaTotal > 0),
    estado VARCHAR(15) NOT NULL CHECK (estado IN ('EN_TRAMITE', 'ACTIVA', 'VENCIDA', 'CANCELADA')),
    idVehiculo INT NOT NULL,
    idAseguradora INT NOT NULL,
    idFormaPago INT NOT NULL,
    idPolizaAnterior INT,
    CONSTRAINT chk_vigencia CHECK (fechaFin > fechaInicio),
    FOREIGN KEY (idVehiculo) REFERENCES vehiculo(idVehiculo) ON UPDATE CASCADE,
    FOREIGN KEY (idAseguradora) REFERENCES aseguradora(idAseguradora) ON UPDATE CASCADE,
    FOREIGN KEY (idFormaPago) REFERENCES formapago(idFormaPago) ON UPDATE CASCADE,
    FOREIGN KEY (idPolizaAnterior) REFERENCES poliza(idPoliza) ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE polizacobertura (
    idPoliza INT NOT NULL,
    idCobertura INT NOT NULL,
    sumaAsegurada DECIMAL(12,2) NOT NULL CHECK (sumaAsegurada > 0),
    deducible DECIMAL(10,2) NOT NULL DEFAULT 0 CHECK (deducible >= 0),
    PRIMARY KEY (idPoliza, idCobertura),
    FOREIGN KEY (idPoliza) REFERENCES poliza(idPoliza) ON UPDATE CASCADE,
    FOREIGN KEY (idCobertura) REFERENCES cobertura(idCobertura) ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE pago (
    idPago INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    monto DECIMAL(10,2) NOT NULL CHECK (monto > 0),
    referencia VARCHAR(100),
    anulado BOOLEAN NOT NULL DEFAULT FALSE,
    motivoAnulacion VARCHAR(255),
    idPoliza INT NOT NULL,
    idMetodoPago INT NOT NULL,
    idUsuario INT NOT NULL,
    FOREIGN KEY (idPoliza) REFERENCES poliza(idPoliza) ON UPDATE CASCADE,
    FOREIGN KEY (idMetodoPago) REFERENCES metodopago(idMetodoPago) ON UPDATE CASCADE,
    FOREIGN KEY (idUsuario) REFERENCES usuario(idUsuario) ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE solicitud (
    idSolicitud INT AUTO_INCREMENT PRIMARY KEY,
    fechaSolicitud DATE NOT NULL,
    descripcion TEXT NOT NULL,
    estado VARCHAR(15) NOT NULL DEFAULT 'PENDIENTE' CHECK (estado IN ('PENDIENTE', 'APROBADA', 'RECHAZADA')),
    fechaResolucion DATE,
    motivoResolucion VARCHAR(255),
    idPoliza INT NOT NULL,
    idTipoSolicitud INT NOT NULL,
    idAgente INT NOT NULL,
    idAutorizador INT,
    FOREIGN KEY (idPoliza) REFERENCES poliza(idPoliza) ON UPDATE CASCADE,
    FOREIGN KEY (idTipoSolicitud) REFERENCES tiposolicitud(idTipoSolicitud) ON UPDATE CASCADE,
    FOREIGN KEY (idAgente) REFERENCES usuario(idUsuario) ON UPDATE CASCADE,
    FOREIGN KEY (idAutorizador) REFERENCES usuario(idUsuario) ON UPDATE CASCADE
) ENGINE=InnoDB;

-- =====================================================================
-- Datos de prueba
-- =====================================================================

INSERT INTO marca (nombre) VALUES ('Nissan'), ('Chevrolet'), ('Toyota'), ('Volkswagen');

INSERT INTO modelo (nombre, idMarca) VALUES
('Versa', 1), ('Sentra', 1), ('Aveo', 2), ('Onix', 2), ('Corolla', 3), ('Hilux', 3), ('Jetta', 4);

INSERT INTO usovehiculo (nombre) VALUES ('Particular'), ('Comercial'), ('Servicio público'), ('Carga');

INSERT INTO aseguradora (nombre, rfc, telefono, correo, nombreContacto) VALUES
('GNP Seguros', 'GNS921124AA1', '8005002000', 'contacto@gnp.com.mx', 'Laura Gómez Ruiz'),
('Qualitas', 'QCS931209BB2', '8007002262', 'contacto@qualitas.com.mx', 'Pedro Sánchez Mora');

INSERT INTO formapago (nombre, numeroPagos) VALUES
('Contado', 1), ('Semestral', 2), ('Trimestral', 4), ('Mensual', 12);

INSERT INTO cobertura (nombre, descripcion) VALUES
('Daños Materiales', 'Cubre los daños sufridos por el vehículo asegurado'),
('Robo Total', 'Cubre el robo total del vehículo asegurado'),
('Responsabilidad Civil', 'Cubre daños a terceros en sus bienes y personas'),
('Gastos Médicos Ocupantes', 'Cubre la atención médica de los ocupantes del vehículo');

INSERT INTO metodopago (nombre) VALUES ('Efectivo'), ('Tarjeta de débito'), ('Tarjeta de crédito'), ('Transferencia');

INSERT INTO rol (nombre) VALUES ('ADMINISTRADOR'), ('AGENTE'), ('SUPERVISOR');

INSERT INTO tiposolicitud (nombre) VALUES ('Cancelación'), ('Endoso'), ('Renovación'), ('Aclaración');

-- Contraseña de prueba de los tres usuarios: 1234
INSERT INTO usuario (nombre, apellidoPaterno, apellidoMaterno, nombreUsuario, contrasenaHash, idRol) VALUES
('Bryan', 'Lázaro', 'Ramírez', 'bryan', SHA2('1234', 256), 2),
('Diego Jampierd', 'Cruz', 'Contreras', 'diego', SHA2('1234', 256), 1),
('Jonathan Gael', 'Alonso', 'López Arcos', 'jonathan', SHA2('1234', 256), 3);

INSERT INTO cliente (tipoPersona, nombre, apellidoPaterno, apellidoMaterno, rfc, curp, telefono, correo, fechaRegistro) VALUES
('FISICA', 'Carlos', 'Mendoza', 'López', 'MELC850214AB3', 'MELC850214HQRNPR05', '9847654321', 'carlos.mendoza@correo.com', '2025-11-05'),
('FISICA', 'María', 'Fernández', 'Ortiz', 'FEOM900530HK1', 'FEOM900530MQRRRR02', '9846176891', 'maria.fernandez@correo.com', '2026-11-12'),
('FISICA', 'Luis', 'Herrera', 'Castillo', 'HECL781122PQ7', 'HECL781122HQRRSS09', '9856677627', 'luis.herrera@correo.com', '2026-11-14'),
('MORAL', 'Transportes Riviera Maya S.A. de C.V.', NULL, NULL, 'TRM150310AB2', NULL, '9988841200', 'contacto@trmaya.com', '2026-11-16');

INSERT INTO vehiculo (anio, numSerie, placas, color, idCliente, idModelo, idUso) VALUES
(2024, '3N1CN7AD4RK123456', 'UTV-123-A', 'Blanco', 1, 1, 1),
(2023, '5YFBURHE5PP654321', 'UTW-456-B', 'Gris', 2, 5, 1),
(2022, '3G1J85CC5NS987654', 'UTX-789-C', 'Rojo', 3, 4, 1),
(2025, 'MR0HA3CD7S0112233', 'URA-101-D', 'Negro', 4, 6, 4);

-- Póliza 1: la del año pasado de Carlos, ya vencida
INSERT INTO poliza (numeroPoliza, fechaInicio, fechaFin, primaTotal, estado, idVehiculo, idAseguradora, idFormaPago, idPolizaAnterior) VALUES
('GNP-2025-0001', '2025-11-11', '2026-11-11', 11800.00, 'VENCIDA', 1, 1, 1, NULL);
-- Póliza 2: renovación de la póliza 1, activa
INSERT INTO poliza (numeroPoliza, fechaInicio, fechaFin, primaTotal, estado, idVehiculo, idAseguradora, idFormaPago, idPolizaAnterior) VALUES
('GNP-2026-0001', '2026-11-11', '2027-11-11', 12500.00, 'ACTIVA', 1, 1, 1, 1);
-- Póliza 3: de María, en trámite (ya tiene cobertura y primer pago; falta el número de póliza)
INSERT INTO poliza (numeroPoliza, fechaInicio, fechaFin, primaTotal, estado, idVehiculo, idAseguradora, idFormaPago, idPolizaAnterior) VALUES
(NULL, '2026-11-20', '2027-11-20', 8400.00, 'EN_TRAMITE', 2, 2, 2, NULL);

INSERT INTO polizacobertura (idPoliza, idCobertura, sumaAsegurada, deducible) VALUES
(1, 1, 240000.00, 5000.00),
(1, 3, 3000000.00, 0.00),
(2, 1, 250000.00, 5000.00),
(2, 2, 250000.00, 0.00),
(2, 3, 3000000.00, 0.00),
(3, 3, 3000000.00, 0.00);

INSERT INTO pago (fecha, monto, referencia, anulado, motivoAnulacion, idPoliza, idMetodoPago, idUsuario) VALUES
('2025-11-11', 11800.00, 'COMP-0001', FALSE, NULL, 1, 3, 1),
('2026-11-10', 12500.00, 'COMP-0102', TRUE, 'Se cobró con la tarjeta equivocada', 2, 3, 1),
('2026-11-11', 12500.00, 'COMP-0103', FALSE, NULL, 2, 3, 1),
('2026-11-20', 4200.00, 'TRF-88213', FALSE, NULL, 3, 4, 1);

INSERT INTO solicitud (fechaSolicitud, descripcion, estado, fechaResolucion, motivoResolucion, idPoliza, idTipoSolicitud, idAgente, idAutorizador) VALUES
('2026-11-15', 'El cliente pide cambiar el correo de envío de la póliza', 'APROBADA', '2026-11-16', 'Cambio de datos de contacto sin costo', 2, 2, 1, 2),
('2026-11-25', 'El cliente pregunta si puede agregar Gastos Médicos Ocupantes', 'PENDIENTE', NULL, NULL, 2, 4, 1, NULL);
