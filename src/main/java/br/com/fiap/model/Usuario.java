package br.com.fiap.model;

public abstract class Usuario {
    //atributos da classe Usuario
    public Carteira carteira;
    public String email;
    public String senha;
    public String pais;
    public String estado;
    public String cidade;
    public String bairro;
    public String rua;
    public String numero;
    public String tipo;
    public Preferencias preferencias;

    public Usuario() {}

    //construtor da classe Usuario
    public Usuario(
            String tipo,
            String email,
            String senha,
            String pais,
            String estado,
            String cidade,
            String bairro,
            String rua,
            String numero
    ) {
        this.carteira = new Carteira( this);
        this.tipo = tipo;
        this.email = email;
        this.senha = senha;
        this.pais = pais;
        this.estado = estado;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.numero = numero;
        this.preferencias = new Preferencias( this, "light", "pt-BR", true); //valor padrao
    }

    //getters
    public Carteira getCarteira() {
        return carteira;
    }
    public String getEmail() {return email;}
    public String getPais() {return pais;}
    public String getEstado() {return estado;}
    public String getCidade() {return cidade;}
    public String getBairro() {return bairro;}
    public String getRua() {return rua;}
    public String getNumero() {return numero;}

    //setters
    public void setCarteira(Carteira carteira) {this.carteira = carteira;}
    public void setPais(String pais) {this.pais = pais;}
    public void setEstado(String estado) {this.estado = estado;}
    public void setCidade(String cidade) {this.cidade = cidade;}
    public void setBairro(String bairro) {this.bairro = bairro;}
    public void setRua(String rua) {this.rua = rua;}
    public void setNumero(String numero) {this.numero = numero;}

    //metodo abstrato assinatura
    public abstract String getNome();
    public abstract double getTaxaTransacao();
}
