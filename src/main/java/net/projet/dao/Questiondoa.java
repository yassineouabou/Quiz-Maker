package net.projet.dao;

import net.projet.util.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Questiondoa {

    private Connection connection;
    private Statement stat;


    public Questiondoa() {
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

    public boolean createQuestion(String question,String options,Long examId) throws SQLException{
        String query="INSERT INTO question (text,options,examId) VALUES (?,?,?)";
        try(PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1,question);
            ps.setString(2,options);
            ps.setLong(3,examId);
            return ps.executeUpdate()>0;
        }
    }
}
