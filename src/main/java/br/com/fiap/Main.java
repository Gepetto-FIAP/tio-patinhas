
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
        System.out.println("8 - CONSULTAR TRANSACAO");
        System.out.println("9 - CONSULTAR TRANSFERENCIA");
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
                        // implementar cadastro


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
