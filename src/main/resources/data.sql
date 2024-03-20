INSERT INTO perfil (perfil_id, authority) VALUES
(1, 'ROLE_USER'),
(2, 'ROLE_OPERATOR'),
(3, 'ROLE_ADMIN')
ON CONFLICT DO NOTHING;

INSERT INTO categorias (categoria_id, nome) VALUES
(1, 'Aventura'), (2, 'Simulação'),
(3, 'Quebra-Cabeças'), (4, 'Corrida'),
(5, 'Luta'), (6, 'Mundo Aberto'),
(7, 'Terror'), (8, 'Plataforma'),
(9, 'Roguelike'), (10, 'Arcade')
ON CONFLICT DO NOTHING;

INSERT INTO produtos (produto_id, altura, codigo_barras, comprimento, data_lancamento, descricao, largura, marca, peso, plataforma, preco, publisher, quantidade, status, titulo) VALUES
(1, 15.0, '1234567890123', 12.0, '2022-11-15', 'Ação-aventura emocionante', 8.0, 'Ubisoft', 200, 5, 59.99, 'Ubisoft', 50, 1, 'Assassins Creed Valhalla'),
(2, 16.0, '9876543210987', 12.5, '2021-08-10', 'Lute pelo seu reino', 9.0, 'Warner Bros', 180, 9, 49.99, 'Warner Bros', 30, 1, 'Mortal Kombat 11'),
(3, 14.0, '8765432109876', 11.5, '2020-06-25', 'Desvende mistérios antigos', 8.5, 'Sony', 170, 4, 39.99, 'Sony', 40, 1, 'Uncharted 4: A Thiefs End'),
(4, 15.5, '7654321098765', 13.2, '2021-03-18', 'Explore mundos abertos', 9.2, 'Rockstar Games', 190, 5, 49.99, 'Rockstar Games', 35, 1, 'Red Dead Redemption 2'),
(5, 17.0, '6543210987654', 14.0, '2019-11-08', 'Reviva a nostalgia', 10.0, 'Nintendo', 160, 9, 44.99, 'Nintendo', 60, 1, 'The Legend of Zelda: Links Awakening'),
(6, 13.5, '5432109876543', 11.8, '2020-04-10', 'Aventura espacial', 8.7, 'Electronic Arts', 220, 4, 35.99, 'Electronic Arts', 20, 1, 'Mass Effect: Legendary Edition'),
(7, 16.2, '4321098765432', 13.7, '2021-02-02', 'Sobreviva ao apocalipse', 9.5, 'Capcom', 180, 5, 42.99, 'Capcom', 25, 1, 'Resident Evil Village'),
(8, 14.8, '3210987654321', 12.3, '2020-07-07', 'Estratégia militar', 8.2, 'Sega', 210, 9, 37.99, 'Sega', 45, 1, 'Total War: Three Kingdoms'),
(9, 15.3, '2109876543210', 13.0, '2019-05-22', 'Ação e aventura no Velho Oeste', 9.8, 'Ubisoft', 195, 5, 49.99, 'Ubisoft', 28, 1, 'Wild West Online'),
(10, 16.5, '1098765432109', 12.6, '2021-10-20', 'Um épico de fantasia', 9.6, 'Square Enix', 185, 5, 54.99, 'Square Enix', 22, 1, 'Final Fantasy XVI')
ON CONFLICT DO NOTHING;


INSERT INTO categorias_produtos (produto_id, categoria_id) VALUES
-- Jogo 1 (Assassins Creed Valhalla)
(1, 1), -- Categoria "Aventura"
(1, 6), -- Categoria "Mundo Aberto"
-- Jogo 2 (Mortal Kombat 11)
(2, 5), -- Categoria "Luta"
(2, 10), -- Categoria "Arcade"
-- Jogo 3 (Uncharted 4: A Thief's End)
(3, 1), -- Categoria "Aventura"
(3, 8), -- Categoria "Plataforma"
-- Jogo 4 (Red Dead Redemption 2)
(4, 5), -- Categoria "Luta"
(4, 6), -- Categoria "Mundo Aberto"
-- Jogo 5 (The Legend of Zelda: Link's Awakening)
(5, 1), -- Categoria "Aventura"
(5, 8), -- Categoria "Plataforma"
-- Jogo 6 (Mass Effect: Legendary Edition)
(6, 1), -- Categoria "Aventura"
(6, 6), -- Categoria "Mundo Aberto"
-- Jogo 7 (Resident Evil Village)
(7, 5), -- Categoria "Luta"
(7, 7), -- Categoria "Terror"
-- Jogo 8 (Total War: Three Kingdoms)
(8, 6), -- Categoria "Mundo Aberto"
(8, 8), -- Categoria "Plataforma"
-- Jogo 9 (Wild West Online)
(9, 5), -- Categoria "Luta"
(9, 7), -- Categoria "Terror"
-- Jogo 10 (Final Fantasy XVI)
(10, 1), -- Categoria "Aventura"
(10, 8)  -- Categoria "Plataforma"
ON CONFLICT DO NOTHING;

