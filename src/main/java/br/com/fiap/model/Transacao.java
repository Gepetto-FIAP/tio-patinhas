package br.com.fiap.model;


import java.time.LocalDateTime;

public class Transacao {
    private static int contadorId = 0;
    public int id;
    private Carteira carteira;
    private Investimento investimento;
    private Moeda moeda;
    private TipoOperacao tipoOperacao;
    private double valorTotalTransacao;
    private double valorLiquidoTransacao;
    private double valorTaxaTransacao;
    private Double quantidadeMoeda;
    private Status status;
    private LocalDateTime dataTransacao;

    public Transacao (
            Carteira carteira,
            Investimento investimento,
            Moeda moeda,
            TipoOperacao tipoOperacao,
            double valorTotalTransacao,
            double valorLiquidoTransacao,
            double valorTaxaTransacao,
            Double quantidadeMoeda,
            Status status,
            LocalDateTime data
    ) {
        this.id = contadorId++;
        this.carteira = carteira;
        this.investimento = investimento;
        this.moeda = moeda;
        this.tipoOperacao = tipoOperacao;
        this.valorTotalTransacao = valorTotalTransacao;
        this.valorLiquidoTransacao = valorLiquidoTransacao;
        this.valorTaxaTransacao = valorTaxaTransacao;
        this.quantidadeMoeda = quantidadeMoeda;
        this.status = status;
        this.dataTransacao = data;
    }

    public void exibir() {
        System.out.printf("\n[Resumo] DADOS DA TRANSAÇÃO #%d:\n Tipo: %s\n Usuário: %s\n Moeda: %s \n Quantidade moeda: %s %.8f\n Valor total: R$ %.2f \n Valor taxa: R$ %.2f \n Status: %s \n",
                this.id,
                this.tipoOperacao,
                this.carteira.getNomeUsuario(),
                this.moeda.getNome(),
                this.moeda.getSimbolo(),
                this.quantidadeMoeda,
                this.valorTotalTransacao,
                this.valorTaxaTransacao,
                this.status
        );
    }


    public int getId() {
        return id;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
}
