package br.com.bossini.myapplication;

import java.util.Date;

class Mensagem implements Comparable <Mensagem> {

    @Override
    public int compareTo(Mensagem mensagem) {
        return this.data.compareTo(mensagem.data);
    }

    private String texto;
    private Date data;
    private String nickname;

    public Mensagem() {
    }

    public Mensagem(String texto, Date data, String nickname) {
        this.texto = texto;
        this.data = data;
        this.nickname = nickname;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}
