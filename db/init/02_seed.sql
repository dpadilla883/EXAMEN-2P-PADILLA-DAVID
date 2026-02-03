INSERT INTO usuarios (id, nombre, email, capital_disponible) VALUES
('a1b2c3d4-e5f6-7890-abcd-ef1234567890','Juan Pérez','juan.perez@email.com',5000.00),
('b2c3d4e5-f6a7-8901-bcde-f23456789012','María García','maria.garcia@email.com',8000.00),
('c3d4e5f6-a7b8-9012-cdef-345678901234','Carlos Ruiz','carlos.ruiz@email.com',1200.00),
('d4e5f6a7-b8c9-0123-def0-456789012345','Ana López','ana.lopez@email.com',4000.00),
('e5f6a7b8-c9d0-1234-efab-567890123456','Luis Torres','luis.torres@email.com',2000.00);


INSERT INTO productos_financieros (id, nombre, descripcion, costo, porcentaje_retorno, activo) VALUES
('11111111-1111-1111-1111-111111111111','ETF Global','ETF diversificado global',1500.00,12.00,true),
('22222222-2222-2222-2222-222222222222','Fondo Acciones Tech','Fondo de acciones tecnológicas',1000.00,8.50,true),
('33333333-3333-3333-3333-333333333333','Bonos Corporativos AAA','Bonos de alta calificación',500.00,5.25,true),
('44444444-4444-4444-4444-444444444444','Fondo de Dividendos','Fondo orientado a dividendos',800.00,6.75,true),
('55555555-5555-5555-5555-555555555555','Bonos del Tesoro','Bonos soberanos',1200.00,4.50,true),
('66666666-6666-6666-6666-666666666666','Fondo Conservador','Fondo de bajo riesgo',600.00,3.25,true),
('77777777-7777-7777-7777-777777777777','Crowdfunding Regulado','Inversión alternativa regulada',900.00,10.00,true),
('88888888-8888-8888-8888-888888888888','Producto Inactivo','No debe mostrarse',700.00,9.00,false);
