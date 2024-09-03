INSERT INTO tb_user (first_name, last_name, email, password) VALUES ('João', 'Silva', 'alex@gmail.com', '$2a$10$lDuTeVaBTZCU1ao7bi/XZOxjR/x.B0y78wJcua.vLjQzqJZvNZaQS');
INSERT INTO tb_user (first_name, last_name, email, password) VALUES ('Maria', 'Silva', 'maria@gmail.com', '$2a$10$lDuTeVaBTZCU1ao7bi/XZOxjR/x.B0y78wJcua.vLjQzqJZvNZaQS');

INSERT INTO tb_role (authority) VALUES ('ROLE_OPERATOR');
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);

INSERT INTO tb_pharmacy (name, address, phone, city, state, zip_code, url, img_url, created_at) VALUES ('Farmacia Central', 'Rua Principal, 123', '123456789', 'Cidade A', 'Estado A', '12345-678', 'farmaciacentral.com.br', 'farmaciacentral.jpg', NOW());
INSERT INTO tb_pharmacy (name, address, phone, city, state, zip_code, url, img_url, created_at) VALUES ('Farmacia Popular', 'Avenida Secundária, 456', '987654321', 'Cidade B', 'Estado B', '87654-321', 'farmaciapopular.com.br', 'farmaciapopular.jpg', NOW());

INSERT INTO tb_category (name, url, created_at) VALUES ('Medicamentos', 'farmacia.com.br/medicamentos', NOW());
INSERT INTO tb_category (name, url, created_at) VALUES ('Vitaminas e Suplementos', 'farmacia.com.br/vitaminas', NOW());
INSERT INTO tb_category (name, url, created_at) VALUES ('Beleza e Perfumaria', 'farmacia.com.br/beleza', NOW());
INSERT INTO tb_category (name, url, created_at) VALUES ('Higiene e Cuidados Pessoais', 'farmacia.com.br/higiene', NOW());
INSERT INTO tb_category (name, url, created_at) VALUES ('Bebês e Crianças', 'farmacia.com.br/bebes', NOW());
INSERT INTO tb_category (name, url, created_at) VALUES ('Primeiros Socorros', 'farmacia.com.br/primeiros-socorros', NOW());
INSERT INTO tb_category (name, url, created_at) VALUES ('Artigos Médicos', 'farmacia.com.br/artigos-medicos', NOW());
INSERT INTO tb_category (name, url, created_at) VALUES ('Dermocosméticos', 'farmacia.com.br/dermocosmeticos', NOW());
INSERT INTO tb_category (name, url, created_at) VALUES ('Aparelhos de Saúde', 'farmacia.com.br/aparelhos-saude', NOW());
INSERT INTO tb_category (name, url, created_at) VALUES ('Alimentos Saudáveis', 'farmacia.com.br/alimentos-saudaveis', NOW());


-- Associações para a Farmacia Central
INSERT INTO tb_pharmacy_category (pharmacy_id, category_id) VALUES (1, 1);
INSERT INTO tb_pharmacy_category (pharmacy_id, category_id) VALUES (1, 2);
INSERT INTO tb_pharmacy_category (pharmacy_id, category_id) VALUES (1, 3);
INSERT INTO tb_pharmacy_category (pharmacy_id, category_id) VALUES (1, 4);
INSERT INTO tb_pharmacy_category (pharmacy_id, category_id) VALUES (1, 5);

-- Associações para a Farmacia Popular
INSERT INTO tb_pharmacy_category (pharmacy_id, category_id) VALUES (2, 6);
INSERT INTO tb_pharmacy_category (pharmacy_id, category_id) VALUES (2, 7);
INSERT INTO tb_pharmacy_category (pharmacy_id, category_id) VALUES (2, 8);
INSERT INTO tb_pharmacy_category (pharmacy_id, category_id) VALUES (2, 9);
INSERT INTO tb_pharmacy_category (pharmacy_id, category_id) VALUES (2, 10);



INSERT INTO tb_product (name, description, price_from, price_final, img_url, url, rating, reviews_count, discount, created_at, category_id) VALUES ('Dipirona Monoidratada 500mg', 'Comprimido para alívio de dor e febre.', 7.99, 7.99, 'dipirona.jpg', 'farmacia.com.br/dipirona', 0, 0, 0, NOW(), 1);
INSERT INTO tb_product (name, description, price_from, price_final, img_url, url, rating, reviews_count, discount, created_at, category_id) VALUES ('Vitamina C 1000mg', 'Cápsula para fortalecimento do sistema imunológico.', 19.99, 19.99, 'vitaminaC.jpg', 'farmacia.com.br/vitaminaC', 0, 0, 0, NOW(), 2);
INSERT INTO tb_product (name, description, price_from, price_final, img_url, url, rating, reviews_count, discount, created_at, category_id) VALUES ('Shampoo Anticaspa 200ml', 'Limpeza e combate à caspa.', 24.99, 24.99, 'shampooAnticaspa.jpg', 'farmacia.com.br/shampooAnticaspa', 0, 0, 0, NOW(), 3);
INSERT INTO tb_product (name, description, price_from, price_final, img_url, url, rating, reviews_count, discount, created_at, category_id) VALUES ('Protetor Solar FPS 50', 'Proteção solar para todos os tipos de pele.', 49.99, 49.99, 'protetorSolar.jpg', 'farmacia.com.br/protetorSolar', 0, 0, 0, NOW(), 3);
INSERT INTO tb_product (name, description, price_from, price_final, img_url, url, rating, reviews_count, discount, created_at, category_id) VALUES ('Termômetro Digital', 'Termômetro para medição de temperatura corporal.', 29.99, 29.99, 'termometro.jpg', 'farmacia.com.br/termometro', 0, 0, 0, NOW(), 10);
INSERT INTO tb_product (name, description, price_from, price_final, img_url, url, rating, reviews_count, discount, created_at, category_id) VALUES ('Creme Dental 90g', 'Creme dental para higiene bucal diária.', 5.99, 5.99, 'cremeDental.jpg', 'farmacia.com.br/cremeDental', 0, 0, 0, NOW(), 4);
INSERT INTO tb_product (name, description, price_from, price_final, img_url, url, rating, reviews_count, discount, created_at, category_id) VALUES ('Escova Dental', 'Escova dental macia para uso diário.', 3.99, 3.99, 'escovaDental.jpg', 'farmacia.com.br/escovaDental', 0, 0, 0, NOW(), 4);
INSERT INTO tb_product (name, description, price_from, price_final, img_url, url, rating, reviews_count, discount, created_at, category_id) VALUES ('Álcool Gel 70%', 'Álcool gel para higienização das mãos.', 9.99, 9.99, 'alcoolGel.jpg', 'farmacia.com.br/alcoolGel', 0, 0, 0, NOW(), 6);
INSERT INTO tb_product (name, description, price_from, price_final, img_url, url, rating, reviews_count, discount, created_at, category_id) VALUES ('Band-Aid', 'Curativo adesivo para pequenos ferimentos.', 4.99, 4.99, 'bandAid.jpg', 'farmacia.com.br/bandAid', 0, 0, 0, NOW(), 6);
INSERT INTO tb_product (name, description, price_from, price_final, img_url, url, rating, reviews_count, discount, created_at, category_id) VALUES ('Antisséptico Bucal 250ml', 'Enxaguante bucal para higiene oral.', 14.99, 14.99, 'antissepticoBucal.jpg', 'farmacia.com.br/antissepticoBucal', 0, 0, 0, NOW(), 4);
INSERT INTO tb_product (name, description, price_from, price_final, img_url, url, rating, reviews_count, discount, created_at, category_id) VALUES ('Sabonete Líquido 200ml', 'Sabonete líquido para limpeza das mãos.', 7.99, 7.99, 'saboneteLiquido.jpg', 'farmacia.com.br/saboneteLiquido', 0, 0, 0, NOW(), 4);
INSERT INTO tb_product (name, description, price_from, price_final, img_url, url, rating, reviews_count, discount, created_at, category_id) VALUES ('Desodorante Aerosol 150ml', 'Desodorante aerosol para proteção diária.', 12.99, 12.99, 'desodorante.jpg', 'farmacia.com.br/desodorante', 0, 0, 0, NOW(), 4);
INSERT INTO tb_product (name, description, price_from, price_final, img_url, url, rating, reviews_count, discount, created_at, category_id) VALUES ('Lenços Umedecidos', 'Lenços umedecidos para limpeza e higiene.', 8.99, 8.99, 'lencosUmedecidos.jpg', 'farmacia.com.br/lencosUmedecidos', 0, 0, 0, NOW(), 5);
INSERT INTO tb_product (name, description, price_from, price_final, img_url, url, rating, reviews_count, discount, created_at, category_id) VALUES ('Fralda Geriátrica', 'Fralda geriátrica para incontinência.', 39.99, 39.99, 'fraldaGeriatrica.jpg', 'farmacia.com.br/fraldaGeriatrica', 0, 0, 0, NOW(), 5);
INSERT INTO tb_product (name, description, price_from, price_final, img_url, url, rating, reviews_count, discount, created_at, category_id) VALUES ('Soro Fisiológico 500ml', 'Soro fisiológico para hidratação nasal.', 6.99, 6.99, 'soroFisiologico.jpg', 'farmacia.com.br/soroFisiologico', 0, 0, 0, NOW(), 6);
INSERT INTO tb_product (name, description, price_from, price_final, img_url, url, rating, reviews_count, discount, created_at, category_id) VALUES ('Gaze Estéril', 'Gaze estéril para curativos.', 2.99, 2.99, 'gazeEsteril.jpg', 'farmacia.com.br/gazeEsteril', 0, 0, 0, NOW(), 6);
INSERT INTO tb_product (name, description, price_from, price_final, img_url, url, rating, reviews_count, discount, created_at, category_id) VALUES ('Esparadrapo 10m', 'Esparadrapo para fixação de curativos.', 3.99, 3.99, 'esparadrapo.jpg', 'farmacia.com.br/esparadrapo', 0, 0, 0, NOW(), 6);
INSERT INTO tb_product (name, description, price_from, price_final, img_url, url, rating, reviews_count, discount, created_at, category_id) VALUES ('Pomada para Assaduras', 'Pomada para prevenção de assaduras.', 14.99, 14.99, 'pomadaAssaduras.jpg', 'farmacia.com.br/pomadaAssaduras', 0, 0, 0, NOW(), 5);
INSERT INTO tb_product (name, description, price_from, price_final, img_url, url, rating, reviews_count, discount, created_at, category_id) VALUES ('Creme Hidratante 200ml', 'Creme hidratante para pele seca.', 19.99, 19.99, 'cremeHidratante.jpg', 'farmacia.com.br/cremeHidratante', 0, 0, 0, NOW(), 3);
INSERT INTO tb_product (name, description, price_from, price_final, img_url, url, rating, reviews_count, discount, created_at, category_id) VALUES ('Repelente de Insetos 100ml', 'Repelente de insetos para uso corporal.', 15.99, 15.99, 'repelente.jpg', 'farmacia.com.br/repelente', 0, 0, 0, NOW(), 5);
INSERT INTO tb_product (name, description, price_from, price_final, img_url, url, rating, reviews_count, discount, created_at, category_id) VALUES ('Colírio 15ml', 'Colírio para alívio de irritações oculares.', 9.99, 9.99, 'colirio.jpg', 'farmacia.com.br/colirio', 0, 0, 0, NOW(), 6);
INSERT INTO tb_product (name, description, price_from, price_final, img_url, url, rating, reviews_count, discount, created_at, category_id) VALUES ('Luva Descartável', 'Luva descartável para procedimentos.', 12.99, 12.99, 'luvaDescartavel.jpg', 'farmacia.com.br/luvaDescartavel', 0, 0, 0, NOW(), 6);
INSERT INTO tb_product (name, description, price_from, price_final, img_url, url, rating, reviews_count, discount, created_at, category_id) VALUES ('Máscara Cirúrgica', 'Máscara cirúrgica para proteção.', 19.99, 19.99, 'mascaraCirurgica.jpg', 'farmacia.com.br/mascaraCirurgica', 0, 0, 0, NOW(), 6);
INSERT INTO tb_product (name, description, price_from, price_final, img_url, url, rating, reviews_count, discount, created_at, category_id) VALUES ('Termômetro Infravermelho', 'Termômetro infravermelho para medição de temperatura.', 89.99, 89.99, 'termometroInfravermelho.jpg', 'farmacia.com.br/termometroInfravermelho', 0, 0, 0, NOW(), 10);
INSERT INTO tb_product (name, description, price_from, price_final, img_url, url, rating, reviews_count, discount, created_at, category_id) VALUES ('Oxímetro de Pulso', 'Oxímetro para medição de saturação de oxigênio.', 59.99, 59.99, 'oximetro.jpg', 'farmacia.com.br/oximetro', 0, 0, 0, NOW(), 10);
INSERT INTO tb_product (name, description, price_from, price_final, img_url, url, rating, reviews_count, discount, created_at, category_id) VALUES ('Nebulizador', 'Nebulizador para tratamento respiratório.', 129.99, 129.99, 'nebulizador.jpg', 'farmacia.com.br/nebulizador', 0, 0, 0, NOW(), 10);
INSERT INTO tb_product (name, description, price_from, price_final, img_url, url, rating, reviews_count, discount, created_at, category_id) VALUES ('Balança Digital', 'Balança digital para medição de peso corporal.', 49.99, 49.99, 'balancaDigital.jpg', 'farmacia.com.br/balancaDigital', 0, 0, 0, NOW(), 10);
INSERT INTO tb_product (name, description, price_from, price_final, img_url, url, rating, reviews_count, discount, created_at, category_id) VALUES ('Cadeira de Rodas', 'Cadeira de rodas para mobilidade.', 499.99, 499.99, 'cadeiraRodas.jpg', 'farmacia.com.br/cadeiraRodas', 0, 0, 0, NOW(), 10);