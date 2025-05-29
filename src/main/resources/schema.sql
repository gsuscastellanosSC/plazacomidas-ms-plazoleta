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
