package com.example.shiradojavafx;

public class Professor {
    private String emailCorreto;
    private String senhaCorreta;

    public Professor(String email, String senha) {
        this.emailCorreto = email;
        this.senhaCorreta = senha;
    }

    public boolean validarLogin(String email, String senha) {
        return emailCorreto.equals(email) && senhaCorreta.equals(senha);
    }
}