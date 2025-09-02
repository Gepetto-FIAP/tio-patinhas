package br.com.fiap.model;

public class PessoaFisica extends Usuario {
    public String cpf;
    public String genero;
    public int idade;
    public String nome;
    public String sobrenome;
    private double taxaTransacao;

    public PessoaFisica() {
        super();
    }

    public PessoaFisica(
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
            String cpf,
            String genero,
            int idade,
            String nome,
            String sobrenome
    ) {
        super(id, tipo, email, senha, pais, estado, cidade, bairro, rua, numero);
        this.cpf = cpf;
        this.genero = genero;
        this.idade = idade;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.taxaTransacao = 0.02;
    }

    public PessoaFisica(String pf, String mail, String s, String brasil, String bahia, String s1, String centro, String ruaA, String number, String s2, String masculino, int i, String manoel, String souza) {
    }

    //getters
    public String getCpf() { return cpf; }
    public String getGenero() { return genero; }
    public int getIdade() { return idade; }
    public String getSobrenome() { return sobrenome; }

    //setters
    public void setNome(String nome) { this.nome = nome; }
    public void setSobrenome(String sobrenome) { this.sobrenome = sobrenome; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setGenero(String genero) { this.genero = genero; }
    public void setIdade(int idade) { this.idade = idade; }
    public void setTaxaTransacao(double taxaTransacao) { this.taxaTransacao = taxaTransacao; }

    @Override
    public String getNome() { return nome; }

    @Override
    public double getTaxaTransacao() { return taxaTransacao; }
}
