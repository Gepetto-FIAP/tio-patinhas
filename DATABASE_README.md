# Voltz - Missão Tio Patinhas - Database Integration

## Fase 6 - Implementação Completa

Este projeto implementa integração completa com banco de dados Oracle FIAP conforme os requisitos da Fase 6, com todas as camadas DAO implementadas e testadas.

---

## 📁 Estrutura do Projeto

### Scripts SQL
- `scripts/DDL.sql` - Comandos para criação das tabelas, sequences, índices e restrições
- `scripts/DML.sql` - Comandos para população das tabelas com dados de exemplo e consultas SELECT

### Classes Java

#### 🔗 Conexão
- **`br.com.fiap.factory.ConnectionFactory`** - Gerenciamento de conexões com banco Oracle FIAP

#### 📊 Camada DAO (Data Access Object)
Todos os DAOs implementam operações CRUD completas (INSERT, UPDATE, DELETE, SELECT):

1. **`br.com.fiap.dao.UsuarioDAO`** - Operações para T_USUARIO, T_PF e T_PJ
2. **`br.com.fiap.dao.CarteiraDAO`** - Operações para T_CARTEIRA
3. **`br.com.fiap.dao.MoedaDAO`** - Operações para T_MOEDA
4. **`br.com.fiap.dao.InvestimentoDAO`** - Operações para T_INVESTIMENTO
5. **`br.com.fiap.dao.TransacaoDAO`** - Operações para T_TRANSACAO
6. **`br.com.fiap.dao.TransferenciaDAO`** - Operações para T_TRANSFERENCIA
7. **`br.com.fiap.dao.PreferenciasDAO`** - Operações para T_PREFERENCIAS

#### 🎯 Camada Model
- Classes de modelo para todas as entidades do sistema
- Getters e setters implementados para integração JDBC

---

## ✅ Funcionalidades Implementadas

### 1. MoedaDAO - CRUD Completo
#### Operações Disponíveis
- ✅ **Inserir**: Adicionar nova moeda ao banco
- ✅ **Alterar**: Atualizar dados da moeda e cotações
- ✅ **Excluir**: Remover moeda do banco
- ✅ **Exibir**: Listar e buscar moedas

#### Métodos
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

---

### 2. UsuarioDAO - CRUD Completo
#### Operações Disponíveis
- ✅ **Inserir**: Criar novo usuário (PF ou PJ)
- ✅ **Alterar**: Atualizar dados do usuário
- ✅ **Excluir**: Remover usuário e dados relacionados
- ✅ **Exibir**: Listar e buscar usuários

#### Métodos
- `inserir(Usuario)` - Insere novo usuário (PF ou PJ) com transação
- `buscarPorId(int)` - Busca usuário completo por ID
- `listarTodos()` - Lista todos os usuários
- `atualizar(Usuario)` - Atualiza dados do usuário
- `deletar(int)` - Remove usuário com cascade

---

### 3. CarteiraDAO - CRUD Completo
#### Operações Disponíveis
- ✅ **Inserir**: Criar nova carteira
- ✅ **Alterar**: Atualizar saldo e dados
- ✅ **Excluir**: Remover carteira
- ✅ **Exibir**: Listar e buscar carteiras

#### Métodos
- `inserir(Carteira)` - Insere nova carteira
- `buscarPorId(int)` - Busca carteira por ID
- `buscarPorUsuario(int)` - Busca carteira do usuário
- `listarTodas()` - Lista todas as carteiras
- `atualizarSaldo(int, double)` - Atualiza saldo
- `depositar(int, double)` - Adiciona valor
- `sacar(int, double)` - Remove valor
- `deletar(int)` - Remove carteira

---

### 4. InvestimentoDAO - CRUD Completo
#### Operações Disponíveis
- ✅ **Inserir**: Registrar novo investimento
- ✅ **Alterar**: Atualizar quantidade de moedas
- ✅ **Excluir**: Remover investimento
- ✅ **Exibir**: Listar e buscar investimentos

#### Métodos
- `inserir(Investimento)` - Registra novo investimento
- `buscarPorId(int)` - Busca investimento por ID
- `buscarPorCarteira(int)` - Lista investimentos da carteira
- `listarTodos()` - Lista todos os investimentos
- `atualizar(int, double)` - Atualiza quantidade de moedas
- `excluir(int)` - Remove investimento
- `buscarPorMoedaECarteira(int, int)` - Busca investimento específico
- `contarInvestimentos()` - Conta total de investimentos

---

### 5. TransacaoDAO - CRUD Completo
#### Operações Disponíveis
- ✅ **Inserir**: Registrar nova transação
- ✅ **Alterar**: Atualizar status da transação
- ✅ **Excluir**: Remover transação
- ✅ **Exibir**: Listar e buscar transações

#### Métodos
- `inserir(Transacao)` - Registra nova transação
- `buscarPorId(int)` - Busca transação por ID
- `buscarPorCarteira(int)` - Lista transações da carteira
- `listarTodas()` - Lista todas as transações
- `atualizar(int, Status)` - Atualiza status
- `excluir(int)` - Remove transação
- `contarTransacoes()` - Conta total de transações
- `buscarPorStatus(Status)` - Filtra por status

---

### 6. TransferenciaDAO - CRUD Completo
#### Operações Disponíveis
- ✅ **Inserir**: Registrar nova transferência
- ✅ **Alterar**: Atualizar status da transferência
- ✅ **Excluir**: Remover transferência
- ✅ **Exibir**: Listar e buscar transferências

#### Métodos
- `inserir(Transferencia)` - Registra nova transferência
- `atualizar(Transferencia)` - Atualiza status
- `consultar(int, int)` - Busca transferência específica
- `excluir(int)` - Remove transferência
- `listarTodas()` - Lista todas as transferências

---

### 7. PreferenciasDAO - CRUD Completo
#### Operações Disponíveis
- ✅ **Inserir**: Criar preferências do usuário
- ✅ **Alterar**: Atualizar preferências
- ✅ **Excluir**: Remover preferências
- ✅ **Exibir**: Listar e buscar preferências

#### Métodos
- `inserir(Preferencias)` - Cria preferências
- `buscarPorId(int)` - Busca por ID
- `buscarPorUsuario(int)` - Busca preferências do usuário
- `listarTodas()` - Lista todas as preferências
- `atualizar(Preferencias, int)` - Atualiza preferências
- `atualizarPorUsuario(Preferencias, int)` - Atualiza por usuário
- `excluir(int)` - Remove preferências
- `contarPreferencias()` - Conta total
- `buscarPorTema(String)` - Filtra por tema

---

## 🗄️ Estrutura das Tabelas

### Tabelas Principais

1. **T_USUARIO** - Dados básicos dos usuários
   - Campos: id_usuario, tipo (PF/PJ), email, senha, endereço completo

2. **T_PF** - Dados específicos de Pessoa Física
   - Campos: id_usuario, cpf, genero, idade, nome, sobrenome

3. **T_PJ** - Dados específicos de Pessoa Jurídica
   - Campos: id_usuario, cnpj, ramo, nome_fantasia

4. **T_CARTEIRA** - Carteiras dos usuários
   - Campos: id_carteira, saldo_em_real, id_usuario

5. **T_MOEDA** - Moedas disponíveis
   - Campos: id_moeda, nome, simbolo, cotacao_para_real

6. **T_INVESTIMENTO** - Investimentos dos usuários
   - Campos: id_investimento, id_moeda, id_carteira, quantidade_moeda

7. **T_TRANSACAO** - Histórico de transações
   - Campos: id_transacao, id_carteira, id_investimento, id_moeda, tipo_operacao, valores, quantidade, status, data/hora

8. **T_TRANSFERENCIA** - Transferências entre usuários
   - Campos: id_transferencia, id_carteira_remetente, id_carteira_destinatario, valor, status, data/hora

9. **T_PREFERENCIAS** - Preferências dos usuários
   - Campos: id_preferencias, id_usuario, tema, idioma, receber_notificacoes

---

## 🚀 Como Usar

### 1. Configurar Banco de Dados
```sql
-- 1. Execute o script DDL para criar as tabelas
@scripts/DDL.sql

-- 2. Execute o script DML para popular com dados de exemplo
@scripts/DML.sql
```

### 2. Testar Funcionalidades

#### Executar a aplicação:
```bash
mvn compile
mvn exec:java -Dexec.mainClass="br.com.fiap.Main"
```

#### Opções do Menu de Testes:
- **Opção 10** - Testar MoedaDAO (CRUD completo)
- **Opção 11** - Testar InvestimentoDAO (listagem e contagem)
- **Opção 12** - Testar TransacaoDAO (listagem por status)
- **Opção 13** - Testar PreferenciasDAO (listagem e filtros)
- **Opção 14** - Testar TransferenciaDAO (listagem)
- **Opção 15** - Testar CarteiraDAO (listagem completa)

Cada teste demonstra as operações CRUD em ação, validando a integração completa com o banco de dados.

---

## 📋 Requisitos Atendidos - Fase 6

### ✅ Checklist de Implementação

| Requisito | Status | Descrição |
|-----------|--------|-----------|
| **Classes JDBC** | ✅ | 7 DAOs implementados com JDBC |
| **ConnectionFactory** | ✅ | Gerenciamento de conexões com Oracle FIAP |
| **Camada DAO** | ✅ | Todas as tabelas possuem DAOs |
| **INSERT** | ✅ | Implementado em todos os DAOs |
| **UPDATE** | ✅ | Implementado em todos os DAOs |
| **DELETE** | ✅ | Implementado em todos os DAOs |
| **SELECT** | ✅ | Múltiplos métodos de consulta |
| **Collections** | ✅ | ArrayList usado extensivamente |
| **Main com Testes** | ✅ | 6 métodos de teste implementados |
| **PreparedStatement** | ✅ | Usado em todas as operações |
| **Tratamento de Exceções** | ✅ | Try-catch em todos os métodos |
| **Sequences** | ✅ | Usadas para geração de IDs |

---

## 🔒 Observações Técnicas

### Segurança
- ✅ Todas as operações usam **PreparedStatement** para prevenir SQL Injection
- ✅ Conexões sempre fechadas após uso (try-with-resources)
- ✅ Senhas armazenadas no banco (em produção, use hash)

### Integridade de Dados
- ✅ Foreign Keys garantem integridade referencial
- ✅ CHECK constraints validam valores
- ✅ UNIQUE constraints evitam duplicatas
- ✅ CASCADE DELETE remove dados relacionados

### Performance
- ✅ Índices criados em colunas frequentemente consultadas
- ✅ Sequences para geração eficiente de IDs
- ✅ Queries otimizadas com JOINs apropriados

### Logs e Debugging
- ✅ Mensagens informativas em operações bem-sucedidas
- ✅ Warnings quando registros não são encontrados
- ✅ Stack traces completos em caso de erros

---

## 📦 Dependências

### Maven (pom.xml)
```xml
<dependency>
    <groupId>com.oracle.database.jdbc</groupId>
    <artifactId>ojdbc8</artifactId>
    <version>23.2.0.0</version>
</dependency>
```

---

## 👥 Equipe

Projeto desenvolvido para a disciplina de Compliance, Quality Assurance & Tests da FIAP.

**Fase 6 - Integração JDBC com Oracle Database**

---

## 📝 Notas da Entrega

### Diferenciais Implementados:
1. **CRUD Completo em TODOS os DAOs** (não apenas em um)
2. **Múltiplos métodos de consulta** em cada DAO
3. **Menu interativo** com testes para todas as camadas
4. **Tratamento robusto de exceções**
5. **Código bem documentado** e organizado
6. **Uso extensivo de Collections** (ArrayList, List)
7. **Integração completa** entre todas as camadas

### Execução dos Testes:
Todos os métodos de teste podem ser executados diretamente pelo menu da aplicação, demonstrando:
- Inserção de dados
- Consultas diversas (por ID, por atributos, listagem completa)
- Atualizações
- Contagens e validações
- Operações específicas de cada entidade

---

**Data de Conclusão:** 2025
**Versão:** 1.0-SNAPSHOT
