package net.projet.dao;

import net.projet.entity.Question;
import net.projet.exceptions.QuestionNotFoundException;
import net.projet.util.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class Questiondoa {

    private Connection connection;


    public Questiondoa() {
        connecter();
    }

    public void connecter() {
        connection = DataBaseConnection.getConnection();
    }

    public boolean createQuestion(Question question){
        String query="INSERT INTO question (text,options,examId) VALUES (?,?,?)";
        try(PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1,question.getText());
            ps.setString(2,question.getOptions().toString());
            ps.setLong(3,question.getExam().getId());
            return ps.executeUpdate()>0;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Question> getAllQuestionsByExamId(Long examId){
        String query = "SELECT * FROM question WHERE examId = ?";
        ArrayList<Question> questions = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(query)){
            ps.setLong(1,examId);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                Question question = new Question(resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3));
                questions.add(question);
            }
            return questions;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Question findById(Long id){
        String query = "SELECT * FROM question WHERE questionId = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setLong(1,id);
            ResultSet resultSet = ps.executeQuery();
            if(!resultSet.next())
                throw new QuestionNotFoundException("Question Not Found !");
            else{
                Question question = new Question(resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3));
                return question;
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

}
