package com.example.shiradojavafx;

import java.sql.*;
import java.util.List;

public class PresencaDAO {
    public static void salvarPresencas(List<Presenca> presencas) throws SQLException {
        String sql = "INSERT INTO presenca(ra, data, presente) VALUES(?,?,?)";

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
}