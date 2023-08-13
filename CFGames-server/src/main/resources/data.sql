INSERT INTO perfil (perfil_id, authority) VALUES (1, 'ROLE_USER') ON CONFLICT DO NOTHING;
INSERT INTO perfil (perfil_id, authority) VALUES (2, 'ROLE_OPERATOR') ON CONFLICT DO NOTHING;
INSERT INTO perfil (perfil_id, authority) VALUES (3, 'ROLE_ADMIN') ON CONFLICT DO NOTHING;