INSERT INTO tb_user (first_name, last_name, email, password) VALUES ('João', 'Silva', 'joaopaulo@gmail.com', '$2a$10$3Zz9Zz9Zz9Zz9Zz9Zz9ZzO');
INSERT INTO tb_user (first_name, last_name, email, password) VALUES ('Maria', 'Silva', 'maria@gmail.com', '$2a$10$3Zz9Zz9Zz9Zz9Zz9Zz9ZzO');

INSERT INTO tb_role (authority) VALUES ('ROLE_OPERATOR');
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);

INSERT INTO tb_pharmacy (name, address, phone, city, state, zip_code, url, img_url, created_at) VALUES ('Farmacia Central', 'Rua Principal, 123', '123456789', 'Cidade A', 'Estado A', '12345-678', 'farmaciacentral.com.br', 'farmaciacentral.jpg', NOW());
INSERT INTO tb_pharmacy (name, address, phone, city, state, zip_code, url, img_url, created_at) VALUES ('Farmacia Popular', 'Avenida Secundária, 456', '987654321', 'Cidade B', 'Estado B', '87654-321', 'farmaciapopular.com.br', 'farmaciapopular.jpg', NOW());

INSERT INTO tb_category (name, url, created_at, pharmacy_id) VALUES ('Medicamentos', 'farmacia.com.br/medicamentos', NOW(), 1);
INSERT INTO tb_category (name, url, created_at, pharmacy_id) VALUES ('Vitaminas e Suplementos', 'farmacia.com.br/vitaminas', NOW(), 1);
INSERT INTO tb_category (name, url, created_at, pharmacy_id) VALUES ('Beleza e Perfumaria', 'farmacia.com.br/beleza', NOW(), 1);
INSERT INTO tb_category (name, url, created_at, pharmacy_id) VALUES ('Higiene e Cuidados Pessoais', 'farmacia.com.br/higiene', NOW(), 1);
INSERT INTO tb_category (name, url, created_at, pharmacy_id) VALUES ('Bebês e Crianças', 'farmacia.com.br/bebes', NOW(), 1);
INSERT INTO tb_category (name, url, created_at, pharmacy_id) VALUES ('Primeiros Socorros', 'farmacia.com.br/primeiros-socorros', NOW(), 1);
INSERT INTO tb_category (name, url, created_at, pharmacy_id) VALUES ('Artigos Médicos', 'farmacia.com.br/artigos-medicos', NOW(), 1);
INSERT INTO tb_category (name, url, created_at, pharmacy_id) VALUES ('Dermocosméticos', 'farmacia.com.br/dermocosmeticos', NOW(), 1);
INSERT INTO tb_category (name, url, created_at, pharmacy_id) VALUES ('Aparelhos de Saúde', 'farmacia.com.br/aparelhos-saude', NOW(), 1);
INSERT INTO tb_category (name, url, created_at, pharmacy_id) VALUES ('Alimentos Saudáveis', 'farmacia.com.br/alimentos-saudaveis', NOW(), 1);

INSERT INTO tb_product (name, description, price, img_url, url, created_at, category_id) VALUES ('Dipirona Monoidratada 500mg', 'Comprimido para alívio de dor e febre.', 7.99, 'dipirona.jpg', 'farmacia.com.br/dipirona', NOW(), 1);
INSERT INTO tb_product (name, description, price, img_url, url, created_at, category_id) VALUES ('Vitamina C 1000mg', 'Cápsula para fortalecimento do sistema imunológico.', 19.99, 'vitaminaC.jpg', 'farmacia.com.br/vitaminaC', NOW(), 2);
INSERT INTO tb_product (name, description, price, img_url, url, created_at, category_id) VALUES ('Shampoo Anticaspa 200ml', 'Limpeza e combate à caspa.', 24.99, 'shampooAnticaspa.jpg', 'farmacia.com.br/shampooAnticaspa', NOW(), 3);
INSERT INTO tb_product (name, description, price, img_url, url, created_at, category_id) VALUES ('Protetor Solar FPS 50', 'Proteção solar para todos os tipos de pele.', 49.99, 'protetorSolar.jpg', 'farmacia.com.br/protetorSolar', NOW(), 3);
INSERT INTO tb_product (name, description, price, img_url, url, created_at, category_id) VALUES ('Termômetro Digital', 'Termômetro para medição de temperatura corporal.', 29.99, 'termometro.jpg', 'farmacia.com.br/termometro', NOW(), 10);
INSERT INTO tb_product (name, description, price, img_url, url, created_at, category_id) VALUES ('Creme Dental 90g', 'Creme dental para higiene bucal diária.', 5.99, 'cremeDental.jpg', 'farmacia.com.br/cremeDental', NOW(), 4);
INSERT INTO tb_product (name, description, price, img_url, url, created_at, category_id) VALUES ('Escova Dental', 'Escova dental macia para uso diário.', 3.99, 'escovaDental.jpg', 'farmacia.com.br/escovaDental', NOW(), 4);
INSERT INTO tb_product (name, description, price, img_url, url, created_at, category_id) VALUES ('Álcool Gel 70\%', 'Álcool gel para higienização das mãos.', 9.99, 'alcoolGel.jpg', 'farmacia.com.br/alcoolGel', NOW(), 6);
INSERT INTO tb_product (name, description, price, img_url, url, created_at, category_id) VALUES ('Band-Aid', 'Curativo adesivo para pequenos ferimentos.', 4.99, 'bandAid.jpg', 'farmacia.com.br/bandAid', NOW(), 6);
INSERT INTO tb_product (name, description, price, img_url, url, created_at, category_id) VALUES ('Antisséptico Bucal 250ml', 'Enxaguante bucal para higiene oral.', 14.99, 'antissepticoBucal.jpg', 'farmacia.com.br/antissepticoBucal', NOW(), 4);
INSERT INTO tb_product (name, description, price, img_url, url, created_at, category_id) VALUES ('Sabonete Líquido 200ml', 'Sabonete líquido para limpeza das mãos.', 7.99, 'saboneteLiquido.jpg', 'farmacia.com.br/saboneteLiquido', NOW(), 4);
INSERT INTO tb_product (name, description, price, img_url, url, created_at, category_id) VALUES ('Desodorante Aerosol 150ml', 'Desodorante aerosol para proteção diária.', 12.99, 'desodorante.jpg', 'farmacia.com.br/desodorante', NOW(), 4);
INSERT INTO tb_product (name, description, price, img_url, url, created_at, category_id) VALUES ('Lenços Umedecidos', 'Lenços umedecidos para limpeza e higiene.', 8.99, 'lencosUmedecidos.jpg', 'farmacia.com.br/lencosUmedecidos', NOW(), 5);
INSERT INTO tb_product (name, description, price, img_url, url, created_at, category_id) VALUES ('Fralda Geriátrica', 'Fralda geriátrica para incontinência.', 39.99, 'fraldaGeriatrica.jpg', 'farmacia.com.br/fraldaGeriatrica', NOW(), 5);
INSERT INTO tb_product (name, description, price, img_url, url, created_at, category_id) VALUES ('Soro Fisiológico 500ml', 'Soro fisiológico para hidratação nasal.', 6.99, 'soroFisiologico.jpg', 'farmacia.com.br/soroFisiologico', NOW(), 6);
INSERT INTO tb_product (name, description, price, img_url, url, created_at, category_id) VALUES ('Gaze Estéril', 'Gaze estéril para curativos.', 2.99, 'gazeEsteril.jpg', 'farmacia.com.br/gazeEsteril', NOW(), 6);
INSERT INTO tb_product (name, description, price, img_url, url, created_at, category_id) VALUES ('Esparadrapo 10m', 'Esparadrapo para fixação de curativos.', 3.99, 'esparadrapo.jpg', 'farmacia.com.br/esparadrapo', NOW(), 6);
INSERT INTO tb_product (name, description, price, img_url, url, created_at, category_id) VALUES ('Pomada para Assaduras', 'Pomada para prevenção de assaduras.', 14.99, 'pomadaAssaduras.jpg', 'farmacia.com.br/pomadaAssaduras', NOW(), 5);
INSERT INTO tb_product (name, description, price, img_url, url, created_at, category_id) VALUES ('Creme Hidratante 200ml', 'Creme hidratante para pele seca.', 19.99, 'cremeHidratante.jpg', 'farmacia.com.br/cremeHidratante', NOW(), 3);
INSERT INTO tb_product (name, description, price, img_url, url, created_at, category_id) VALUES ('Repelente de Insetos 100ml', 'Repelente de insetos para uso corporal.', 15.99, 'repelente.jpg', 'farmacia.com.br/repelente', NOW(), 5);
INSERT INTO tb_product (name, description, price, img_url, url, created_at, category_id) VALUES ('Colírio 15ml', 'Colírio para alívio de irritações oculares.', 9.99, 'colirio.jpg', 'farmacia.com.br/colirio', NOW(), 6);
INSERT INTO tb_product (name, description, price, img_url, url, created_at, category_id) VALUES ('Luva Descartável', 'Luva descartável para procedimentos.', 12.99, 'luvaDescartavel.jpg', 'farmacia.com.br/luvaDescartavel', NOW(), 6);
INSERT INTO tb_product (name, description, price, img_url, url, created_at, category_id) VALUES ('Máscara Cirúrgica', 'Máscara cirúrgica para proteção.', 19.99, 'mascaraCirurgica.jpg', 'farmacia.com.br/mascaraCirurgica', NOW(), 6);
INSERT INTO tb_product (name, description, price, img_url, url, created_at, category_id) VALUES ('Termômetro Infravermelho', 'Termômetro infravermelho para medição de temperatura.', 89.99, 'termometroInfravermelho.jpg', 'farmacia.com.br/termometroInfravermelho', NOW(), 10);
INSERT INTO tb_product (name, description, price, img_url, url, created_at, category_id) VALUES ('Oxímetro de Pulso', 'Oxímetro para medição de saturação de oxigênio.', 59.99, 'oximetro.jpg', 'farmacia.com.br/oximetro', NOW(), 10);
INSERT INTO tb_product (name, description, price, img_url, url, created_at, category_id) VALUES ('Nebulizador', 'Nebulizador para tratamento respiratório.', 129.99, 'nebulizador.jpg', 'farmacia.com.br/nebulizador', NOW(), 10);
INSERT INTO tb_product (name, description, price, img_url, url, created_at, category_id) VALUES ('Balança Digital', 'Balança digital para medição de peso corporal.', 49.99, 'balancaDigital.jpg', 'farmacia.com.br/balancaDigital', NOW(), 10);
INSERT INTO tb_product (name, description, price, img_url, url, created_at, category_id) VALUES ('Cadeira de Rodas', 'Cadeira de rodas para mobilidade.', 499.99, 'cadeiraRodas.jpg', 'farmacia.com.br/cadeiraRodas', NOW(), 10);