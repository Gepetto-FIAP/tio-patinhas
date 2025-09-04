# Dicionário de Dados

Este documento contém a descrição detalhada de todas as tabelas do modelo relacional de dados do sistema CRYPTONKS.

## Tabela: USUARIO

| Atributo | Tipo | Restrições | Descrição |
|----------|------|------------|-----------|
| id | INT | PK, NOT NULL, AUTO_INCREMENT | Identificador único do usuário |
| email | VARCHAR(100) | NOT NULL, UNIQUE | Email do usuário (usado para login) |
| senha | VARCHAR(255) | NOT NULL | Senha do usuário (deve ser armazenada com criptografia) |
| pais | VARCHAR(50) | NOT NULL | País de residência do usuário |
| estado | VARCHAR(50) | NOT NULL | Estado de residência do usuário |
| cidade | VARCHAR(50) | NOT NULL | Cidade de residência do usuário |
| bairro | VARCHAR(50) | NOT NULL | Bairro de residência do usuário |
| rua | VARCHAR(100) | NOT NULL | Rua de residência do usuário |
| numero | VARCHAR(10) | NOT NULL | Número do endereço do usuário |
| taxa_transacao | DECIMAL(5,4) | NOT NULL | Taxa de transação aplicada ao usuário (em percentual) |

## Tabela: PESSOA_FISICA

| Atributo | Tipo | Restrições | Descrição |
|----------|------|------------|-----------|
| id | INT | PK, NOT NULL, AUTO_INCREMENT | Identificador único da pessoa física |
| usuario_id | INT | FK, NOT NULL | Referência ao ID do usuário (USUARIO.id) |
| cpf | VARCHAR(14) | UNIQUE | CPF da pessoa física |
| genero | VARCHAR(20) | | Gênero da pessoa física |
| idade | INT | | Idade da pessoa física |
| nome | VARCHAR(50) | NOT NULL | Nome da pessoa física |
| sobrenome | VARCHAR(50) | NOT NULL | Sobrenome da pessoa física |

## Tabela: PESSOA_JURIDICA

| Atributo | Tipo | Restrições | Descrição |
|----------|------|------------|-----------|
| id | INT | PK, NOT NULL, AUTO_INCREMENT | Identificador único da pessoa jurídica |
| usuario_id | INT | FK, NOT NULL | Referência ao ID do usuário (USUARIO.id) |
| cnpj | VARCHAR(18) | UNIQUE | CNPJ da pessoa jurídica |
| ramo | VARCHAR(50) | | Ramo de atividade da pessoa jurídica |
| nome_fantasia | VARCHAR(100) | NOT NULL | Nome fantasia da pessoa jurídica |

## Tabela: CARTEIRA

| Atributo | Tipo | Restrições | Descrição |
|----------|------|------------|-----------|
| id | INT | PK, NOT NULL, AUTO_INCREMENT | Identificador único da carteira |
| usuario_id | INT | FK, NOT NULL | Referência ao ID do usuário (USUARIO.id) |
| saldo_em_real | DECIMAL(15,2) | NOT NULL, DEFAULT 0 | Saldo em reais disponível na carteira |

## Tabela: MOEDA

| Atributo | Tipo | Restrições | Descrição |
|----------|------|------------|-----------|
| id | INT | PK, NOT NULL, AUTO_INCREMENT | Identificador único da moeda |
| nome | VARCHAR(50) | NOT NULL | Nome da moeda (ex: Bitcoin) |
| simbolo | VARCHAR(10) | NOT NULL, UNIQUE | Símbolo da moeda (ex: BTC) |
| total_disponivel | DECIMAL(20,8) | NOT NULL | Quantidade total disponível da moeda |
| cotacao_para_real | DECIMAL(20,2) | NOT NULL | Valor da moeda em reais |

## Tabela: INVESTIMENTO

| Atributo | Tipo | Restrições | Descrição |
|----------|------|------------|-----------|
| id | INT | PK, NOT NULL, AUTO_INCREMENT | Identificador único do investimento |
| carteira_id | INT | FK, NOT NULL | Referência ao ID da carteira (CARTEIRA.id) |
| moeda_id | INT | FK, NOT NULL | Referência ao ID da moeda (MOEDA.id) |
| quantidade_moeda | DECIMAL(20,8) | NOT NULL, DEFAULT 0 | Quantidade da moeda que o usuário possui |

## Tabela: TRANSACAO

| Atributo | Tipo | Restrições | Descrição |
|----------|------|------------|-----------|
| id | INT | PK, NOT NULL, AUTO_INCREMENT | Identificador único da transação |
| investimento_id | INT | FK, NOT NULL | Referência ao ID do investimento (INVESTIMENTO.id) |
| moeda_id | INT | FK, NOT NULL | Referência ao ID da moeda (MOEDA.id) |
| tipo_operacao | ENUM('COMPRA', 'VENDA') | NOT NULL | Tipo da operação (compra ou venda) |
| valor_total_transacao | DECIMAL(15,2) | NOT NULL | Valor total da transação em reais |
| valor_liquido_transacao | DECIMAL(15,2) | NOT NULL | Valor líquido da transação em reais (sem taxas) |
| valor_taxa_transacao | DECIMAL(15,2) | NOT NULL | Valor da taxa da transação em reais |
| quantidade_moeda | DECIMAL(20,8) | NOT NULL | Quantidade da moeda envolvida na transação |
| status | ENUM('ERRO', 'PENDENTE', 'CONCLUIDA') | NOT NULL | Status atual da transação |
| data | DATE | NOT NULL | Data da transação |
| hora | TIME | NOT NULL | Hora da transação |

## Tabela: TRANSFERENCIA

| Atributo | Tipo | Restrições | Descrição |
|----------|------|------------|-----------|
| id | INT | PK, NOT NULL, AUTO_INCREMENT | Identificador único da transferência |
| carteira_remetente_id | INT | FK, NOT NULL | Referência ao ID da carteira remetente (CARTEIRA.id) |
| carteira_destinatario_id | INT | FK, NOT NULL | Referência ao ID da carteira destinatária (CARTEIRA.id) |
| valor_transferencia | DECIMAL(15,2) | NOT NULL | Valor da transferência em reais |
| status | ENUM('ERRO', 'PENDENTE', 'CONCLUIDA') | NOT NULL | Status atual da transferência |
| data | DATE | NOT NULL | Data da transferência |
| hora | TIME | NOT NULL | Hora da transferência |

## Tabela: PREFERENCIAS

| Atributo | Tipo | Restrições | Descrição |
|----------|------|------------|-----------|
| id | INT | PK, NOT NULL, AUTO_INCREMENT | Identificador único das preferências |
| usuario_id | INT | FK, NOT NULL | Referência ao ID do usuário (USUARIO.id) |
| tema | VARCHAR(20) | NOT NULL, DEFAULT 'light' | Tema da interface (ex: light, dark) |
| idioma | VARCHAR(10) | NOT NULL, DEFAULT 'pt-BR' | Idioma da interface (ex: pt-BR, en-US) |
| receber_notificacoes | BOOLEAN | NOT NULL, DEFAULT TRUE | Indica se o usuário deseja receber notificações |
