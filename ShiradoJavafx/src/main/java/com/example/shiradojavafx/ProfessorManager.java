package com.example.shiradojavafx;

import java.util.ArrayList;
import java.util.List;

public class ProfessorManager {
    private static ProfessorManager instance;
    private List<Professor> professores;

    private ProfessorManager() {
        professores = new ArrayList<>();

        professores.add(new Professor("professor@email.com", "123456", "Professor Padrão"));
    }

    public static ProfessorManager getInstance() {
        if (instance == null) {
            instance = new ProfessorManager();
        }
        return instance;
    }

    public boolean cadastrarProfessor(String email, String senha, String nome) {

        for (Professor prof : professores) {
            if (prof.getEmail().equals(email)) {
                return false; // Email já cadastrado
            }
        }

        professores.add(new Professor(email, senha, nome));
        return true;
    }

    public boolean validarLogin(String email, String senha) {
        for (Professor prof : professores) {
            if (prof.validarLogin(email, senha)) {
                return true;
            }
        }
        return false;
    }

    public List<Professor> getProfessores() {
        return new ArrayList<>(professores);
    }

    public boolean emailExiste(String email) {
        for (Professor prof : professores) {
            if (prof.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
}