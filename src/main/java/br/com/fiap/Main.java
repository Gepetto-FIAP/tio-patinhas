
package br.com.fiap;

import br.com.fiap.util.FileManipulation;
import br.com.fiap.model.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import br.com.fiap.dao.*;

public class Main {
    private static ArrayList<Moeda> moedas = new ArrayList<>();

    public static void exibirUsuarios() {
        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            List<Usuario> usuarios = usuarioDAO.listarTodos();

            System.out.println("=== LISTA DE USUÁRIOS ===");
            for (Usuario u : usuarios) {
                System.out.println("ID: " + u.getId());
                System.out.println("Nome: " + u.getNome());
                System.out.println("Saldo da Carteira: R$ " + u.getCarteira().getSaldo() );
                System.out.println("-------------------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void exibirMenuPrincipal() {
        System.out.println("\n[*] BEM VINDO A CRYPTONKS, SELECIONE UM USUARIO PARA PROSSEGUIR");
        exibirUsuarios();
        System.out.println("5 - SAIR");
    }

    public static void exibirMenuAcoes(Usuario usuarioSelecionado) {
        if (usuarioSelecionado == null) {
            System.out.println("\n[*] SELECIONE UM USUARIO PARA PROSSEGUIR.");
        } else {
            System.out.printf("\n[*] %s, SELECIONE UMA ACAO\n", usuarioSelecionado.getNome().toUpperCase());
        }
        System.out.println("1 - SELECIONAR USUARIO");
        System.out.println("2 - CRIAR USUARIO");
        System.out.println("3 - EDITAR USUARIO");
        System.out.println("4 - REMOVER USUARIO");
        System.out.println("5 - TRANSFERIR SALDO");
        System.out.println("6 - COMPRAR MOEDA");
        System.out.println("7 - VENDER MOEDA");
        System.out.println("8 - CONSULTAR TRANSACAO (INVESTIMENTOS EM MOEDAS)");
        System.out.println("9 - CONSULTAR TRANSFERENCIA");
        System.out.println("\n=== TESTES DE INTEGRAÇÃO COM BANCO DE DADOS ===");
        System.out.println("10 - TESTAR MOEDA DAO");
        System.out.println("11 - TESTAR INVESTIMENTO DAO");
        System.out.println("12 - TESTAR TRANSACAO DAO");
        System.out.println("13 - TESTAR PREFERENCIAS DAO");
        System.out.println("14 - TESTAR TRANSFERENCIA DAO");
        System.out.println("15 - TESTAR CARTEIRA DAO");
        System.out.println("\n0 - SAIR");
    }


    public static void exibirMenuMoedas() {
        for (int i = 0; i < moedas.size(); i++) {
            Moeda moeda = moedas.get(i);
            System.out.printf("%d - %s (%s) - Cotação: R$ %.2f\n",
                    i + 1,
                    moeda.getNome(),
                    moeda.getSimbolo(),
                    moeda.getCotacaoParaReal());
        }
    }

    public static void testarFuncionalidadesMoeda() {
        System.out.println("\n=== TESTANDO FUNCIONALIDADES DE MOEDA - DATABASE ===");

        try {
            MoedaDAO moedaDAO = new MoedaDAO();

            System.out.println("\n1. TESTANDO INSERÇÃO DE MOEDA:");
            Moeda novaMoeda = new Moeda("Litecoin", "LTC", 450.00);
            moedaDAO.inserir(novaMoeda);

            System.out.println("\n2. TESTANDO LISTAGEM DE TODAS AS MOEDAS:");
            List<Moeda> todasMoedas = moedaDAO.listarTodas();
            for (Moeda m : todasMoedas) {
                System.out.printf("- %s (%s): R$ %.2f\n", m.getNome(), m.getSimbolo(), m.getCotacaoParaReal());
            }

            System.out.println("\n3. TESTANDO BUSCA POR SÍMBOLO:");
            Moeda btcEncontrada = moedaDAO.buscarPorSimbolo("BTC");
            if (btcEncontrada != null) {
                System.out.printf("Bitcoin encontrado: %s - R$ %.2f\n",
                        btcEncontrada.getSimbolo(), btcEncontrada.getCotacaoParaReal());
            } else {
                System.out.println("Bitcoin não encontrado no banco de dados");
            }

            System.out.println("\n4. TESTANDO ATUALIZAÇÃO DE COTAÇÃO:");
            moedaDAO.atualizarCotacao("BTC", 550000.00);

            System.out.println("\n5. TESTANDO BUSCA POR NOME:");
            List<Moeda> moedasBitcoin = moedaDAO.buscarPorNome("Bitcoin");
            for (Moeda m : moedasBitcoin) {
                System.out.printf("Encontrado: %s (%s): R$ %.2f\n", m.getNome(), m.getSimbolo(), m.getCotacaoParaReal());
            }

            System.out.println("\n6. TESTANDO CONTAGEM DE MOEDAS:");
            int totalMoedas = moedaDAO.contarMoedas();
            System.out.println("Total de moedas cadastradas: " + totalMoedas);

            System.out.println("\n7. TESTANDO VERIFICAÇÃO DE EXISTÊNCIA:");
            boolean ethereumExiste = moedaDAO.existePorSimbolo("ETH");
            System.out.println("Ethereum existe no banco? " + (ethereumExiste ? "Sim" : "Não"));

            System.out.println("\n8. TESTANDO BUSCA POR ID:");
            Moeda moedaPorId = moedaDAO.buscarPorId(1);
            if (moedaPorId != null) {
                System.out.printf("Moeda ID 1: %s (%s): R$ %.2f\n",
                        moedaPorId.getNome(), moedaPorId.getSimbolo(), moedaPorId.getCotacaoParaReal());
            } else {
                System.out.println("Moeda com ID 1 não encontrada");
            }

            System.out.println("\n=== TESTE FINALIZADO ===");

        } catch (Exception e) {
            System.err.println("[ERRO] Falha ao testar funcionalidades de moeda: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void testarFuncionalidadesInvestimento() {
        System.out.println("\n=== TESTANDO FUNCIONALIDADES DE INVESTIMENTO - DATABASE ===");

        try {
            InvestimentoDAO investimentoDAO = new InvestimentoDAO();

            System.out.println("\n1. TESTANDO LISTAGEM DE TODOS OS INVESTIMENTOS:");
            List<Investimento> todos = investimentoDAO.listarTodos();
            System.out.println("Total de investimentos: " + todos.size());

            System.out.println("\n2. TESTANDO BUSCA POR CARTEIRA:");
            List<Investimento> investimentosCarteira1 = investimentoDAO.buscarPorCarteira(1);
            System.out.println("Investimentos da carteira 1: " + investimentosCarteira1.size());

            System.out.println("\n3. TESTANDO CONTAGEM:");
            int total = investimentoDAO.contarInvestimentos();
            System.out.println("Total de investimentos no sistema: " + total);

            System.out.println("\n=== TESTE FINALIZADO ===");

        } catch (Exception e) {
            System.err.println("[ERRO] Falha ao testar funcionalidades de investimento: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void testarFuncionalidadesTransacao() {
        System.out.println("\n=== TESTANDO FUNCIONALIDADES DE TRANSAÇÃO - DATABASE ===");

        try {
            TransacaoDAO transacaoDAO = new TransacaoDAO();

            System.out.println("\n1. TESTANDO LISTAGEM DE TODAS AS TRANSAÇÕES:");
            List<Transacao> todas = transacaoDAO.listarTodas();
            System.out.println("Total de transações: " + todas.size());

            System.out.println("\n2. TESTANDO BUSCA POR STATUS:");
            List<Transacao> concluidas = transacaoDAO.buscarPorStatus(Status.CONCLUIDA);
            System.out.println("Transações concluídas: " + concluidas.size());

            System.out.println("\n3. TESTANDO CONTAGEM:");
            int total = transacaoDAO.contarTransacoes();
            System.out.println("Total de transações no sistema: " + total);

            System.out.println("\n=== TESTE FINALIZADO ===");

        } catch (Exception e) {
            System.err.println("[ERRO] Falha ao testar funcionalidades de transação: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void testarFuncionalidadesPreferencias() {
        System.out.println("\n=== TESTANDO FUNCIONALIDADES DE PREFERÊNCIAS - DATABASE ===");

        try {
            PreferenciasDAO preferenciasDAO = new PreferenciasDAO();

            System.out.println("\n1. TESTANDO LISTAGEM DE TODAS AS PREFERÊNCIAS:");
            List<Preferencias> todas = preferenciasDAO.listarTodas();
            System.out.println("Total de preferências: " + todas.size());
            for (Preferencias p : todas) {
                System.out.printf("- Usuário: %s | Tema: %s | Idioma: %s | Notificações: %s\n",
                        p.getUsuario() != null ? p.getUsuario().getNome() : "N/A",
                        p.getTema(),
                        p.getIdioma(),
                        p.isReceberNotificacoes() ? "Sim" : "Não");
            }

            System.out.println("\n2. TESTANDO BUSCA POR TEMA:");
            List<Preferencias> dark = preferenciasDAO.buscarPorTema("dark");
            System.out.println("Usuários com tema dark: " + dark.size());

            System.out.println("\n3. TESTANDO CONTAGEM:");
            int total = preferenciasDAO.contarPreferencias();
            System.out.println("Total de preferências no sistema: " + total);

            System.out.println("\n=== TESTE FINALIZADO ===");

        } catch (Exception e) {
            System.err.println("[ERRO] Falha ao testar funcionalidades de preferências: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void testarFuncionalidadesTransferencia() {
        System.out.println("\n=== TESTANDO FUNCIONALIDADES DE TRANSFERÊNCIA - DATABASE ===");

        try {
            TransferenciaDAO transferenciaDAO = new TransferenciaDAO();

            System.out.println("\n1. TESTANDO LISTAGEM DE TODAS AS TRANSFERÊNCIAS:");
            List<Transferencia> todas = transferenciaDAO.listarTodas();
            System.out.println("Total de transferências: " + todas.size());

            System.out.println("\n=== TESTE FINALIZADO ===");

            transferenciaDAO.fecharConexao();

        } catch (Exception e) {
            System.err.println("[ERRO] Falha ao testar funcionalidades de transferência: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void testarFuncionalidadesCarteira() {
        System.out.println("\n=== TESTANDO FUNCIONALIDADES DE CARTEIRA - DATABASE ===");

        try {
            CarteiraDAO carteiraDAO = new CarteiraDAO();

            System.out.println("\n1. TESTANDO LISTAGEM DE TODAS AS CARTEIRAS:");
            java.util.List<Carteira> todas = carteiraDAO.listarTodas();
            System.out.println("Total de carteiras: " + todas.size());
            for (Carteira c : todas) {
                System.out.printf("- ID: %d | Saldo: R$ %.2f | Usuário: %s\n",
                        c.getId(),
                        c.getSaldo(),
                        c.getUsuario() != null ? c.getUsuario().getNome() : "N/A");
            }

            System.out.println("\n=== TESTE FINALIZADO ===");

            carteiraDAO.fecharConexao();

        } catch (Exception e) {
            System.err.println("[ERRO] Falha ao testar funcionalidades de carteira: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            Moeda btc = new Moeda(
                    "Bitcoin",
                    "BTC",
                    500000.00
            );
            moedas.add(btc);

            Moeda eth = new Moeda(
                    "Ethereum",
                    "ETH",
                    2000.00
            );
            moedas.add(eth);

            Moeda sol = new Moeda(
                    "Solana",
                    "SOL",
                    200.00
            );
            moedas.add(sol);

            // Criando HashMap para mapear símbolos de moedas para objetos Moeda
            HashMap<String, Moeda> moedasPorSimbolo = new HashMap<>();
            for (Moeda moeda : moedas) {
                moedasPorSimbolo.put(moeda.getSimbolo(), moeda);
            }

            // Salvando em arquivo usando a classe utilitária
            FileManipulation.salvarMoedas(moedasPorSimbolo);

            Usuario usuarioSelecionado = null;

            boolean running = true;

            UsuarioDAO usuarioDAO = new UsuarioDAO();

            while (running) {
                exibirMenuAcoes(usuarioSelecionado);
                int opcao = scanner.nextInt();

                switch (opcao) {
                    case 1 -> {
                        // Selecionar usuário
                        System.out.println("[*] Buscando usuários...");
                        exibirUsuarios();
                        System.out.print("[*] Digite o ID do usuário: ");
                        int id = scanner.nextInt();

                        usuarioSelecionado = usuarioDAO.buscarPorId(id);

                        if (usuarioSelecionado != null) {
                            System.out.println("[*] Usuário " + usuarioSelecionado.getNome() + " selecionado com sucesso!");
                        } else {
                            System.out.println("[ERRO] Usuário não encontrado.");
                        }
                    }
                    case 2 -> {
                        // Criar usuário
                        System.out.println("[*] Criando novo usuário...");

                        System.out.println("Digite o tipo de usuário (1 - Pessoa Física | 2 - Pessoa Jurídica): ");
                        int tipo = scanner.nextInt();
                        scanner.nextLine(); // limpar buffer

                        System.out.println("Digite o email: ");
                        String email = scanner.nextLine();

                        System.out.println("Digite a senha: ");
                        String senha = scanner.nextLine();

                        System.out.println("Digite o país: ");
                        String pais = scanner.nextLine();

                        System.out.println("Digite o estado: ");
                        String estado = scanner.nextLine();

                        System.out.println("Digite a cidade: ");
                        String cidade = scanner.nextLine();

                        System.out.println("Digite o bairro: ");
                        String bairro = scanner.nextLine();

                        System.out.println("Digite a rua: ");
                        String rua = scanner.nextLine();

                        System.out.println("Digite o número: ");
                        String numero = scanner.nextLine();

                        Usuario novoUsuario = null;

                        if (tipo == 1) {
                            // Pessoa Física
                            System.out.println("Digite o CPF: ");
                            String cpf = scanner.nextLine();

                            System.out.println("Digite o genero: ");
                            String genero = scanner.nextLine();

                            System.out.println("Digite a idade: ");
                            int idade = scanner.nextInt();
                            scanner.nextLine(); // consome o \n pendente

                            System.out.println("Digite o nome: ");
                            String nome = scanner.nextLine();

                            System.out.println("Digite o sobrenome: ");
                            String sobrenome = scanner.nextLine();

                            novoUsuario = new PessoaFisica(
                                    0,
                                    "PF", email, senha, pais, estado, cidade, bairro, rua, numero, cpf, genero, idade, nome, sobrenome
                            );

                        } else if (tipo == 2) {
                            // Pessoa Jurídica
                            System.out.println("Digite o CNPJ: ");
                            String cnpj = scanner.nextLine();

                            System.out.println("Digite o ramo: ");
                            String ramo = scanner.nextLine();

                            System.out.println("Digite a razão social: ");
                            String razaoSocial = scanner.nextLine();

                            novoUsuario = new PessoaJuridica(
                                    1,"PJ", email, senha, pais, estado, cidade, bairro, rua, numero, cnpj, ramo, razaoSocial
                            );

                        }
                        if (novoUsuario != null) {
                            try {
                                UsuarioDAO dao = new UsuarioDAO();
                                int id = dao.inserir(novoUsuario);
                                System.out.println("[✔] Usuário criado com sucesso!");
                            } catch (Exception e) {
                                System.out.println("[x] Erro ao inserir usuário: " + e.getMessage());
                            }
                        } else {
                            System.out.println("[x] Tipo de usuário inválido!");
                        }
                    }
                    case 3 -> {
                        // Editar usuário
                        System.out.println("[*] Buscando usuários...");
                        exibirUsuarios(); // Mostra todos os usuários cadastrados
                        System.out.println("[*] Digite o id do usuário a ser editado: ");
                        int id = scanner.nextInt();
                        scanner.nextLine(); // consumir quebra de linha

                        Usuario usuarioExistente = usuarioDAO.buscarPorId(id);

                        if (usuarioExistente == null) {
                            System.out.println("[!] Usuário não encontrado!");
                        } else {
                            System.out.println("[*] Editando dados do usuário ID " + id);

                            System.out.print("Novo email (atual: " + usuarioExistente.getEmail() + "): ");
                            String email = scanner.nextLine();
                            if (!email.isEmpty()) usuarioExistente.setEmail(email);

                            System.out.print("Nova senha (atual: " + usuarioExistente.getSenha() + "): ");
                            String senha = scanner.nextLine();
                            if (!senha.isEmpty()) usuarioExistente.setSenha(senha);

                            System.out.print("Novo país (atual: " + usuarioExistente.getPais() + "): ");
                            String pais = scanner.nextLine();
                            if (!pais.isEmpty()) usuarioExistente.setPais(pais);

                            System.out.print("Novo estado (atual: " + usuarioExistente.getEstado() + "): ");
                            String estado = scanner.nextLine();
                            if (!estado.isEmpty()) usuarioExistente.setEstado(estado);

                            System.out.print("Nova cidade (atual: " + usuarioExistente.getCidade() + "): ");
                            String cidade = scanner.nextLine();
                            if (!cidade.isEmpty()) usuarioExistente.setCidade(cidade);

                            System.out.print("Novo bairro (atual: " + usuarioExistente.getBairro() + "): ");
                            String bairro = scanner.nextLine();
                            if (!bairro.isEmpty()) usuarioExistente.setBairro(bairro);

                            System.out.print("Nova rua (atual: " + usuarioExistente.getRua() + "): ");
                            String rua = scanner.nextLine();
                            if (!rua.isEmpty()) usuarioExistente.setRua(rua);

                            System.out.print("Novo número (atual: " + usuarioExistente.getNumero() + "): ");
                            String numero = scanner.nextLine();
                            if (!numero.isEmpty()) usuarioExistente.setNumero(numero);

                            // Atualizar no banco
                            usuarioDAO.atualizar(usuarioExistente);
                            System.out.println("[✔] Usuário atualizado com sucesso!");
                        }
                    }
                    case 4 -> {
                        //Remover usuario
                        System.out.println("[*] Buscando usuários...");
                        exibirUsuarios();
                        System.out.println("[*] Digite o id do usuário a ser removido: ");
                        int id = scanner.nextInt();

                        try {
                            UsuarioDAO dao = new UsuarioDAO();
                            dao.deletar(id);

                            System.out.println("[✔] Usuário removido com sucesso!");
                        } catch (Exception e) {
                            System.out.println("[x] Erro ao remover usuário: " + e.getMessage());
                        }

                    }
                    case 5 -> {
                        if (usuarioSelecionado == null) {
                            System.out.println("[ERRO] NECESSÁRIO SELECIONAR USUÁRIO!");
                            break;
                        }
                        // Transferir saldo
                        System.out.println("[*] DIGITE A QUANTIDADE DE SALDO A SER TRANSFERIDA.");
                        double valorTransferencia = scanner.nextDouble();


                        exibirUsuarios();
                        System.out.println("[*] SELECIONE PARA QUEM TRANSFERIR SALDO.");
                        int idDestino = scanner.nextInt();

                        Usuario usuarioDestino = usuarioDAO.buscarPorId(idDestino);

                        if (usuarioDestino == null) {
                            System.out.println("[ERRO] Usuário destino não encontrado!");
                            break;
                        }

                        usuarioSelecionado.getCarteira().transferirSaldo(usuarioSelecionado, usuarioDestino, valorTransferencia);

                    }
                    case 6 -> {
                        if (usuarioSelecionado == null) {
                            System.out.println("[ERRO] Você precisa selecionar um usuário primeiro!");
                            break;
                        }
                        // Comprar moeda
                        exibirMenuMoedas();

                        Moeda moedaSelecionada = null;

                        while (moedaSelecionada == null) {
                            int moedaOpcao = scanner.nextInt();
                            moedaSelecionada = switch (moedaOpcao) {
                                case 1 -> btc;
                                case 2 -> eth;
                                case 3 -> sol;
                                default -> {
                                    System.out.println("Opcao invalida.");
                                    yield null;
                                }
                            };
                        }

                        System.out.println("[*] DIGITE A QUANTIDADE DE MOEDA A SER COMPRADA.");
                        double quantidadeMoeda = scanner.nextDouble();
                        usuarioSelecionado.getCarteira().comprarMoeda(moedaSelecionada, quantidadeMoeda);
                    }
                    case 7 -> {
                        if (usuarioSelecionado == null) {
                            System.out.println("[ERRO] Você precisa selecionar um usuário primeiro!");
                            break;
                        }
                        // Vender moeda
                        exibirMenuMoedas();

                        Moeda moedaSelecionada = null;

                        while (moedaSelecionada == null) {
                            int moedaOpcao = scanner.nextInt();
                            moedaSelecionada = switch (moedaOpcao) {
                                case 1 -> btc;
                                case 2 -> eth;
                                case 3 -> sol;
                                default -> {
                                    System.out.println("Opcao invalida.");
                                    yield null;
                                }
                            };
                        }

                        System.out.println("[*] DIGITE A QUANTIDADE DE MOEDA A SER VENDIDA.");
                        double quantidadeMoeda = scanner.nextDouble();
                        usuarioSelecionado.getCarteira().venderMoeda(moedaSelecionada, quantidadeMoeda);
                    }
                    case 8 -> {
                        if (usuarioSelecionado == null) {
                            System.out.println("[ERRO] Você precisa selecionar um usuário primeiro!");
                            break;
                        }

                        System.out.println("[*] DIGITE O ID DA TRANSACAO A SER CONSULTADA.");
                        int idTransacao = scanner.nextInt();
                        usuarioSelecionado.getCarteira().consultarTransacao(idTransacao);
                    }

                    case 9 -> {
                        if (usuarioSelecionado == null) {
                            System.out.println("[ERRO] Você precisa selecionar um usuário primeiro!");
                            break;
                        }
                        System.out.println("[*] DIGITE O ID DA TRANSFERENCIA A SER CONSULTADA.");
                        int idTransferencia = scanner.nextInt();
                        usuarioSelecionado.getCarteira().consultarTransferencia(idTransferencia);
                    }
                    case 10 -> {
                        testarFuncionalidadesMoeda();
                    }
                    case 11 -> {
                        testarFuncionalidadesInvestimento();
                    }
                    case 12 -> {
                        testarFuncionalidadesTransacao();
                    }
                    case 13 -> {
                        testarFuncionalidadesPreferencias();
                    }
                    case 14 -> {
                        testarFuncionalidadesTransferencia();
                    }
                    case 15 -> {
                        testarFuncionalidadesCarteira();
                    }
                    case 0 -> {
                        running = false;
                        System.out.println("Encerrando...");
                    }
                    default -> System.out.println("[ERRO] Opção inválida.");
                }
            }
        }
        catch (InputMismatchException e) {
            System.err.println("[ERRO] Valor digitado invalido.");
        }
        catch (Exception e) {
            System.err.println(e);
        }
        finally {
            scanner.close();
        }
    }
}
