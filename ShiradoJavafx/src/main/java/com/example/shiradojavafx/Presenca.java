
package com.example.shiradojavafx;

import java.time.LocalDate;

public class Presenca {
    private int ra;
    private String nomeCompleto;
    private String curso;
    private String turno;
    private LocalDate data;
    private boolean presente;

    public Presenca(int ra, String nomeCompleto, String curso, String turno,
                    LocalDate data, boolean presente) {
        this.ra = ra;
        this.nomeCompleto = nomeCompleto;
        this.curso = curso;
        this.turno = turno;
        this.data = data;
        this.presente = presente;
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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public boolean isPresente() {
        return presente;
    }

    public void setPresente(boolean presente) {
        this.presente = presente;
    }
}