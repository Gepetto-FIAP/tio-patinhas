package br.com.fiap.model;

import br.com.fiap.dao.InvestimentoDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class Investimento {
    private int id;
    private Moeda moeda;
    private Carteira carteira;
    private double quantidadeMoeda;

    public Investimento(Moeda moeda, Carteira carteira) {
        this.moeda = moeda;
        this.carteira = carteira;
    }

    public Investimento () {

    }

    public void validarTransacao(double quantidadeMoeda, TipoOperacao tipoOperacao, Transacao novaTransacao, double valorLiquido, double valorTaxa) throws SQLException {
        double valorTotal = valorLiquido + valorTaxa;
        if (tipoOperacao == TipoOperacao.COMPRA) {
            if(this.carteira.getSaldo() >= valorTotal) {
                this.quantidadeMoeda += quantidadeMoeda;
                this.carteira.removerSaldoCarteira(valorTotal, this.carteira);

                InvestimentoDAO dao = new InvestimentoDAO();
                dao.atualizarQuantidadeMoeda(this);

                novaTransacao.setStatus(Status.CONCLUIDA);

                System.out.printf("\n[Resumo] COMPRA REALIZADA\n Id: %d\n Usuario: %s\n Moeda: %s\n Quantidade: %s %.8f\n Valor total: R$ %.2f\n Valor líquido: R$ %.2f\n Valor taxa: R$ %.2f\n Saldo restante: R$ %.2f\n",
                        novaTransacao.getId(),
                        this.carteira.getNomeUsuario(),
                        moeda.getNome(),
                        moeda.getSimbolo(),
                        quantidadeMoeda,
                        valorTotal,
                        valorLiquido,
                        valorTaxa,
                        this.carteira.getSaldo()
                );


            } else {
                novaTransacao.setStatus(Status.ERRO);

                System.out.printf("\n[Resumo] COMPRA NÃO REALIZADA\n Id: %d\n Usuario: %s\n Motivo: saldo insuficiente\n Moeda: %s\n Quantidade: %s %.8f\n Valor total: R$ %.2f\n Valor líquido: R$ %.2f\n Valor taxa: R$ %.2f\n Saldo restante: R$ %.2f\n",
                        novaTransacao.getId(),
                        this.carteira.getNomeUsuario(),
                        moeda.getNome(),
                        moeda.getSimbolo(),
                        quantidadeMoeda,
                        valorTotal,
                        valorLiquido,
                        valorTaxa,
                        this.carteira.getSaldo()
                );
            }
        }
        else if (tipoOperacao == TipoOperacao.VENDA) {
            if (this.quantidadeMoeda >= quantidadeMoeda) {
                this.carteira.adicionarSaldoCarteira(valorLiquido, this.carteira);

                this.quantidadeMoeda -= quantidadeMoeda;
                InvestimentoDAO dao = new InvestimentoDAO();
                dao.atualizarQuantidadeMoeda(this);

                novaTransacao.setStatus(Status.CONCLUIDA);

                System.out.printf("\n[Resumo] VENDA REALIZADA\n Id: %d\n Usuario: %s\n Moeda: %s\n Quantidade: %s %.8f\n Valor total: R$ %.2f\n Valor a receber: R$ %.2f\n Valor taxa: R$ %.2f\n Saldo: R$ %.2f\n",
                        novaTransacao.getId(),
                        this.carteira.getNomeUsuario(),
                        moeda.getNome(),
                        moeda.getSimbolo(),
                        quantidadeMoeda,
                        valorTotal,
                        valorLiquido,
                        valorTaxa,
                        this.carteira.getSaldo()
                );
            }
            else {
                novaTransacao.setStatus(Status.ERRO);
                System.out.printf("\n[Resumo] VENDA NÃO REALIZADA\n Id: %d\n Usuario: %s\n Motivo: moedas insuficientes\n Moeda: %s\n Quantidade: %s %.8f\n Quantidade possuída: %s %.8f\n Saldo: R$ %.2f\n",
                        novaTransacao.getId(),
                        this.carteira.getNomeUsuario(),
                        moeda.getNome(),
                        moeda.getSimbolo(),
                        quantidadeMoeda,
                        moeda.getSimbolo(),
                        this.quantidadeMoeda,
                        this.carteira.getSaldo()
                );
            }
        }
    }

    //getters
    public Moeda getMoeda () {
        return this.moeda;
    }
    public double getQuantidadeMoeda () {
        return this.quantidadeMoeda;
    }

    public Carteira getCarteira () {
        return this.carteira;
    }

    public int getId () {
        return this.id;
    }

    //setters
    public void setIdInvestimento (int idInvestimento) {
        this.id = idInvestimento;
    }
    public void setMoeda (Moeda moeda) {
        this.moeda = moeda;
    }

    public void setId (int id) {
        this.id = id;
    }

    public void setCarteira (Carteira carteira) {
        this.carteira = carteira;
    }

    public  void setQuantidadeMoeda (double quantidadeMoeda) {
        this.quantidadeMoeda = quantidadeMoeda;
    }
}
