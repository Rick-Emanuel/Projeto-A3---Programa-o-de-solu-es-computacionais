package com.example.shiradojavafx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/universidade";
    private static final String USER = "root";
    private static final String PASSWORD = "abuble123";

    public static void initialize() {
        try (Connection conn = connect()) {
            if (conn != null) {
                createTables(conn);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inicializar banco de dados: " + e.getMessage());
        }
    }

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private static void createTables(Connection conn) {
        String sqlAluno = "CREATE TABLE IF NOT EXISTS aluno (" +
                "ra INT PRIMARY KEY," +
                "nome VARCHAR(255) NOT NULL," +
                "email VARCHAR(255)," +
                "telefone VARCHAR(20)," +
                "curso VARCHAR(100) NOT NULL," +
                "turno VARCHAR(50) NOT NULL)";

        String sqlPresenca = "CREATE TABLE IF NOT EXISTS presenca (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "ra INT NOT NULL," +
                "data DATE NOT NULL," +
                "presente BOOLEAN NOT NULL," +
                "FOREIGN KEY (ra) REFERENCES aluno(ra))";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sqlAluno);
            stmt.execute(sqlPresenca);
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabelas: " + e.getMessage());
        }
    }
}