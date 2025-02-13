package net.projet.dao;

import net.projet.entity.Exam;
import net.projet.entity.Question;
import net.projet.entity.User;
import net.projet.exceptions.ExamNotFoundException;
import net.projet.util.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class Examdoa {

    private Connection connection;

    private Questiondoa questiondoa;
    private UserDoa userDoa;

    public Examdoa() {
        connecter();
        userDoa = new UserDoa(DataBaseConnection.getConnection());
        questiondoa = new Questiondoa();

    }

    public void connecter() {
        connection = DataBaseConnection.getConnection();
    }

    public Long CreateExam(Exam exam){
        String query="INSERT INTO exam (nom,codeUnique,professorId,temps) VALUES (?,?,?,?)";
        try(PreparedStatement ps = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1,exam.getNom());
            ps.setString(2,exam.getCodeUnque());
            ps.setLong(3,exam.getProf().getId());
            ps.setString(4,exam.getTemps());
            int rowAffected = ps.executeUpdate();
            Long id = -1L;
            if (rowAffected > 0) {
                try (ResultSet rs= ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1); // Récupération et définition de l'ID
                    }
                }
            } else {
                throw new RuntimeException("Failed to create exam, no rows affected.");
            }
            return id;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Exam findExamByCodeUnique(String codeUnique){
        String query = "SELECT * from exam WHERE codeUnique=?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1,codeUnique);
            ResultSet resultSet = ps.executeQuery();
            if(!resultSet.next()){
                throw new ExamNotFoundException("Exam Not Found !");
            }
            else{
                User user = userDoa.findById(resultSet.getLong(4));
                ArrayList<Question> questions = questiondoa.getAllQuestionsByExamId(resultSet.getLong(1));
                Exam exam = new Exam(resultSet.getLong(1),
                        resultSet.getString(2),
                        user,
                        resultSet.getString(5),
                        resultSet.getString(3),
                        questions);
                return exam;
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Exam findById(Long examId){
        String query = "SELECT * FROM exam WHERE examId = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setLong(1,examId);
            ResultSet resultSet = ps.executeQuery();
            if(!resultSet.next())
                throw new ExamNotFoundException("Exam not found ! ");
            else{
                User user = userDoa.findById(resultSet.getLong(4));
                ArrayList<Question> questions = questiondoa.getAllQuestionsByExamId(resultSet.getLong(1));
                Exam exam = new Exam(resultSet.getLong(1),
                        resultSet.getString(2),
                        user,
                        resultSet.getString(5),
                        resultSet.getString(3),
                        questions);
                return exam;
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Exam> findByProfId(Long profId){
        String query = "SELECT * FROM exam where professorId = ?";
        ArrayList<Exam> exams = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setLong(1,profId);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                User user = userDoa.findById(resultSet.getLong(4));
                ArrayList<Question> questions = questiondoa.getAllQuestionsByExamId(resultSet.getLong(1));
                Exam exam = new Exam(resultSet.getLong(1),
                        resultSet.getString(2),
                        user,
                        resultSet.getString(5),
                        resultSet.getString(3),
                        questions);
                exams.add(exam);
            }
            return exams;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
