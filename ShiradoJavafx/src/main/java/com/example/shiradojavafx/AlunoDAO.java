package com.example.shiradojavafx;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {
    public static void inserirAluno(CadastroDeEstudante aluno) throws SQLException {
        String sql = "INSERT INTO aluno(ra, nome, email, telefone, curso, turno) VALUES(?,?,?,?,?,?)";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, aluno.getRa());
            pstmt.setString(2, aluno.getNomeCompleto());
            pstmt.setString(3, aluno.getEmail());
            pstmt.setInt(4, aluno.getTelefone());
            pstmt.setString(5, aluno.getCurso());
            pstmt.setString(6, aluno.getTurno());
            pstmt.executeUpdate();
        }
    }

    public static List<CadastroDeEstudante> listarTodosAlunos() throws SQLException {
        List<CadastroDeEstudante> alunos = new ArrayList<>();
        String sql = "SELECT ra, nome, email, telefone, curso, turno FROM aluno";

        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                CadastroDeEstudante aluno = new CadastroDeEstudante(
                        rs.getInt("ra"),
                        rs.getString("nome"),
                        rs.getString("curso"),
                        rs.getString("turno"),
                        rs.getString("email"),
                        rs.getInt("telefone")
                );
                alunos.add(aluno);
            }
        }
        return alunos;
    }


    public static List<CadastroDeEstudante> buscarTodos() throws SQLException {
        return listarTodosAlunos();
    }
}
