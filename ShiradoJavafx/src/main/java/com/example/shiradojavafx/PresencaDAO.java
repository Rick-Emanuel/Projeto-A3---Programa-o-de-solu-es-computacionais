package com.example.shiradojavafx;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PresencaDAO {

    public static void salvarPresencas(List<Presenca> presencas) throws SQLException {
        String sql = "INSERT INTO presenca(ra, data, presente) " +
                "VALUES(?,?,?) " +
                "ON DUPLICATE KEY UPDATE " +
                "presente = VALUES(presente)";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (Presenca presenca : presencas) {
                pstmt.setInt(1, presenca.getRa());
                pstmt.setDate(2, java.sql.Date.valueOf(presenca.getData()));
                pstmt.setBoolean(3, presenca.isPresente());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
    }

    public static List<Presenca> listarPresencas() throws SQLException {
        List<Presenca> lista = new ArrayList<>();
        String sql = "SELECT p.ra, a.nome, a.curso, a.turno, p.data, p.presente " +
                "FROM presenca p " +
                "INNER JOIN aluno a ON p.ra = a.ra " +
                "ORDER BY p.data DESC, a.nome ASC";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int ra = rs.getInt("ra");
                String nome = rs.getString("nome");
                String curso = rs.getString("curso");
                String turno = rs.getString("turno");
                LocalDate data = rs.getDate("data").toLocalDate();
                boolean presente = rs.getBoolean("presente");

                lista.add(new Presenca(ra, nome, curso, turno, data, presente));
            }
        }
        return lista;
    }

    public static List<Presenca> buscarPresencasPorRA(int ra) throws SQLException {
        List<Presenca> lista = new ArrayList<>();
        String sql = "SELECT p.ra, a.nome, a.curso, a.turno, p.data, p.presente " +
                "FROM presenca p " +
                "INNER JOIN aluno a ON p.ra = a.ra " +
                "WHERE p.ra = ? " +
                "ORDER BY p.data DESC";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ra);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int raResult = rs.getInt("ra");
                    String nome = rs.getString("nome");
                    String curso = rs.getString("curso");
                    String turno = rs.getString("turno");
                    LocalDate data = rs.getDate("data").toLocalDate();
                    boolean presente = rs.getBoolean("presente");

                    lista.add(new Presenca(raResult, nome, curso, turno, data, presente));
                }
            }
        }
        return lista;
    }

    public static List<Presenca> buscarPresencasPorNome(String nome) throws SQLException {
        List<Presenca> lista = new ArrayList<>();
        String sql = "SELECT p.ra, a.nome, a.curso, a.turno, p.data, p.presente " +
                "FROM presenca p " +
                "INNER JOIN aluno a ON p.ra = a.ra " +
                "WHERE a.nome LIKE ? " +
                "ORDER BY p.data DESC";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nome + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int ra = rs.getInt("ra");
                    String nomeCompleto = rs.getString("nome");
                    String curso = rs.getString("curso");
                    String turno = rs.getString("turno");
                    LocalDate data = rs.getDate("data").toLocalDate();
                    boolean presente = rs.getBoolean("presente");

                    lista.add(new Presenca(ra, nomeCompleto, curso, turno, data, presente));
                }
            }
        }
        return lista;
    }

    public static List<Presenca> buscarPresencasPorCursoETurno(String curso, String turno) throws SQLException {
        List<Presenca> lista = new ArrayList<>();
        String sql = "SELECT p.ra, a.nome, a.curso, a.turno, p.data, p.presente " +
                "FROM presenca p " +
                "INNER JOIN aluno a ON p.ra = a.ra " +
                "WHERE a.curso = ? AND a.turno = ? " +
                "ORDER BY p.data DESC";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, curso);
            stmt.setString(2, turno);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int ra = rs.getInt("ra");
                    String nome = rs.getString("nome");
                    String cursoResult = rs.getString("curso");
                    String turnoResult = rs.getString("turno");
                    LocalDate data = rs.getDate("data").toLocalDate();
                    boolean presente = rs.getBoolean("presente");

                    lista.add(new Presenca(ra, nome, cursoResult, turnoResult, data, presente));
                }
            }
        }
        return lista;
    }
}