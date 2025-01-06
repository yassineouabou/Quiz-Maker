package net.projet.dao;

import net.projet.entity.Result;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ResultDoa {
    private Connection connection;

    public ResultDoa(Connection connection){
        this.connection = connection;
    }

    public boolean addResult(Result result){
        String query = "INSERT INTO result(studentId,examId,note) VALUES(?,?,?)";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setLong(1,result.getEtudiant().getId());
            ps.setLong(2,result.getExam().getId());
            ps.setFloat(3,result.getNote());
            int rowAffected = ps.executeUpdate();
            return rowAffected > 0;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
