package br.com.fiap.model;

public class Moeda {
    private static int contadorId = 1;
    private int id;
    public String nome;
    public String simbolo;
    public double cotacaoParaReal;

    public Moeda ( String nome, String simbolo, double cotacaoParaReal) {
        this.id = contadorId++;
        this.nome = nome;
        this.simbolo = simbolo;
        this.cotacaoParaReal = cotacaoParaReal;
    }


    //getters
    public int getId () {return this.id;}
    public String getNome () {
        return this.nome;
    }
    public String getSimbolo () {
        return this.simbolo;
    }
    public double getCotacaoParaReal () {return this.cotacaoParaReal;}

    //setters
    public void setNome(String nome) {this.nome = nome;}
    public void setSimbolo(String simbolo) {this.simbolo = simbolo;}
    public void setCotacaoParaReal(double cotacaoParaReal) {this.cotacaoParaReal = cotacaoParaReal;}

}
