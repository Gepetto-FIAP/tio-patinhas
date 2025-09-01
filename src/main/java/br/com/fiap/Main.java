
package br.com.fiap;
import br.com.fiap.model.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.File;
import java.util.List;
import br.com.fiap.dao.*;

public class Main {
    public static void exibirUsuarios(Usuario manoel, Usuario neymar, Usuario gerson, Usuario fiap) {
        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            List<Usuario> usuarios = usuarioDAO.listarTodos();

            System.out.println("=== LISTA DE USUÁRIOS ===");
            for (Usuario u : usuarios) {
                System.out.println("Nome: " + u.getNome());
                System.out.println("Email: " + u.getEmail());
                System.out.println("Saldo da Carteira: R$ " + u.getCarteira().getSaldo());
                System.out.println("-------------------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void exibirMenuPrincipal(Usuario manoel, Usuario neymar, Usuario gerson, Usuario fiap) {
        System.out.println("\n[*] BEM VINDO A CRYPTONKS, SELECIONE UM USUARIO PARA PROSSEGUIR");
        exibirUsuarios(manoel, neymar, gerson, fiap);
        System.out.println("5 - SAIR");
    }

    public static void exibirMenuAcoes(String nomeUsuarioSelecionado) {
        System.out.printf("\n[*] %s, SELECIONE UMA ACAO\n",nomeUsuarioSelecionado.toUpperCase());
        System.out.println("1 - TRANSFERIR SALDO");
        System.out.println("2 - COMPRAR MOEDA");
        System.out.println("3 - VENDER MOEDA");
        System.out.println("4 - CONSULTAR TRANSACAO");
        System.out.println("5 - CONSULTAR TRANSFERENCIA");
    }

    public static void exibirMenuMoedas() {
        System.out.println("\n[*] SELECIONE UM MOEDA");
        System.out.println("1 - BITCOIN");
        System.out.println("2 - ETHEREUM");
        System.out.println("3 - SOLANA");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            // Criando ArrayList de usuários
            ArrayList<Usuario> usuarios = new ArrayList<>();
            Usuario manoel = new PessoaFisica(
                    "pf",
                    "manoel@gmail.com",
                    "",
                    "Brasil",
                    "Bahia",
                    "Xique-xique",
                    "Centro",
                    "Rua A",
                    "10",
                    "",
                    "masculino",
                    19,
                    "Manoel",
                    "Souza"
            );
            usuarios.add(manoel);

            Usuario neymar = new PessoaFisica(
                    "pf",
                    "neymar@gmail.com",
                    "",
                    "Brasil",
                    "São Paulo",
                    "Santos",
                    "Centro",
                    "Rua B",
                    "11",
                    "",
                    "masculino",
                    19,
                    "Neymar",
                    "Souza"
            );
            usuarios.add(neymar);

            Usuario gerson = new PessoaFisica(
                    "pf",
                    "gerson@gmail.com",
                    "",
                    "Brasil",
                    "Rio de Janeiro",
                    "Rio de Janeiro",
                    "Centro",
                    "Rua c",
                    "12",
                    "",
                    "masculino",
                    19,
                    "Gerson",
                    "Souza"
            );
            usuarios.add(gerson);

            Usuario fiap = new PessoaJuridica(
                    "pj",
                    "fiap@fiap.com",
                    "",
                    "Brasil",
                    "Sao Paulo",
                    "Sao Paulo",
                    "Cerqueira Cesar",
                    "Avenida Paulista",
                    "1106",
                    "",
                    "Educacao",
                    "FIAP"
            );
            usuarios.add(fiap);

            // Criando ArrayList de moedas
            ArrayList<Moeda> moedas = new ArrayList<>();

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

            manoel.getCarteira().adicionarSaldo(10000.00);
            neymar.getCarteira().adicionarSaldo(1000000.00);
            gerson.getCarteira().adicionarSaldo(100000.00);

            // Exibindo informações dos usuários usando ArrayList
            System.out.println("\n[*] LISTA DE USUÁRIOS:");
            for (int i = 0; i < usuarios.size(); i++) {
                Usuario usuario = usuarios.get(i);
                System.out.printf("%d - %s (Saldo R$ %.2f)\n",
                        i + 1,
                        usuario.getNome(),
                        usuario.getCarteira().getSaldo());
            }

            // Exibindo informações das moedas usando ArrayList
            System.out.println("\n[*] LISTA DE MOEDAS DISPONÍVEIS:");
            for (int i = 0; i < moedas.size(); i++) {
                Moeda moeda = moedas.get(i);
                System.out.printf("%d - %s (%s) - Cotação: R$ %.2f\n",
                        i + 1,
                        moeda.getNome(),
                        moeda.getSimbolo(),
                        moeda.getCotacaoParaReal());
            }

            // Criando HashMap para mapear emails de usuários para objetos Usuario
            HashMap<String, Usuario> usuariosPorEmail = new HashMap<>();
            for (Usuario usuario : usuarios) {
                usuariosPorEmail.put(usuario.getEmail(), usuario);
            }

            // Criando HashMap para mapear símbolos de moedas para objetos Moeda
            HashMap<String, Moeda> moedasPorSimbolo = new HashMap<>();
            for (Moeda moeda : moedas) {
                moedasPorSimbolo.put(moeda.getSimbolo(), moeda);
            }

            // Exibindo informações usando HashMap
            System.out.println("\n[*] BUSCA DE USUÁRIO POR EMAIL:");
            String emailBusca = "manoel@gmail.com";
            Usuario usuarioEncontrado = usuariosPorEmail.get(emailBusca);
            if (usuarioEncontrado != null) {
                System.out.printf("Usuário encontrado: %s (Saldo R$ %.2f)\n",
                        usuarioEncontrado.getNome(),
                        usuarioEncontrado.getCarteira().getSaldo());
            } else {
                System.out.println("Usuário não encontrado para o email: " + emailBusca);
            }

            System.out.println("\n[*] BUSCA DE MOEDA POR SÍMBOLO:");
            String simboloBusca = "BTC";
            Moeda moedaEncontrada = moedasPorSimbolo.get(simboloBusca);
            if (moedaEncontrada != null) {
                System.out.printf("Moeda encontrada: %s - Cotação: R$ %.2f\n",
                        moedaEncontrada.getNome(),
                        moedaEncontrada.getCotacaoParaReal());
            } else {
                System.out.println("Moeda não encontrada para o símbolo: " + simboloBusca);
            }

            // Implementando manipulação de arquivos
            try {
                // Criando diretório para os arquivos se não existir
                File diretorio = new File("dados");
                if (!diretorio.exists()) {
                    diretorio.mkdir();
                }

                // Escrevendo informações dos usuários do ArrayList para um arquivo
                FileWriter fileWriterUsuarios = new FileWriter("dados/usuarios.txt");
                BufferedWriter writerUsuarios = new BufferedWriter(fileWriterUsuarios);

                writerUsuarios.write("LISTA DE USUÁRIOS:\n");
                writerUsuarios.write("=================\n\n");

                for (Usuario usuario : usuarios) {
                    writerUsuarios.write("Nome: " + usuario.getNome() + "\n");
                    writerUsuarios.write("Email: " + usuario.getEmail() + "\n");
                    writerUsuarios.write("País: " + usuario.getPais() + "\n");
                    writerUsuarios.write("Estado: " + usuario.getEstado() + "\n");
                    writerUsuarios.write("Cidade: " + usuario.getCidade() + "\n");
                    writerUsuarios.write("Saldo: R$ " + usuario.getCarteira().getSaldo() + "\n");
                    writerUsuarios.write("Taxa de Transação: " + (usuario.getTaxaTransacao() * 100) + "%\n");
                    writerUsuarios.write("Tipo: " + (usuario instanceof PessoaFisica ? "Pessoa Física" : "Pessoa Jurídica") + "\n");
                    writerUsuarios.write("\n");
                }

                writerUsuarios.close();
                System.out.println("\n[*] Arquivo de usuários criado com sucesso: dados/usuarios.txt");

                // Escrevendo informações das moedas do HashMap para um arquivo
                FileWriter fileWriterMoedas = new FileWriter("dados/moedas.txt");
                BufferedWriter writerMoedas = new BufferedWriter(fileWriterMoedas);

                writerMoedas.write("LISTA DE MOEDAS:\n");
                writerMoedas.write("===============\n\n");

                for (String simbolo : moedasPorSimbolo.keySet()) {
                    Moeda moeda = moedasPorSimbolo.get(simbolo);
                    writerMoedas.write("Nome: " + moeda.getNome() + "\n");
                    writerMoedas.write("Símbolo: " + moeda.getSimbolo() + "\n");
                    writerMoedas.write("Cotação: R$ " + moeda.getCotacaoParaReal() + "\n");
                    writerMoedas.write("\n");
                }

                writerMoedas.close();
                System.out.println("[*] Arquivo de moedas criado com sucesso: dados/moedas.txt");

            } catch (IOException e) {
                System.err.println("[ERRO] Erro ao manipular arquivos: " + e.getMessage());
            }

            boolean running = true;

            while (running) {
                exibirMenuPrincipal(manoel, neymar, gerson, fiap);
                int usuarioOpcao = scanner.nextInt();

                Usuario usuarioSelecionado = switch (usuarioOpcao) {
                    case 1 -> manoel;
                    case 2 -> neymar;
                    case 3 -> gerson;
                    case 4 -> fiap;
                    case 5 -> {
                        System.out.println("Encerrando programa.");
                        running = false;
                        yield null;
                    }
                    default -> {
                        System.out.println("Opcao invalida.");
                        yield null;
                    }
                };

                if (usuarioSelecionado == null) continue;

                exibirMenuAcoes(usuarioSelecionado.getNome());

                int acaoOpcao = scanner.nextInt();

                switch (acaoOpcao) {
                    case 1 -> {
                        Usuario usuarioDestino = null;

                        while (usuarioDestino == null) {
                            System.out.println("[*] SELECIONE PARA QUEM TRANSFERIR SALDO.");
                            exibirUsuarios(manoel, neymar, gerson, fiap);

                            int usuarioDestinoOpcao = scanner.nextInt();

                            usuarioDestino = switch (usuarioDestinoOpcao) {
                                case 1 -> manoel;
                                case 2 -> neymar;
                                case 3 -> gerson;
                                case 4 -> fiap;
                                default -> {
                                    System.out.println("Opcao invalida.");
                                    yield null;
                                }
                            };
                        }

                        System.out.println("[*] DIGITE A QUANTIDADE DE SALDO A SER TRANSFERIDA.");
                        double valorTransferencia = scanner.nextDouble();
                        usuarioSelecionado.getCarteira().transferirSaldo(usuarioDestino, valorTransferencia);
                    }
                    case 2 -> {
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

                        System.out.println("[*] DIGITE A QUANTIDADE DE MOEDA A SER C0MPRADA.");
                        double quantidadeMoeda = scanner.nextDouble();
                        usuarioSelecionado.getCarteira().comprarMoeda(moedaSelecionada, quantidadeMoeda);
                    }
                    case 3 -> {
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

                    case 4 -> {
                        System.out.println("[*] DIGITE O ID DA TRANSACAO A SER CONSULTADA.");
                        int idTransacao = scanner.nextInt();
                        usuarioSelecionado.getCarteira().consultarTransacao(idTransacao);
                    }

                    case 5 -> {
                        System.out.println("[*] DIGITE O ID DA TRANSFERENCIA A SER CONSULTADA.");
                        int idTransferencia = scanner.nextInt();
                        usuarioSelecionado.getCarteira().consultarTransferencia(idTransferencia);
                    }

                }
                ;
            }
        }
        catch (InputMismatchException e) {
            System.err.println("[ERRO] Valor digitado invalido.");
        }

        catch (Exception e) {
            System.err.println("[ERRO] Erro inesperado.");
        }

        finally {
            scanner.close();
        }
    }
}
