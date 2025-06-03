INSERT INTO restaurantes (nombre, nit, direccion, telefono, urllogo, id_propietario)
VALUES
    ('Tacos El Mexicano', '1234567890', 'Calle 10 # 5-20', '+573012345678', 'https://imagenes.com/tacos.png', 1),
    ('Hamburguesas Juan', '9876543210', 'Carrera 15 # 10-50', '+573098765432', 'https://imagenes.com/hamburguesas.png', 1);

INSERT INTO categorias (nombre, descripcion) VALUES
                                                 ('Entradas', 'Platos ligeros para iniciar la comida'),
                                                 ('Platos fuertes', 'Comidas principales con mayor porción'),
                                                 ('Postres', 'Dulces para finalizar la comida'),
                                                 ('Bebidas', 'Bebidas frías y calientes para acompañar');
