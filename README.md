# Voltz - Missão Tio Patinhas - Database Integration

## Sprint 6 - Implementação Completa

Este projeto implementa integração completa com banco de dados Oracle FIAP conforme os requisitos da Sprint 6, com todas as operações CRUD implementadas para todas as tabelas.

## Estrutura do Projeto

### Scripts SQL
- `scripts/DDL.sql` - Comandos para criação das tabelas e restrições
- `scripts/DML.sql` - Comandos para população das tabelas com dados de exemplo

### Classes Java - DAOs Implementados
- `br.com.fiap.factory.ConnectionFactory` - Gerenciamento de conexões com banco Oracle FIAP
- `br.com.fiap.dao.UsuarioDAO` - **CRUD completo para usuários (PF/PJ)**
- `br.com.fiap.dao.CarteiraDAO` - **CRUD completo para carteiras**
- `br.com.fiap.dao.MoedaDAO` - **CRUD completo para moedas**
- `br.com.fiap.dao.InvestimentoDAO` - **CRUD completo para investimentos**
- `br.com.fiap.dao.TransacaoDAO` - **CRUD completo para transações**
- `br.com.fiap.dao.TransferenciaDAO` - **CRUD completo para transferências**
- `br.com.fiap.dao.PreferenciasDAO` - **CRUD completo para preferências**

## Funcionalidades Implementadas

### 🎯 Todos os DAOs com CRUD Completo

#### UsuarioDAO - Gestão de Usuários
- **Inserir**: Criar usuários PF/PJ com validações
- **Buscar**: Por ID, listar todos, ordenados por nome
- **Atualizar**: Dados pessoais e endereço
- **Excluir**: Remoção com integridade referencial
- **Collections**: Agrupamento por tipo, contagem por país, filtros por estado

#### CarteiraDAO - Gestão de Carteiras
- **Inserir**: Criação automática para novos usuários
- **Buscar**: Por ID, por usuário, por faixa de saldo
- **Atualizar**: Saldo, depósitos, saques
- **Excluir**: Remoção por usuário
- **Relatórios**: Saldo total, carteiras com saldo alto

#### MoedaDAO - Gestão de Moedas
- **Inserir**: Adicionar novas moedas
- **Buscar**: Por ID, símbolo, nome, listar todas
- **Atualizar**: Dados completos ou apenas cotação
- **Excluir**: Remoção por ID
- **Collections**: Mapeamento por símbolo, ordenação por cotação, contagem por nome

#### InvestimentoDAO - Gestão de Investimentos
- **Inserir**: Criar investimentos em moedas
- **Buscar**: Por ID, carteira, moeda, listar todos
- **Atualizar**: Quantidade de moedas
- **Excluir**: Por ID ou por carteira
- **Relatórios**: Valor total dos investimentos

#### TransacaoDAO - Gestão de Transações
- **Inserir**: Registrar compras/vendas
- **Buscar**: Por ID, carteira, moeda, status, tipo, período
- **Atualizar**: Dados completos ou apenas status
- **Excluir**: Remoção por ID
- **Collections**: Agrupamento por status, ordenação por valor, contagem por tipo
- **Relatórios**: Volume total, transações por valor mínimo

#### TransferenciaDAO - Gestão de Transferências
- **Inserir**: Registrar transferências entre carteiras
- **Buscar**: Por ID, remetente, destinatário, status
- **Atualizar**: Status das transferências
- **Excluir**: Remoção por ID
- **Relatórios**: Volume total, contagem de transferências

#### PreferenciasDAO - Gestão de Preferências
- **Inserir**: Configurar preferências de usuário
- **Buscar**: Por ID, usuário, tema, idioma, notificações
- **Atualizar**: Tema, idioma, notificações individualmente
- **Excluir**: Por ID ou por usuário
- **Relatórios**: Contagem por tema, filtros por configuração

## Como Usar

### 1. Configurar Banco de Dados
1. Execute o script `scripts/DDL.sql` para criar as tabelas
2. Execute o script `scripts/DML.sql` para popular com dados de exemplo

### 2. Testar Funcionalidades
1. Execute a classe `Main`
2. **Opções de Teste Disponíveis:**
   - **Opção 10**: Testar Moedas (CRUD completo)
   - **Opção 11**: Testar Transações (CRUD completo)
   - **Opção 12**: Testar Transferências (CRUD completo)
   - **Opção 13**: Testar Investimentos (CRUD completo)
   - **Opção 14**: Testar Preferências (CRUD completo)
   - **Opção 15**: Testar Carteiras (CRUD completo)
   - **Opção 16**: Testar Usuários (CRUD completo)
   - **🎯 Opção 17**: **TESTE COMPLETO DE INTEGRAÇÃO** (Todas as tabelas)
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

## Requisitos da Sprint 6 - 100% Atendidos

### ✅ **Todas as classes Java em arquivos separados**
- ✅ Cada classe em arquivo próprio
- ✅ Estrutura organizada em packages

### ✅ **Scripts SQL completos**
- ✅ DDL.sql com criação de todas as tabelas
- ✅ DML.sql com dados de exemplo
- ✅ Sequences, constraints, indexes implementados

### ✅ **Camada de conexão com Oracle FIAP**
- ✅ ConnectionFactory configurado para Oracle FIAP
- ✅ Credenciais e URL corretas

### ✅ **Camada DAO completa**
- ✅ **7 DAOs** implementados com CRUD completo
- ✅ UsuarioDAO, CarteiraDAO, MoedaDAO, InvestimentoDAO, TransacaoDAO, TransferenciaDAO, PreferenciasDAO
- ✅ Todos com operações INSERT, UPDATE, DELETE, SELECT

### ✅ **Operações CRUD em todas as tabelas**
- ✅ INSERT: Todos os DAOs
- ✅ UPDATE: Todos os DAOs  
- ✅ DELETE: Todos os DAOs
- ✅ SELECT: Todos os DAOs com múltiplas variações

### ✅ **Uso de Collections**
- ✅ ArrayList para listagens
- ✅ HashMap para mapeamentos
- ✅ Collections.sort() para ordenações
- ✅ Métodos de agrupamento e filtragem

### ✅ **Classe Main para testes**
- ✅ Menu expandido com 17 opções
- ✅ Testes individuais para cada tabela
- ✅ Teste completo de integração
- ✅ Demonstração de todas as operações

### ✅ **Integração completa com banco Oracle**
- ✅ Try-with-resources implementado
- ✅ Gerenciamento adequado de conexões
- ✅ Tratamento de exceções
- ✅ Logs informativos  

## 🚀 Como Executar o Sistema

### 1. Configurar Ambiente
```bash
# Compilar o projeto
mvn compile

# Executar a aplicação
mvn exec:java -Dexec.mainClass="br.com.fiap.Main"
```

### 2. Executar Scripts SQL
```sql
-- 1. Execute scripts/DDL.sql para criar as tabelas
-- 2. Execute scripts/DML.sql para popular com dados de exemplo
```

### 3. Testar Integração
- Execute a classe Main
- Selecione a **Opção 17** para teste completo de integração
- Todas as operações CRUD serão testadas automaticamente

## 📊 Estatísticas do Projeto

- **7 DAOs** implementados com CRUD completo
- **17 métodos de teste** na classe Main
- **50+ métodos** de operações de banco
- **Collections** implementadas (ArrayList, HashMap)
- **Try-with-resources** em todos os métodos
- **Integração completa** com Oracle FIAP

## Observações Técnicas

- ✅ Todas as operações de banco são feitas com PreparedStatement para segurança
- ✅ Tratamento adequado de exceções SQLException
- ✅ Try-with-resources implementado em todos os métodos
- ✅ Conexões sempre fechadas automaticamente
- ✅ Logs informativos para acompanhar operações
- ✅ Validações de dados com CHECK constraints no banco
- ✅ Uso adequado de Collections (ArrayList, HashMap)
- ✅ Métodos de agrupamento, ordenação e filtragem
- ✅ Integridade referencial mantida em todas as operações

## 🎯 Status do Projeto

**✅ SPRINT 6 - 100% COMPLETA**

O projeto está totalmente pronto para entrega, atendendo a todos os requisitos da Sprint 6 com implementação completa de integração Java + Oracle Database.
