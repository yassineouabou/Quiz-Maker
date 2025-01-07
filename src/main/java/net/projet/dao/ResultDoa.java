package net.projet.dao;

import com.mysql.cj.log.Log;
import net.projet.entity.Exam;
import net.projet.entity.Result;
import net.projet.entity.User;
import net.projet.exceptions.UserNotFoundException;
import net.projet.services.ExamService;
import net.projet.services.UserService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultDoa {
    private Connection connection;
    private UserService userService;
    private ExamService examService;

    public ResultDoa(Connection connection){
        this.connection = connection;
        userService = new UserService();
        examService = new ExamService();
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

    public Result findResultOfUser(Long userId){
        String query = "SELECT * FROM result WHERE studentId = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setLong(1,userId);
            ResultSet resultSet = ps.executeQuery();
            if(!resultSet.next())
                throw new UserNotFoundException("User Not Found !");
            else{
                System.out.println("Column 1: "+resultSet.getLong(1));
                User user = userService.findById(userId);
                Exam exam = examService.findById(resultSet.getLong(3));
                Result result = new Result(resultSet.getLong(1),
                        user,
                        exam,
                        resultSet.getFloat(4));
                System.out.println("result id: "+result.getId());
                return result;
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
