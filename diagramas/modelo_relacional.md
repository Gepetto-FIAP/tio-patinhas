# Modelo Relacional de Dados (MER)

## Diagrama ER

```mermaid
erDiagram
    USUARIO {
        int id PK
        string email
        string senha
        string pais
        string estado
        string cidade
        string bairro
        string rua
        string numero
        double taxa_transacao
    }
    
    PESSOA_FISICA {
        int id PK
        int usuario_id FK
        string cpf
        string genero
        int idade
        string nome
        string sobrenome
    }
    
    PESSOA_JURIDICA {
        int id PK
        int usuario_id FK
        string cnpj
        string ramo
        string nome_fantasia
    }
    
    CARTEIRA {
        int id PK
        int usuario_id FK
        double saldo_em_real
    }
    
    MOEDA {
        int id PK
        string nome
        string simbolo
        double total_disponivel
        double cotacao_para_real
    }
    
    INVESTIMENTO {
        int id PK
        int carteira_id FK
        int moeda_id FK
        double quantidade_moeda
    }
    
    TRANSACAO {
        int id PK
        int investimento_id FK
        int moeda_id FK
        string tipo_operacao
        double valor_total_transacao
        double valor_liquido_transacao
        double valor_taxa_transacao
        double quantidade_moeda
        string status
        string data
        string hora
    }
    
    TRANSFERENCIA {
        int id PK
        int carteira_remetente_id FK
        int carteira_destinatario_id FK
        double valor_transferencia
        string status
        string data
        string hora
    }
    
    PREFERENCIAS {
        int id PK
        int usuario_id FK
        string tema
        string idioma
        boolean receber_notificacoes
    }
    
    USUARIO ||--o{ PESSOA_FISICA : "é"
    USUARIO ||--o{ PESSOA_JURIDICA : "é"
    USUARIO ||--|| CARTEIRA : "possui"
    USUARIO ||--|| PREFERENCIAS : "possui"
    CARTEIRA ||--o{ INVESTIMENTO : "possui"
    INVESTIMENTO }o--|| MOEDA : "referencia"
    INVESTIMENTO ||--o{ TRANSACAO : "possui"
    TRANSACAO }o--|| MOEDA : "envolve"
    CARTEIRA ||--o{ TRANSFERENCIA : "envia"
    CARTEIRA ||--o{ TRANSFERENCIA : "recebe"
```

## Normalização

### Primeira Forma Normal (1FN)
- Todas as tabelas possuem uma chave primária (PK)
- Todos os atributos são atômicos (não há valores multivalorados)
- Não há grupos repetitivos

### Segunda Forma Normal (2FN)
- Todas as tabelas estão na 1FN
- Todos os atributos não-chave dependem totalmente da chave primária
- Foram criadas tabelas separadas para PESSOA_FISICA e PESSOA_JURIDICA para evitar dependências parciais

### Terceira Forma Normal (3FN)
- Todas as tabelas estão na 2FN
- Não há dependências transitivas (atributos não-chave que dependem de outros atributos não-chave)
- Tabelas como INVESTIMENTO, TRANSACAO e TRANSFERENCIA foram normalizadas para evitar dependências transitivas

## Relacionamentos
- Um USUARIO pode ser uma PESSOA_FISICA ou uma PESSOA_JURIDICA (herança)
- Um USUARIO possui uma CARTEIRA
- Um USUARIO possui PREFERENCIAS
- Uma CARTEIRA pode ter vários INVESTIMENTOS
- Um INVESTIMENTO referencia uma MOEDA
- Um INVESTIMENTO pode ter várias TRANSACOES
- Uma TRANSACAO envolve uma MOEDA
- Uma CARTEIRA pode enviar ou receber várias TRANSFERENCIAS
