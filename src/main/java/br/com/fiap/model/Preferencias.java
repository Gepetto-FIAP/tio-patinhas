
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

    // Getters
    public Usuario getUsuario() {
        return this.usuario;
    }

    public String getTema() {
        return this.tema;
    }

    public String getIdioma() {
        return this.idioma;
    }

    public boolean isReceberNotificacoes() {
        return this.receberNotificacoes;
    }
}
