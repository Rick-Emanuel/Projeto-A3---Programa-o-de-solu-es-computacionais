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
        try (Statement stmt = conn.createStatement()) {

            stmt.execute("DROP TABLE IF EXISTS presenca");


            String sqlAluno = "CREATE TABLE IF NOT EXISTS aluno (" +
                    "ra INT PRIMARY KEY," +
                    "nome VARCHAR(255) NOT NULL," +
                    "email VARCHAR(255)," +
                    "telefone VARCHAR(20)," +
                    "curso VARCHAR(100) NOT NULL," +
                    "turno VARCHAR(50) NOT NULL)";
            stmt.execute(sqlAluno);


            String sqlPresenca = "CREATE TABLE presenca (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "ra INT NOT NULL," +
                    "data DATE NOT NULL," +
                    "presente BOOLEAN NOT NULL," +
                    "CONSTRAINT fk_presenca_aluno FOREIGN KEY (ra) REFERENCES aluno(ra) ON DELETE CASCADE ON UPDATE CASCADE," +
                    "UNIQUE KEY unique_presenca (ra, data))";
            stmt.execute(sqlPresenca);

            System.out.println("Tabelas criadas com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao criar tabelas: " + e.getMessage());
        }
    }


    public static void fixForeignKeyConstraints() {
        try (Connection conn = connect()) {
            Statement stmt = conn.createStatement();


            try {
                stmt.execute("ALTER TABLE presenca DROP FOREIGN KEY presenca_ibfk_1");
            } catch (SQLException e) {

            }

            try {
                stmt.execute("ALTER TABLE presenca DROP FOREIGN KEY fk_presenca_aluno");
            } catch (SQLException e) {

            }


            stmt.execute("ALTER TABLE presenca ADD CONSTRAINT fk_presenca_aluno " +
                    "FOREIGN KEY (ra) REFERENCES aluno(ra) ON DELETE CASCADE ON UPDATE CASCADE");

            System.out.println("Foreign key constraints fixed!");

        } catch (SQLException e) {
            System.err.println("Erro ao corrigir constraints: " + e.getMessage());
        }
    }
}