CREATE TABLE restaurantes (
                              id BIGINT PRIMARY KEY AUTO_INCREMENT,
                              nombre VARCHAR(100) NOT NULL,
                              nit VARCHAR(20) NOT NULL,
                              direccion VARCHAR(255) NOT NULL,
                              telefono VARCHAR(20) NOT NULL,
                              urllogo VARCHAR(255) NOT NULL,
                              id_propietario BIGINT NOT NULL
);

-- Tabla de categorías actualizada con descripción
CREATE TABLE categorias (
                            id BIGINT PRIMARY KEY AUTO_INCREMENT,
                            nombre VARCHAR(50) NOT NULL,
                            descripcion VARCHAR(255) NOT NULL
);

CREATE TABLE trazabilidad (
                              id BIGINT PRIMARY KEY AUTO_INCREMENT,
                              id_pedido BIGINT NOT NULL,
                              id_cliente VARCHAR(100) NOT NULL,
                              correo_cliente VARCHAR(255) NOT NULL,
                              fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              estado_anterior VARCHAR(50),
                              estado_nuevo VARCHAR(50) NOT NULL,
                              id_empleado BIGINT,
                              correo_empleado VARCHAR(255)
);
