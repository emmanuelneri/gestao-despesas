INSERT INTO despesa(data, valor, descricao, categoria, paga) VALUES (now(), 20, 'Almoço', 'ALIMENTACAO', true);
INSERT INTO despesa(data, valor, descricao, categoria, paga) VALUES (now(), 500, 'Pós graduação', 'EDUCACAO', true);
INSERT INTO despesa(data, valor, descricao, categoria, paga) VALUES (now(), 10, 'Onibus', 'TRANSPORTE', true);
INSERT INTO despesa(data, valor, descricao, categoria, paga) VALUES (now(), 1200, 'Compra celular', 'OUTROS', true);
INSERT INTO despesa(data, valor, descricao, categoria, paga) VALUES (now(), 100, 'Luz', 'MORARIA', false);
INSERT INTO despesa(data, valor, descricao, categoria, paga) VALUES (now(), 70, 'Agua', 'MORARIA', false);
INSERT INTO despesa(data, valor, descricao, categoria, paga) VALUES (now(), 800, 'Aluguel', 'MORARIA', false);
INSERT INTO despesa(data, valor, descricao, categoria, paga) VALUES ((now() - INTERVAL '1 days'), 300, 'Plano de saúde', 'SAUDE', false);
INSERT INTO despesa(data, valor, descricao, categoria, paga) VALUES ((now() - INTERVAL '10 days'), 650, 'Carro - Parcela 12/24', 'OUTROS', false);
