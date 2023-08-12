package common;

public class Condutor {
    String nome = null;
    String cpf = null;

    boolean logado = false;

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCpf() {
        return this.cpf;
    }

    public void setLogado(boolean isLogged) {
        this.logado = isLogged;
    }

    public boolean isLogado() {
        return this.logado;
    }
}
