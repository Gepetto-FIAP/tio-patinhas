
package br.com.fiap.model;
public class Preferencias {
    private Usuario usuario;
    private String tema;
    private String idioma;
    private boolean receberNotificacoes;

    public Preferencias(Usuario usuario, String tema, String idioma, boolean receberNotificacoes) {
        this.usuario = usuario;
        this.tema = tema;
        this.idioma = idioma;
        this.receberNotificacoes = receberNotificacoes;
    }

    public void mudarTema(String tema) {
        this.tema = tema;
    }

    public void mudarIdioma(String idioma) {
        this.idioma = idioma;
    }

    public void mudarReceberNotificacoes(boolean receberNotificacoes) {
        this.receberNotificacoes = receberNotificacoes;
    }

    // Getters e Setters
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getTema() {
        return tema;
    }

    public String getIdioma() {
        return idioma;
    }

    public boolean isReceberNotificacoes() {
        return receberNotificacoes;
    }
}
