package com.example.shiradojavafx;

public class CadastroDeEstudante {
    private int ra;
    private String nomeCompleto;
    private String curso;
    private String turno;
    private String email;
    private String telefone;


    public CadastroDeEstudante() {
    }


    public CadastroDeEstudante(int ra, String nomeCompleto, String curso, String turno, String email, String telefone) {
        this.ra = ra;
        this.nomeCompleto = nomeCompleto;
        this.curso = curso;
        this.turno = turno;
        this.email = email;
        this.telefone = telefone;
    }


    public int getRa() {
        return ra;
    }

    public void setRa(int ra) {
        this.ra = ra;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}