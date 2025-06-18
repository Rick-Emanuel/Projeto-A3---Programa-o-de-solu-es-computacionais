package com.example.shiradojavafx;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    public static void inserirAluno(CadastroDeEstudante aluno) throws SQLException {
        String sql = "INSERT INTO aluno(ra, nome, curso, turno, email, telefone) VALUES(?,?,?,?,?,?)";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, aluno.getRa());
            pstmt.setString(2, aluno.getNomeCompleto());
            pstmt.setString(3, aluno.getCurso());
            pstmt.setString(4, aluno.getTurno());
            pstmt.setString(5, aluno.getEmail());
            pstmt.setString(6, aluno.getTelefone());
            pstmt.executeUpdate();
        }
    }

    public static List<CadastroDeEstudante> listarTodosAlunos() throws SQLException {
        List<CadastroDeEstudante> alunos = new ArrayList<>();
        String sql = "SELECT ra, nome, email, telefone, curso, turno FROM aluno ORDER BY nome";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                CadastroDeEstudante aluno = new CadastroDeEstudante();
                aluno.setRa(rs.getInt("ra"));
                aluno.setNomeCompleto(rs.getString("nome"));
                aluno.setEmail(rs.getString("email"));
                aluno.setTelefone(rs.getString("telefone"));
                aluno.setCurso(rs.getString("curso"));
                aluno.setTurno(rs.getString("turno"));
                alunos.add(aluno);
            }
        }
        return alunos;
    }

    public static void deletarAluno(int ra) throws SQLException {
        Connection conn = null;
        try {
            conn = Database.connect();
            conn.setAutoCommit(false);


            String sqlDeletePresenca = "DELETE FROM presenca WHERE ra = ?";
            try (PreparedStatement stmtPresenca = conn.prepareStatement(sqlDeletePresenca)) {
                stmtPresenca.setInt(1, ra);
                int presencasRemovidas = stmtPresenca.executeUpdate();
                System.out.println("Removidas " + presencasRemovidas + " presenças do aluno RA: " + ra);
            }


            String sqlDeleteAluno = "DELETE FROM aluno WHERE ra = ?";
            try (PreparedStatement stmtAluno = conn.prepareStatement(sqlDeleteAluno)) {
                stmtAluno.setInt(1, ra);
                int rowsAffected = stmtAluno.executeUpdate();

                if (rowsAffected == 0) {
                    throw new SQLException("Aluno não encontrado com RA: " + ra);
                }
            }

            conn.commit();
            System.out.println("Aluno RA " + ra + " removido com sucesso!");

        } catch (SQLException e) {

            if (conn != null) {
                try {
                    conn.rollback();
                    System.err.println("Transação cancelada devido ao erro: " + e.getMessage());
                } catch (SQLException rollbackEx) {
                    System.err.println("Erro no rollback: " + rollbackEx.getMessage());
                }
            }
            throw new SQLException("Erro ao deletar aluno: " + e.getMessage(), e);
        } finally {

            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException closeEx) {
                    System.err.println("Erro ao fechar conexão: " + closeEx.getMessage());
                }
            }
        }
    }


    public static boolean alunoTemPresencas(int ra) throws SQLException {
        String sql = "SELECT COUNT(*) as count FROM presenca WHERE ra = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ra);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("count") > 0;
                }
            }
        }
        return false;
    }


    public static CadastroDeEstudante buscarAlunoPorRA(int ra) throws SQLException {
        String sql = "SELECT ra, nome, curso, turno, email, telefone FROM aluno WHERE ra = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ra);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String nomeCompleto = rs.getString("nome");
                    String curso = rs.getString("curso");
                    String turno = rs.getString("turno");
                    String email = rs.getString("email");
                    String telefone = rs.getString("telefone");

                    return new CadastroDeEstudante(ra, nomeCompleto, curso, turno, email, telefone);
                }
            }
        }
        return null;
    }
}