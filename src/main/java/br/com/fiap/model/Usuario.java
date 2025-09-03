package br.com.fiap.model;

import br.com.fiap.dao.CarteiraDAO;

import java.sql.SQLException;

public abstract class Usuario {
    //atributos da classe Usuario
    public int id;
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
            int id,
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
        this.id = id;
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
        return this.carteira;
    }
    public String getEmail() {return this.email;}
    public String getPais() {return this.pais;}
    public String getEstado() {return this.estado;}
    public String getCidade() {return this.cidade;}
    public String getBairro() {return this.bairro;}
    public String getRua() {return this.rua;}
    public String getNumero() {return this.numero;}
    public int getId() {return this.id;}
    public String getSenha() {return this.senha;}

    //setters
    public void setCarteira(Carteira carteira) {this.carteira = carteira;}
    public void setPais(String pais) {this.pais = pais;}
    public void setEstado(String estado) {this.estado = estado;}
    public void setCidade(String cidade) {this.cidade = cidade;}
    public void setBairro(String bairro) {this.bairro = bairro;}
    public void setRua(String rua) {this.rua = rua;}
    public void setNumero(String numero) {this.numero = numero;}
    public void setEmail(String email) {this.email = email;}
    public void setSenha(String senha) {this.senha = senha;}
    public void setId(int id) {this.id = id;}

    //metodo abstrato assinatura
    public abstract String getNome();
    public abstract double getTaxaTransacao();

}
