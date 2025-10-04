-- DML Script for Voltz - Missão Tio Patinhas Database
-- Insert sample data for all tables

-- Insert Usuarios (both PF and PJ)
INSERT INTO T_USUARIO (id_usuario, tipo, email, senha, pais, estado, cidade, bairro, rua, numero_imovel) 
VALUES (SEQ_USUARIO.NEXTVAL, 'PF', 'joao.silva@email.com', 'senha123', 'Brasil', 'SP', 'São Paulo', 'Centro', 'Rua das Flores', '123');

INSERT INTO T_USUARIO (id_usuario, tipo, email, senha, pais, estado, cidade, bairro, rua, numero_imovel) 
VALUES (SEQ_USUARIO.NEXTVAL, 'PF', 'maria.santos@email.com', 'senha456', 'Brasil', 'RJ', 'Rio de Janeiro', 'Copacabana', 'Avenida Atlântica', '456');

INSERT INTO T_USUARIO (id_usuario, tipo, email, senha, pais, estado, cidade, bairro, rua, numero_imovel) 
VALUES (SEQ_USUARIO.NEXTVAL, 'PJ', 'contato@techcorp.com', 'senha789', 'Brasil', 'SP', 'São Paulo', 'Vila Olímpia', 'Rua dos Pinheiros', '789');

INSERT INTO T_USUARIO (id_usuario, tipo, email, senha, pais, estado, cidade, bairro, rua, numero_imovel) 
VALUES (SEQ_USUARIO.NEXTVAL, 'PF', 'pedro.oliveira@email.com', 'senha321', 'Brasil', 'MG', 'Belo Horizonte', 'Savassi', 'Rua da Bahia', '321');

INSERT INTO T_USUARIO (id_usuario, tipo, email, senha, pais, estado, cidade, bairro, rua, numero_imovel) 
VALUES (SEQ_USUARIO.NEXTVAL, 'PJ', 'financeiro@investcorp.com', 'senha654', 'Brasil', 'RJ', 'Rio de Janeiro', 'Centro', 'Avenida Rio Branco', '654');

-- Insert Pessoas Físicas
INSERT INTO T_PF (id_usuario, cpf, genero, idade, nome, sobrenome) 
VALUES (1, '123.456.789-01', 'Masculino', 28, 'João', 'Silva');

INSERT INTO T_PF (id_usuario, cpf, genero, idade, nome, sobrenome) 
VALUES (2, '987.654.321-02', 'Feminino', 32, 'Maria', 'Santos');

INSERT INTO T_PF (id_usuario, cpf, genero, idade, nome, sobrenome) 
VALUES (4, '456.789.123-04', 'Masculino', 35, 'Pedro', 'Oliveira');

-- Insert Pessoas Jurídicas
INSERT INTO T_PJ (id_usuario, cnpj, ramo, nome_fantasia) 
VALUES (3, '12.345.678/0001-90', 'Tecnologia', 'TechCorp Solutions');

INSERT INTO T_PJ (id_usuario, cnpj, ramo, nome_fantasia) 
VALUES (5, '98.765.432/0001-10', 'Investimentos', 'InvestCorp Financeira');

-- Insert Carteiras for each user
INSERT INTO T_CARTEIRA (id_carteira, saldo_em_real, id_usuario) 
VALUES (SEQ_CARTEIRA.NEXTVAL, 10000.00, 1);

INSERT INTO T_CARTEIRA (id_carteira, saldo_em_real, id_usuario) 
VALUES (SEQ_CARTEIRA.NEXTVAL, 25000.50, 2);

INSERT INTO T_CARTEIRA (id_carteira, saldo_em_real, id_usuario) 
VALUES (SEQ_CARTEIRA.NEXTVAL, 50000.00, 3);

INSERT INTO T_CARTEIRA (id_carteira, saldo_em_real, id_usuario) 
VALUES (SEQ_CARTEIRA.NEXTVAL, 15000.75, 4);

INSERT INTO T_CARTEIRA (id_carteira, saldo_em_real, id_usuario) 
VALUES (SEQ_CARTEIRA.NEXTVAL, 100000.00, 5);

-- Insert Moedas
INSERT INTO T_MOEDA (id_moeda, nome, simbolo, cotacao_para_real) 
VALUES (SEQ_MOEDA.NEXTVAL, 'Bitcoin', 'BTC', 500000.00);

INSERT INTO T_MOEDA (id_moeda, nome, simbolo, cotacao_para_real) 
VALUES (SEQ_MOEDA.NEXTVAL, 'Ethereum', 'ETH', 15000.00);

INSERT INTO T_MOEDA (id_moeda, nome, simbolo, cotacao_para_real) 
VALUES (SEQ_MOEDA.NEXTVAL, 'Solana', 'SOL', 800.00);

INSERT INTO T_MOEDA (id_moeda, nome, simbolo, cotacao_para_real) 
VALUES (SEQ_MOEDA.NEXTVAL, 'Cardano', 'ADA', 2.50);

INSERT INTO T_MOEDA (id_moeda, nome, simbolo, cotacao_para_real) 
VALUES (SEQ_MOEDA.NEXTVAL, 'Polygon', 'MATIC', 4.80);

-- Insert Investimentos
INSERT INTO T_INVESTIMENTO (id_investimento, id_moeda, id_carteira, quantidade_moeda) 
VALUES (SEQ_INVESTIMENTO.NEXTVAL, 1, 1, 0.02); -- João tem 0.02 BTC

INSERT INTO T_INVESTIMENTO (id_investimento, id_moeda, id_carteira, quantidade_moeda) 
VALUES (SEQ_INVESTIMENTO.NEXTVAL, 2, 1, 1.5); -- João tem 1.5 ETH

INSERT INTO T_INVESTIMENTO (id_investimento, id_moeda, id_carteira, quantidade_moeda) 
VALUES (SEQ_INVESTIMENTO.NEXTVAL, 1, 2, 0.05); -- Maria tem 0.05 BTC

INSERT INTO T_INVESTIMENTO (id_investimento, id_moeda, id_carteira, quantidade_moeda) 
VALUES (SEQ_INVESTIMENTO.NEXTVAL, 3, 2, 100.0); -- Maria tem 100 SOL

INSERT INTO T_INVESTIMENTO (id_investimento, id_moeda, id_carteira, quantidade_moeda) 
VALUES (SEQ_INVESTIMENTO.NEXTVAL, 2, 3, 10.0); -- TechCorp tem 10 ETH

INSERT INTO T_INVESTIMENTO (id_investimento, id_moeda, id_carteira, quantidade_moeda) 
VALUES (SEQ_INVESTIMENTO.NEXTVAL, 4, 4, 5000.0); -- Pedro tem 5000 ADA

-- Insert Preferencias for all users
INSERT INTO T_PREFERENCIAS (id_preferencias, id_usuario, tema, idioma, receber_notificacoes) 
VALUES (SEQ_PREFERENCIAS.NEXTVAL, 1, 'light', 'pt-BR', 1);

INSERT INTO T_PREFERENCIAS (id_preferencias, id_usuario, tema, idioma, receber_notificacoes) 
VALUES (SEQ_PREFERENCIAS.NEXTVAL, 2, 'dark', 'pt-BR', 1);

INSERT INTO T_PREFERENCIAS (id_preferencias, id_usuario, tema, idioma, receber_notificacoes) 
VALUES (SEQ_PREFERENCIAS.NEXTVAL, 3, 'light', 'en-US', 0);

INSERT INTO T_PREFERENCIAS (id_preferencias, id_usuario, tema, idioma, receber_notificacoes) 
VALUES (SEQ_PREFERENCIAS.NEXTVAL, 4, 'dark', 'pt-BR', 1);

INSERT INTO T_PREFERENCIAS (id_preferencias, id_usuario, tema, idioma, receber_notificacoes) 
VALUES (SEQ_PREFERENCIAS.NEXTVAL, 5, 'light', 'en-US', 1);

-- Insert some sample Transacoes
INSERT INTO T_TRANSACAO (id_transacao, id_carteira, id_investimento, id_moeda, tipo_operacao, valor_total_transacao, valor_liquido_transacao, valor_taxa_transacao, quantidade_moeda, status_transacao) 
VALUES (SEQ_TRANSACAO.NEXTVAL, 1, 1, 1, 'COMPRA', 10200.00, 10000.00, 200.00, 0.02, 'CONCLUIDA');

INSERT INTO T_TRANSACAO (id_transacao, id_carteira, id_investimento, id_moeda, tipo_operacao, valor_total_transacao, valor_liquido_transacao, valor_taxa_transacao, quantidade_moeda, status_transacao) 
VALUES (SEQ_TRANSACAO.NEXTVAL, 1, 2, 2, 'COMPRA', 22950.00, 22500.00, 450.00, 1.5, 'CONCLUIDA');

INSERT INTO T_TRANSACAO (id_transacao, id_carteira, id_investimento, id_moeda, tipo_operacao, valor_total_transacao, valor_liquido_transacao, valor_taxa_transacao, quantidade_moeda, status_transacao) 
VALUES (SEQ_TRANSACAO.NEXTVAL, 2, 3, 1, 'COMPRA', 25500.00, 25000.00, 500.00, 0.05, 'CONCLUIDA');

INSERT INTO T_TRANSACAO (id_transacao, id_carteira, id_investimento, id_moeda, tipo_operacao, valor_total_transacao, valor_liquido_transacao, valor_taxa_transacao, quantidade_moeda, status_transacao) 
VALUES (SEQ_TRANSACAO.NEXTVAL, 2, 4, 3, 'COMPRA', 81600.00, 80000.00, 1600.00, 100.0, 'CONCLUIDA');

INSERT INTO T_TRANSACAO (id_transacao, id_carteira, id_investimento, id_moeda, tipo_operacao, valor_total_transacao, valor_liquido_transacao, valor_taxa_transacao, quantidade_moeda, status_transacao) 
VALUES (SEQ_TRANSACAO.NEXTVAL, 4, 6, 4, 'VENDA', 12000.00, 11880.00, 120.00, 1000.0, 'CONCLUIDA');

-- Insert some sample Transferencias
-- Insert some sample Transferencias ajustado
INSERT INTO T_TRANSFERENCIA (id_transferencia, id_carteira_remetente, id_carteira_destinatario, valor_transferencia, status_transferencia, timestamp_transferencia)
VALUES (SEQ_TRANSFERENCIA.NEXTVAL, 1, 2, 1000.00, 'CONCLUIDA', SYSTIMESTAMP);

INSERT INTO T_TRANSFERENCIA (id_transferencia, id_carteira_remetente, id_carteira_destinatario, valor_transferencia, status_transferencia, timestamp_transferencia)
VALUES (SEQ_TRANSFERENCIA.NEXTVAL, 3, 1, 5000.00, 'CONCLUIDA', SYSTIMESTAMP);

INSERT INTO T_TRANSFERENCIA (id_transferencia, id_carteira_remetente, id_carteira_destinatario, valor_transferencia, status_transferencia, timestamp_transferencia)
VALUES (SEQ_TRANSFERENCIA.NEXTVAL, 2, 4, 2500.00, 'PENDENTE', SYSTIMESTAMP);

INSERT INTO T_TRANSFERENCIA (id_transferencia, id_carteira_remetente, id_carteira_destinatario, valor_transferencia, status_transferencia, timestamp_transferencia)
VALUES (SEQ_TRANSFERENCIA.NEXTVAL, 5, 3, 10000.00, 'CONCLUIDA', SYSTIMESTAMP);

-- Sample UPDATE statements
UPDATE T_MOEDA SET cotacao_para_real = 520000.00 WHERE simbolo = 'BTC';
UPDATE T_CARTEIRA SET saldo_em_real = saldo_em_real + 1000.00 WHERE id_usuario = 1;
UPDATE T_USUARIO SET cidade = 'Campinas' WHERE id_usuario = 1;
UPDATE T_PREFERENCIAS SET tema = 'dark' WHERE id_usuario = 1;

-- Sample DELETE statements (commented to preserve data, uncomment if needed)
-- DELETE FROM T_TRANSACAO WHERE status_transacao = 'ERRO';
-- DELETE FROM T_TRANSFERENCIA WHERE status_transferencia = 'ERRO' AND data_transferencia < SYSDATE - 30;

-- Sample SELECT statements to verify data
-- Consultar todos os usuários
SELECT * FROM T_USUARIO;

-- Consultar Pessoas Físicas com suas carteiras
SELECT u.email, pf.nome, pf.sobrenome, c.saldo_em_real
FROM T_USUARIO u
JOIN T_PF pf ON u.id_usuario = pf.id_usuario
JOIN T_CARTEIRA c ON u.id_usuario = c.id_usuario;

-- Consultar Pessoas Jurídicas com suas carteiras
SELECT u.email, pj.nome_fantasia, pj.ramo, c.saldo_em_real
FROM T_USUARIO u
JOIN T_PJ pj ON u.id_usuario = pj.id_usuario
JOIN T_CARTEIRA c ON u.id_usuario = c.id_usuario;

-- Consultar moedas mais populares
SELECT m.nome, m.simbolo, COUNT(i.id_investimento) as total_investidores
FROM T_MOEDA m
LEFT JOIN T_INVESTIMENTO i ON m.id_moeda = i.id_moeda
GROUP BY m.nome, m.simbolo
ORDER BY total_investidores DESC;

-- Consultar histórico de transações
SELECT
    CASE WHEN u.tipo = 'PF' THEN pf.nome ELSE pj.nome_fantasia END as nome_usuario,
    t.tipo_operacao,
    m.nome as moeda,
    t.quantidade_moeda,
    t.valor_total_transacao,
    t.status_transacao,
    t.data_transacao
FROM T_TRANSACAO t
JOIN T_CARTEIRA c ON t.id_carteira = c.id_carteira
JOIN T_USUARIO u ON c.id_usuario = u.id_usuario
LEFT JOIN T_PF pf ON u.id_usuario = pf.id_usuario AND u.tipo = 'PF'
LEFT JOIN T_PJ pj ON u.id_usuario = pj.id_usuario AND u.tipo = 'PJ'
JOIN T_MOEDA m ON t.id_moeda = m.id_moeda
ORDER BY t.data_transacao DESC;

-- Consultar todas as moedas disponíveis
SELECT * FROM T_MOEDA ORDER BY nome;

-- Consultar transferências realizadas
SELECT
    t.id_transferencia,
    t.valor_transferencia,
    t.status_transferencia,
    t.data_transferencia
FROM T_TRANSFERENCIA t
ORDER BY t.data_transferencia DESC;

COMMIT;