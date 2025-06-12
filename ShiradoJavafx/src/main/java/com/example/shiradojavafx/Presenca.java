package com.example.shiradojavafx;

import java.time.LocalDate;

public class Presenca {
    private int ra;
    private String nomeCompleto;
    private LocalDate data;
    private boolean presente;

    public Presenca(int ra, String nomeCompleto, LocalDate data, boolean presente) {
        this.ra = ra;
        this.nomeCompleto = nomeCompleto;
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