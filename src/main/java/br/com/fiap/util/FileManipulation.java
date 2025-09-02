package br.com.fiap.util;

import br.com.fiap.model.Moeda;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class FileManipulation {

    // Método para salvar moedas em um arquivo
    public static void salvarMoedas(HashMap<String, Moeda> moedasPorSimbolo) {
        try {
            // Criando diretório para os arquivos se não existir
            File diretorio = new File("dados");
            if (!diretorio.exists()) {
                diretorio.mkdir();
            }

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
    }
}
