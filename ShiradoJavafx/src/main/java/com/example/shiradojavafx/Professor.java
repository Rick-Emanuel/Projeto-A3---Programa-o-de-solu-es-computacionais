package com.example.shiradojavafx;

public class Professor {
    private String email;
    private String senha;
    private String nome;

    public Professor(String email, String senha, String nome) {
        this.email = email;
        this.senha = senha;
        this.nome = nome;
    }

    public boolean validarLogin(String email, String senha) {
        return this.email.equals(email) && this.senha.equals(senha);
    }


    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getNome() {
        return nome;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}