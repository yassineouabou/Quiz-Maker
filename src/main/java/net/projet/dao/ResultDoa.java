package net.projet.dao;

import com.mysql.cj.log.Log;
import net.projet.entity.Exam;
import net.projet.entity.Result;
import net.projet.entity.User;
import net.projet.exceptions.ResultNotFoundException;
import net.projet.exceptions.UserNotFoundException;
import net.projet.services.ExamService;
import net.projet.services.UserService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public Result findResultOfUser(Long etudiantId){
        String query = "SELECT * FROM result WHERE studentId = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setLong(1,etudiantId);
            ResultSet resultSet = ps.executeQuery();
            if(!resultSet.next())
                throw new ResultNotFoundException("Result Not Found !");
            else{
                User user = userService.findById(etudiantId);
                Exam exam = examService.findById(resultSet.getLong(3));
                Result result = new Result(resultSet.getLong(1),
                        user,
                        exam,
                        resultSet.getFloat(4));

                return result;
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Result> getAllEtudiantResult(Long etudiantId){
        String query = "SELECT * FROM result WHERE studentId = ?";
        ArrayList<Result> results = new ArrayList<>();
        try(PreparedStatement ps =connection.prepareStatement(query)){
            ps.setLong(1,etudiantId);
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){
                User user = userService.findById(etudiantId);
                Exam exam = examService.findById(resultSet.getLong(3));
                Result result = new Result(resultSet.getLong(1),
                        user,
                        exam,
                        resultSet.getFloat(4));
                results.add(result);
            }
            return results;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int nbrResultByetudiantId(Long etudiantId){
        String query = "SELECT COUNT(*) as nbrExam FROM result WHERE studentId = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setLong(1,etudiantId);
            ResultSet resultSet = ps.executeQuery();
            if(!resultSet.next())
                throw new ResultNotFoundException("Result Not found Exception !");
            else {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public float moyenGenerale(Long etudiantId){
        String query = "SELECT COUNT(*) as nbrExam, SUM(note) as totalNote FROM result WHERE studentId = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setLong(1,etudiantId);
            ResultSet resultSet = ps.executeQuery();
            if(!resultSet.next())
                throw new ResultNotFoundException("Result Not found Exception !");
            else {
                int totalExam = resultSet.getInt(1);
                float totalNote = resultSet.getFloat(2);
                return totalNote/totalExam;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Result> getAllByExamId(Long examId){
        String query = "SELECT * FROM result WHERE examId = ?";
        ArrayList<Result> results = new ArrayList<>();
        try(PreparedStatement ps =connection.prepareStatement(query)){
            ps.setLong(1,examId);
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){
                User user = userService.findById(resultSet.getLong(2));
                Exam exam = examService.findById(examId);
                Result result = new Result(resultSet.getLong(1),
                        user,
                        exam,
                        resultSet.getFloat(4));
                results.add(result);
            }
            return results;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

}
