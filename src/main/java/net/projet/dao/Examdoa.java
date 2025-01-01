package net.projet.dao;

import net.projet.util.DataBaseConnection;

import java.sql.*;

public class Examdoa {

    private Connection connection;
    private Statement stat;

    public Examdoa() {
        connecter();
    }

    public void connecter() {
        try {
            connection = DataBaseConnection.getConnection();
            stat = connection.createStatement();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int CreateExam(String nomExam,String CodeUnique,Long profId) throws SQLException{
        String query="INSERT INTO exam (nom,codeUnique,professorId) VALUES (?,?,?)";
        try(PreparedStatement ps = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1,nomExam);
            ps.setString(2,CodeUnique);
            ps.setLong(3,profId);
            ps.executeUpdate();
            int newId = -1;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    newId = rs.getInt(1);
                }
            }
            return newId;
        }


    }
}
