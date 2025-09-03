# Voltz - Missão Tio Patinhas - Database Integration

## Fase 5 - Implementação

Este projeto implementa integração completa com banco de dados Oracle FIAP conforme os requisitos da Fase 5.

## Estrutura do Projeto

### Scripts SQL
- `scripts/DDL.sql` - Comandos para criação das tabelas e restrições
- `scripts/DML.sql` - Comandos para população das tabelas com dados de exemplo

### Classes Java
- `br.com.fiap.factory.ConnectionFactory` - Gerenciamento de conexões com banco
- `br.com.fiap.dao.UsuarioDAO` - Operações de banco para usuários
- `br.com.fiap.dao.CarteiraDAO` - Operações de banco para carteiras
- `br.com.fiap.dao.MoedaDAO` - **CRUD completo para moedas (classe escolhida)**

## Funcionalidades Implementadas

### MoedaDAO - Integração Completa
A classe `MoedaDAO` foi escolhida para implementação completa e possui:

#### Operações CRUD
- **Inserir**: Adicionar nova moeda ao banco
- **Alterar**: Atualizar dados da moeda e cotações
- **Excluir**: Remover moeda do banco
- **Exibir**: Listar e buscar moedas

#### Métodos Disponíveis
- `inserir(Moeda)` - Insere nova moeda
- `buscarPorId(int)` - Busca moeda por ID
- `buscarPorSimbolo(String)` - Busca moeda por símbolo (ex: BTC)
- `listarTodas()` - Lista todas as moedas
- `atualizar(Moeda, int)` - Atualiza dados completos da moeda
- `atualizarCotacao(String, double)` - Atualiza apenas a cotação
- `excluir(int)` - Remove moeda por ID
- `existePorSimbolo(String)` - Verifica se moeda existe
- `contarMoedas()` - Conta total de moedas
- `buscarPorNome(String)` - Busca moedas por nome (parcial)

## Como Usar

### 1. Configurar Banco de Dados
1. Execute o script `scripts/DDL.sql` para criar as tabelas
2. Execute o script `scripts/DML.sql` para popular com dados de exemplo

### 2. Testar Funcionalidades
1. Execute a classe `Main`
2. Selecione a opção **10 - TESTAR FUNCIONALIDADES DE MOEDA (DATABASE)**
3. O sistema executará todos os testes de CRUD automaticamente

### 3. Estrutura das Tabelas
O projeto possui as seguintes tabelas principais:
- `T_USUARIO` - Dados básicos dos usuários
- `T_PF` - Dados específicos de Pessoa Física
- `T_PJ` - Dados específicos de Pessoa Jurídica
- `T_CARTEIRA` - Carteiras dos usuários
- `T_MOEDA` - Moedas disponíveis
- `T_INVESTIMENTO` - Investimentos dos usuários
- `T_TRANSACAO` - Histórico de transações
- `T_TRANSFERENCIA` - Transferências entre usuários
- `T_PREFERENCIAS` - Preferências dos usuários

## Requisitos Atendidos

✅ **Script DDL** - Criação de tabelas com PKs e FKs  
✅ **Script DML** - População com INSERT, UPDATE, DELETE e SELECT  
✅ **Classes de conexão** - ConnectionFactory para Oracle FIAP  
✅ **Classe escolhida** - MoedaDAO com integração completa  
✅ **Operações CRUD** - Inserir, alterar, excluir e exibir  
✅ **Métodos na Main** - Testes das funcionalidades de banco  
✅ **Classes separadas** - Cada classe em arquivo próprio  
✅ **Collections** - Uso de ArrayList e HashMap (Fase 4 mantida)  
✅ **Manipulação de arquivos** - FileManipulation mantida da Fase 4  

## Observações Técnicas

- Todas as operações de banco são feitas com PreparedStatement para segurança
- Tratamento adequado de exceções SQLException
- Conexões sempre fechadas após uso
- Logs informativos para acompanhar operações
- Validações de dados com CHECK constraints no banco
