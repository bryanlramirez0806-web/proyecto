CREATE DATABASE IF NOT EXISTS polizas CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE polizas;

CREATE TABLE cliente (
    idCliente INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    correo VARCHAR(100),
    rfc VARCHAR(15) NOT NULL UNIQUE,
    direccion VARCHAR(255),
    medioContacto VARCHAR(50) CHECK (medioContacto IN ('WHATSAPP', 'CORREO', 'LLAMADA')),
    activo BOOLEAN DEFAULT TRUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE usuario (
    idUsuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    telefono VARCHAR(20),
    correo VARCHAR(100) NOT NULL,
    rol VARCHAR(50) NOT NULL CHECK (rol IN ('ADMINISTRADOR', 'VENDEDOR', 'OPERACIONES')),
    activo BOOLEAN DEFAULT TRUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE documento (
    idDocumento INT AUTO_INCREMENT PRIMARY KEY,
    idCliente INT NOT NULL,
    tipo VARCHAR(50) NOT NULL CHECK (tipo IN ('INE', 'COMPROBANTE_DOMICILIO', 'TARJETA_CIRCULACION')),
    fechaRecepcion DATE NOT NULL,
    rutaArchivo VARCHAR(255) NOT NULL,
    FOREIGN KEY (idCliente) REFERENCES cliente(idCliente) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE marca (
    idMarca INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE submarca (
    idSubmarca INT AUTO_INCREMENT PRIMARY KEY,
    idMarca INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    FOREIGN KEY (idMarca) REFERENCES marca(idMarca) ON UPDATE CASCADE,
    UNIQUE (idMarca, nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE vehiculo (
    idVehiculo INT AUTO_INCREMENT PRIMARY KEY,
    idCliente INT NOT NULL,
    idSubmarca INT NOT NULL,
    modelo VARCHAR(20) NOT NULL,
    tipoVehiculo VARCHAR(50) NOT NULL,
    numeroSerie VARCHAR(50) NOT NULL UNIQUE,
    placas VARCHAR(20) NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (idCliente) REFERENCES cliente(idCliente) ON UPDATE CASCADE,
    FOREIGN KEY (idSubmarca) REFERENCES submarca(idSubmarca) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE companiaseguros (
    idCompania INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    correo VARCHAR(100),
    sitioValidacion VARCHAR(255),
    activo BOOLEAN DEFAULT TRUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE tipopoliza (
    idTipo INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    activo BOOLEAN DEFAULT TRUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE cobertura (
    idCobertura INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    activo BOOLEAN DEFAULT TRUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE cotizacion (
    idCotizacion INT AUTO_INCREMENT PRIMARY KEY,
    idVehiculo INT NOT NULL,
    idUsuario INT NOT NULL,
    idCompania INT NOT NULL,
    idTipo INT NOT NULL,
    fecha DATE NOT NULL,
    costo DECIMAL(10,2) NOT NULL CHECK (costo > 0),
    opcionPago VARCHAR(50) NOT NULL CHECK (opcionPago IN ('COMPLETO', 'PARCIALIDADES')),
    estatus VARCHAR(50) NOT NULL CHECK (estatus IN ('PENDIENTE', 'ACEPTADA', 'RECHAZADA')),
    FOREIGN KEY (idVehiculo) REFERENCES vehiculo(idVehiculo) ON UPDATE CASCADE,
    FOREIGN KEY (idUsuario) REFERENCES usuario(idUsuario) ON UPDATE CASCADE,
    FOREIGN KEY (idCompania) REFERENCES companiaseguros(idCompania) ON UPDATE CASCADE,
    FOREIGN KEY (idTipo) REFERENCES tipopoliza(idTipo) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE poliza (
    idPoliza INT AUTO_INCREMENT PRIMARY KEY,
    numeroPoliza VARCHAR(50) DEFAULT NULL UNIQUE,
    idCotizacion INT DEFAULT NULL UNIQUE,
    idVehiculo INT NOT NULL,
    idCompania INT NOT NULL,
    idUsuario INT NOT NULL,
    idTipo INT NOT NULL,
    idPolizaAnterior INT DEFAULT NULL,
    fechaEmision DATE DEFAULT NULL,
    fechaInicio DATE NOT NULL,
    fechaVencimiento DATE NOT NULL,
    primaTotal DECIMAL(10,2) NOT NULL CHECK (primaTotal > 0),
    estatus VARCHAR(50) NOT NULL CHECK (estatus IN ('EN_TRAMITE', 'ACTIVA', 'VENCIDA', 'CANCELADA')),
    CONSTRAINT chk_fechas CHECK (fechaVencimiento > fechaInicio),
    FOREIGN KEY (idCotizacion) REFERENCES cotizacion(idCotizacion) ON UPDATE CASCADE,
    FOREIGN KEY (idVehiculo) REFERENCES vehiculo(idVehiculo) ON UPDATE CASCADE,
    FOREIGN KEY (idCompania) REFERENCES companiaseguros(idCompania) ON UPDATE CASCADE,
    FOREIGN KEY (idUsuario) REFERENCES usuario(idUsuario) ON UPDATE CASCADE,
    FOREIGN KEY (idTipo) REFERENCES tipopoliza(idTipo) ON UPDATE CASCADE,
    FOREIGN KEY (idPolizaAnterior) REFERENCES poliza(idPoliza) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE detallecobertura (
    idPoliza INT NOT NULL,
    idCobertura INT NOT NULL,
    sumaAsegurada DECIMAL(12,2) NOT NULL CHECK (sumaAsegurada > 0),
    deducible DECIMAL(10,2) NOT NULL CHECK (deducible >= 0),
    PRIMARY KEY (idPoliza, idCobertura),
    FOREIGN KEY (idPoliza) REFERENCES poliza(idPoliza) ON UPDATE CASCADE,
    FOREIGN KEY (idCobertura) REFERENCES cobertura(idCobertura) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE pago (
    idPago INT AUTO_INCREMENT PRIMARY KEY,
    idPoliza INT NOT NULL,
    numeroParcialidad INT NOT NULL,
    monto DECIMAL(10,2) NOT NULL CHECK (monto > 0),
    fechaLimite DATE NOT NULL,
    fechaPago DATE DEFAULT NULL,
    metodoPago VARCHAR(50) CHECK (metodoPago IN ('EFECTIVO', 'TARJETA', 'TRANSFERENCIA')),
    comprobante VARCHAR(100),
    estatus VARCHAR(50) NOT NULL CHECK (estatus IN ('PENDIENTE', 'PAGADO')),
    FOREIGN KEY (idPoliza) REFERENCES poliza(idPoliza) ON UPDATE CASCADE,
    UNIQUE (idPoliza, numeroParcialidad)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE notificacion (
    idNotificacion INT AUTO_INCREMENT PRIMARY KEY,
    idPoliza INT NOT NULL,
    idUsuario INT NOT NULL,
    tipo VARCHAR(50) NOT NULL CHECK (tipo IN ('EMISION', 'VENCIMIENTO')),
    fecha DATE NOT NULL,
    medio VARCHAR(50) NOT NULL CHECK (medio IN ('WHATSAPP', 'CORREO', 'LLAMADA')),
    costoRenovacion DECIMAL(10,2) NOT NULL CHECK (costoRenovacion >= 0),
    realizada BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (idPoliza) REFERENCES poliza(idPoliza) ON UPDATE CASCADE,
    FOREIGN KEY (idUsuario) REFERENCES usuario(idUsuario) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE cancelacion (
    idCancelacion INT AUTO_INCREMENT PRIMARY KEY,
    idPoliza INT NOT NULL UNIQUE,
    idUsuario INT NOT NULL,
    fechaSolicitud DATE NOT NULL,
    motivo TEXT NOT NULL,
    montoDevolucion DECIMAL(10,2) NOT NULL CHECK (montoDevolucion >= 0),
    estatusDevolucion VARCHAR(50) NOT NULL CHECK (estatusDevolucion IN ('NO_APLICA', 'PENDIENTE', 'REALIZADA')),
    FOREIGN KEY (idPoliza) REFERENCES poliza(idPoliza) ON UPDATE CASCADE,
    FOREIGN KEY (idUsuario) REFERENCES usuario(idUsuario) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO usuario (nombre, telefono, correo, rol) VALUES
('Bryan Ramírez', '9846721713', 'bryan@despacho.com', 'VENDEDOR'),
('Diego Cruz', '9851234567', 'diego@despacho.com', 'ADMINISTRADOR'),
('Jonathan Alonso', '9842345678', 'jonathan@despacho.com', 'OPERACIONES');

INSERT INTO cliente (nombre, telefono, correo, rfc, direccion, medioContacto) VALUES
('Carlos Mendoza López', '9847654321', 'carlos.mendoza@correo.com', 'MELC850214AB3', 'Calle 10 Norte 245, Playa del Carmen, Q. Roo', 'WHATSAPP'),
('María Fernández Ortiz', '9846176891', 'maria.fernandez@correo.com', 'FEOM900530HK1', 'Av. Constituyentes 120, Playa del Carmen, Q. Roo', 'CORREO'),
('Luis Herrera Castillo', '9856677627', 'luis.herrera@correo.com', 'HECL781122PQ7', 'Calle Cedros 33, Playa del Carmen, Q. Roo', 'LLAMADA');

INSERT INTO documento (idCliente, tipo, fechaRecepcion, rutaArchivo) VALUES
(1, 'INE', '2026-11-10', 'C:/documentos/cliente1/ine.pdf'),
(1, 'COMPROBANTE_DOMICILIO', '2026-11-10', 'C:/documentos/cliente1/domicilio.pdf'),
(1, 'TARJETA_CIRCULACION', '2026-11-10', 'C:/documentos/cliente1/tarjeta.pdf'),
(2, 'INE', '2026-11-12', 'C:/documentos/cliente2/ine.pdf');

INSERT INTO marca (nombre) VALUES ('Nissan'), ('Chevrolet'), ('Toyota'), ('Volkswagen');

INSERT INTO submarca (idMarca, nombre) VALUES
(1, 'Versa'), (1, 'Sentra'), (2, 'Aveo'), (2, 'Onix'), (3, 'Corolla'), (4, 'Jetta');

INSERT INTO vehiculo (idCliente, idSubmarca, modelo, tipoVehiculo, numeroSerie, placas) VALUES
(1, 1, '2024', 'Sedán', '3N1CN7AD4RK123456', 'UTV-123-A'),
(2, 5, '2023', 'Sedán', '5YFBURHE5PP654321', 'UTW-456-B'),
(3, 4, '2022', 'Hatchback', '3G1J85CC5NS987654', 'UTX-789-C');

INSERT INTO companiaseguros (nombre, telefono, correo, sitioValidacion) VALUES
('GNP Seguros', '8005002000', 'contacto@gnp.com.mx', 'https://www.gnp.com.mx/validar'),
('Qualitas', '8007002262', 'contacto@qualitas.com.mx', 'https://www.qualitas.com.mx/validar');

INSERT INTO tipopoliza (nombre, descripcion) VALUES
('Amplia', 'Cobertura completa que incluye daños materiales y robo total'),
('Limitada', 'Cubre responsabilidad civil y robo total del vehículo'),
('Daños a Terceros', 'Cubre únicamente los daños causados a terceros');

INSERT INTO cobertura (nombre, descripcion) VALUES
('Daños Materiales', 'Cubre los daños sufridos por el vehículo asegurado'),
('Robo Total', 'Cubre el robo total del vehículo asegurado'),
('Responsabilidad Civil', 'Cubre daños a terceros en sus bienes y personas'),
('Gastos Médicos Ocupantes', 'Cubre la atención médica de los ocupantes del vehículo');

INSERT INTO cotizacion (idVehiculo, idUsuario, idCompania, idTipo, fecha, costo, opcionPago, estatus) VALUES
(1, 1, 1, 1, '2026-11-10', 12500.00, 'COMPLETO', 'ACEPTADA'),
(2, 1, 2, 2, '2026-11-12', 8400.00, 'PARCIALIDADES', 'ACEPTADA'),
(3, 1, 1, 3, '2026-11-14', 5200.00, 'COMPLETO', 'PENDIENTE');

INSERT INTO poliza (numeroPoliza, idCotizacion, idVehiculo, idCompania, idUsuario, idTipo, fechaEmision, fechaInicio, fechaVencimiento, primaTotal, estatus) VALUES
('GNP-2026-0001', 1, 1, 1, 1, 1, '2026-11-11', '2026-11-11', '2027-11-11', 12500.00, 'ACTIVA');

INSERT INTO poliza (numeroPoliza, idCotizacion, idVehiculo, idCompania, idUsuario, idTipo, fechaEmision, fechaInicio, fechaVencimiento, primaTotal, estatus) VALUES
(NULL, 2, 2, 2, 1, 2, NULL, '2026-11-20', '2027-11-20', 8400.00, 'EN_TRAMITE');

INSERT INTO detallecobertura (idPoliza, idCobertura, sumaAsegurada, deducible) VALUES
(1, 1, 250000.00, 5000.00),
(1, 2, 250000.00, 0.00),
(1, 3, 3000000.00, 0.00),
(2, 3, 3000000.00, 0.00);

INSERT INTO pago (idPoliza, numeroParcialidad, monto, fechaLimite, fechaPago, metodoPago, comprobante, estatus) VALUES
(1, 1, 12500.00, '2026-11-11', '2026-11-11', 'TARJETA', 'COMP-0001', 'PAGADO'),
(2, 1, 4200.00, '2026-11-20', NULL, NULL, NULL, 'PENDIENTE'),
(2, 2, 4200.00, '2026-12-20', NULL, NULL, NULL, 'PENDIENTE');

INSERT INTO notificacion (idPoliza, idUsuario, tipo, fecha, medio, costoRenovacion, realizada) VALUES
(1, 1, 'EMISION', '2026-11-11', 'WHATSAPP', 0.00, 1);
