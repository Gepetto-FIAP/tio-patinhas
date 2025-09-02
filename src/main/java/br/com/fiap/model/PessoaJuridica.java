package br.com.fiap.model;

public class PessoaJuridica extends Usuario {
    public String cnpj;
    public String ramo;
    public String nomeFantasia;
    private double taxaTransacao;

    public PessoaJuridica() {
        super();
    }

    public PessoaJuridica(
            int id,
            String tipo,
            String email,
            String senha,
            String pais,
            String estado,
            String cidade,
            String bairro,
            String rua,
            String numero,
            String cnpj,
            String ramo,
            String nomeFantasia
    ) {
        super(id, tipo, email, senha, pais, estado, cidade, bairro, rua, numero);
        this.cnpj = cnpj;
        this.ramo = ramo;
        this.nomeFantasia = nomeFantasia;
        this.taxaTransacao = 0.04;
    }

    //getters
    public String getCnpj() {return cnpj;}
    public String getRamo() {return ramo;}

    //setters
    public void setCnpj(String cnpj) {this.cnpj = cnpj;}
    public void setRamo(String ramo) {this.ramo = ramo;}
    public void setTaxaTransacao(double taxaTransacao) { this.taxaTransacao = taxaTransacao; }
    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    @Override
    public String getNome() { return nomeFantasia; }

    @Override
    public double getTaxaTransacao() { return taxaTransacao; }

}
